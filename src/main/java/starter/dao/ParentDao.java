package starter.dao;

import starter.entity.ParentEntity;
import starter.entity.SexStatus;
import starter.entity.TeacherEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ParentDao implements Dao<Integer, ParentEntity> {

    private static final ParentDao INSTANCE = new ParentDao();
    public static ParentDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public ParentEntity save(ParentEntity parentEntity) {
        return null;
    }

    @Override
    public void update(ParentEntity parentEntity) {

    }

    @Override
    public Optional<ParentEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<ParentEntity> findById(Integer id, Connection connection) {
        return Optional.empty();
    }

    @Override
    public List<ParentEntity> findAll() {
        return List.of();
    }

    private static ParentEntity buildParentEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String fatherName = resultSet.getString("father_name");
        String surname = resultSet.getString("surname");
        String sex = resultSet.getString("sex");
        String phoneNumber = resultSet.getString("phone_number");
        String email = resultSet.getString("email");
        return new ParentEntity(id, name, fatherName, surname, SexStatus.byValue(sex), phoneNumber, email);
    }
}
