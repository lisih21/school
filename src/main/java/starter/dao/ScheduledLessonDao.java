package starter.dao;

import starter.entity.ClassEntity;
import starter.entity.ScheduledLessonEntity;
import starter.entity.SchoolSubjectEntity;
import starter.entity.TeacherEntity;
import starter.exception.DaoException;
import starter.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScheduledLessonDao implements Dao<Integer, ScheduledLessonEntity> {
    private static final ScheduledLessonDao INSTANCE = new ScheduledLessonDao
            (SchoolSubjectDao.getInstance(),
                    ClassDao.getInstance());
    private final SchoolSubjectDao schoolSubjectDao;
    private final ClassDao classDao;


    private static final String FIND_ALL_SQL =
            "select public.scheduled_lesson.id as scheduled_lesson_id, " +
                    "public.scheduled_lesson.subject_id as subject_id," +
                    "public.scheduled_lesson.class_id as class_id," +
                    "public.scheduled_lesson.number_lesson, " +
                    "public.scheduled_lesson.day_of_week " +
                    "from public.scheduled_lesson ";

    private static final String FIND_BY_ID_SQL =
            FIND_ALL_SQL + " where public.scheduled_lesson.id = ? ";

    public static ScheduledLessonDao getInstance() {
        return INSTANCE;
    }

    private ScheduledLessonDao(SchoolSubjectDao schoolSubjectDao, ClassDao classDao) {
        this.schoolSubjectDao = schoolSubjectDao;
        this.classDao = classDao;
    }


    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public ScheduledLessonEntity save(ScheduledLessonEntity scheduledLessonEntity) {
        return null;
    }

    @Override
    public void update(ScheduledLessonEntity scheduledLessonEntity) {

    }

    @Override
    public Optional<ScheduledLessonEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(FIND_BY_ID_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ScheduledLessonEntity scheduledLessonEntity = null;
            if (resultSet.next()) {
                scheduledLessonEntity = buildScheduledLessonEntity(resultSet);
            }
            return Optional.ofNullable(scheduledLessonEntity);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public Optional<ScheduledLessonEntity> findById(Integer id, Connection connection) {
        return Optional.empty();
    }

    @Override
    public List<ScheduledLessonEntity> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(FIND_ALL_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ScheduledLessonEntity> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(buildScheduledLessonEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private ScheduledLessonEntity buildScheduledLessonEntity(ResultSet resultSet) throws SQLException {
        return new ScheduledLessonEntity(
                resultSet.getInt("scheduled_lesson_id"),
                schoolSubjectDao.findById(resultSet.getInt("subject_id"))
                        .orElseThrow(() -> new IllegalStateException("subject id is null")),
                classDao.findById(resultSet.getInt("class_id"))
                        .orElseThrow(() -> new IllegalStateException("class id is null")),
                resultSet.getInt("number_lesson"),
                resultSet.getInt("day_of_week"));
    }
}
