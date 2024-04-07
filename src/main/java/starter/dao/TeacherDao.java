package starter.dao;

import starter.entity.ClassEntity;
import starter.entity.SexStatus;
import starter.entity.TeacherEntity;
import starter.exception.DaoException;
import starter.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeacherDao implements Dao<Integer, TeacherEntity> {

    private static final TeacherDao INSTANCE = new TeacherDao();
    private static final String DELETE_SQL = "delete from school.public.teacher where id = ? ";
    private static final String SAVE_SQL =
            "insert into school.public.teacher (name, father_name, surname, sex) " +
                    "VALUES (?,?,?,?) ";
    private static final String UPDATE_SQL = "update  school.public.teacher " +
            "set name = ?, father_name = ?, surname = ?, sex = ? " +
            "where id = ?";

    private static final String FIND_ALL_SQL =
            "select id, name, father_name, surname, sex  from public.teacher";

    private static final String FIND_BY_ID_SQL =
            FIND_ALL_SQL + " where school.public.teacher.id = ? ";

    public static TeacherDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            return result == 1;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    @Override
    public TeacherEntity save(TeacherEntity teacherEntity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setString(1, teacherEntity.getName());
            preparedStatement.setString(2, teacherEntity.getFatherName());
            preparedStatement.setString(3, teacherEntity.getSurname());
            preparedStatement.setObject(4, teacherEntity.getSex());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                teacherEntity.setId(generatedKeys.getInt("id"));
            }
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
        return teacherEntity;
    }

    @Override
    public void update(TeacherEntity teacherEntity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(UPDATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, teacherEntity.getName());
            preparedStatement.setString(2, teacherEntity.getFatherName());
            preparedStatement.setString(3, teacherEntity.getSurname());
            preparedStatement.setObject(4, teacherEntity.getSex());
            preparedStatement.setInt(5, teacherEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public Optional<TeacherEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(FIND_BY_ID_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            TeacherEntity teacherEntity = null;
            if (resultSet.next()) {
                teacherEntity = buildTeacherEntity(resultSet);
            }
            return Optional.ofNullable(teacherEntity);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<TeacherEntity> findById(Integer id, Connection connection) {
        return Optional.empty();
    }

    @Override
    public List<TeacherEntity> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(FIND_ALL_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TeacherEntity> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(buildTeacherEntity(resultSet));
            }
            return result;

        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    private static TeacherEntity buildTeacherEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String fatherName = resultSet.getString("father_name");
        String surname = resultSet.getString("surname");
        String sex = resultSet.getString("sex");
        return new TeacherEntity(id, name, fatherName, surname, SexStatus.byValue(sex));
    }
}
