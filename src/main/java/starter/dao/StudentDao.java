package starter.dao;

import starter.entity.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentDao implements Dao<Integer, StudentEntity> {
    private final ClassDao classDao;
    private final ParentDao parentDao;
    private static final StudentDao INSTANCE = new StudentDao(ClassDao.getInstance(), ParentDao.getInstance());

    public static StudentDao getInstance() {
        return INSTANCE;
    }
    private StudentDao( ClassDao classDao, ParentDao parentDao) {
        this.classDao = classDao;
        this.parentDao=parentDao;
    }


    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public StudentEntity save(StudentEntity studentEntity) {
        return null;
    }

    @Override
    public void update(StudentEntity studentEntity) {

    }

    @Override
    public Optional<StudentEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<StudentEntity> findById(Integer id, Connection connection) {
        return Optional.empty();
    }

    @Override
    public List<StudentEntity> findAll() {
        return List.of();
    }

    private StudentEntity buildStudentEntity(ResultSet resultSet) throws SQLException {

        Integer id = resultSet.getInt("student_id");
        ClassEntity classEntity = classDao.findById(resultSet.getInt("class_id"))
                .orElseThrow(() -> new IllegalStateException("class id is null"));
        String name = resultSet.getString("name");
        String fatherName = resultSet.getString("father_name");
        String surname = resultSet.getString("surname");
        String sex = resultSet.getString("sex");
        ParentEntity parentEntity = parentDao.findById(resultSet.getInt("parent_id"))
                .orElseThrow(() -> new IllegalStateException("parent id is null"));

        return new StudentEntity(id,classEntity, name, fatherName, surname, SexStatus.byValue(sex), parentEntity);

    }
}
