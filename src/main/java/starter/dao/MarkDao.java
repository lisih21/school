package starter.dao;


import starter.entity.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MarkDao implements Dao<Integer, MarkEntity> {

    private static final MarkDao INSTANCE = new MarkDao(StudentDao.getInstance(), LessonDao.getInstance(), ActivityDao.getInstance());
    private final StudentDao studentDao;
    private final LessonDao lessonDao;
    private final ActivityDao activityDao;

    public static MarkDao getInstance() {
        return INSTANCE;
    }

    private MarkDao(StudentDao studentDao, LessonDao lessonDao, ActivityDao activityDao) {
        this.studentDao = studentDao;
        this.lessonDao = lessonDao;
        this.activityDao = activityDao;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public MarkEntity save(MarkEntity markEntity) {
        return null;
    }

    @Override
    public void update(MarkEntity markEntity) {

    }

    @Override
    public Optional<MarkEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<MarkEntity> findById(Integer id, Connection connection) {
        return Optional.empty();
    }

    @Override
    public List<MarkEntity> findAll() {
        return List.of();
    }

    private  MarkEntity buildMarkEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        StudentEntity studentEntity = studentDao.findById(resultSet.getInt("student_id"))
                .orElseThrow(() -> new IllegalStateException("student id is null"));
        LessonEntity lessonEntity = lessonDao.findById(resultSet.getInt("lesson_id"))
                .orElseThrow(() -> new IllegalStateException("lesson id is null"));
        ActivityEntity activityEntity = activityDao.findById(resultSet.getInt("activity_id"))
                .orElseThrow(() -> new IllegalStateException("activity id is null"));
        Integer grade = resultSet.getInt("grade");




        return new MarkEntity(id, studentEntity, lessonEntity, activityEntity, grade);
    }
}
