package starter.dao;

import starter.entity.LessonEntity;
import starter.entity.ScheduledLessonEntity;
import starter.exception.DaoException;
import starter.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LessonDao implements Dao<Integer, LessonEntity> {


    private static final LessonDao INSTANCE = new LessonDao(ScheduledLessonDao.getInstance(), TeacherDao.getInstance());
    private final ScheduledLessonDao scheduledLessonDao;
    private final TeacherDao teacherDao;

    private static final String FIND_ALL_SQL =
            "select public.lesson.id as lesson_id, " +
                    "public.lesson.date," +
                    "public.lesson.teacher_id as teacher_id, " +
                    "public.lesson.lesson_table_id as scheduled_lesson_id " +
                    "from public.lesson";

    private static final String FIND_BY_ID_SQL =
            FIND_ALL_SQL + " where  lesson.id = ? ";

    public static LessonDao getInstance() {
        return INSTANCE;
    }

    private LessonDao(ScheduledLessonDao scheduledLessonDao,  TeacherDao teacherDao) {
        this.scheduledLessonDao = scheduledLessonDao;
        this.teacherDao = teacherDao;
    }



    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public LessonEntity save(LessonEntity lessonEntity) {
        return null;
    }

    @Override
    public void update(LessonEntity lessonEntity) {

    }

    @Override
    public Optional<LessonEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(FIND_BY_ID_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            LessonEntity lessonEntity = null;
            if (resultSet.next()) {
                lessonEntity = buildLessonEntity(resultSet);
            }
            return Optional.ofNullable(lessonEntity);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<LessonEntity> findById(Integer id, Connection connection) {
        return Optional.empty();
    }

    @Override
    public List<LessonEntity> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(FIND_ALL_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<LessonEntity> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(buildLessonEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private LessonEntity buildLessonEntity(ResultSet resultSet) throws SQLException {
        return new LessonEntity(
                resultSet.getInt("lesson_id"),
                resultSet.getTimestamp("date"),
                teacherDao.findById(resultSet.getInt("teacher_id"))
                        .orElseThrow(() -> new IllegalStateException("teacher id is null")),
                scheduledLessonDao.findById(resultSet.getInt("scheduled_lesson_id"))
                        .orElseThrow(() -> new IllegalStateException("scheduledLesson id is null")));
    }
}
