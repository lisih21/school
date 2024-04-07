package starter.dao;

import starter.entity.ActivityEntity;
import starter.entity.SchoolSubjectEntity;
import starter.exception.DaoException;
import starter.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SchoolSubjectDao implements Dao< Integer, SchoolSubjectEntity> {
    private static final SchoolSubjectDao INSTANCE = new SchoolSubjectDao();

    private static final String FIND_ALL_SQL = " select id, name" +
            " from school.public.school_subject ";

    private static final String FIND_BY_ID_SQL =
            FIND_ALL_SQL + " where school.public.school_subject.id = ? ";

    public static SchoolSubjectDao getInstance() {
        return INSTANCE;
    }


    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public SchoolSubjectEntity save(SchoolSubjectEntity schoolSubjectEntity) {
        return null;
    }

    @Override
    public void update(SchoolSubjectEntity schoolSubjectEntity) {

    }

    @Override
    public Optional<SchoolSubjectEntity> findById(Integer id) {

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(FIND_BY_ID_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            SchoolSubjectEntity schoolSubjectEntity = null;
            if (resultSet.next()) {
                schoolSubjectEntity = buildSchoolSubjectEntity(resultSet);
            }
            return Optional.ofNullable(schoolSubjectEntity);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<SchoolSubjectEntity> findById(Integer id, Connection connection) {
        return Optional.empty();
    }

    @Override
    public List<SchoolSubjectEntity> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(FIND_ALL_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<SchoolSubjectEntity> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(buildSchoolSubjectEntity(resultSet));
            }
            return result;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    private SchoolSubjectEntity buildSchoolSubjectEntity(ResultSet resultSet) throws SQLException {
        return new SchoolSubjectEntity(
                resultSet.getInt("id"),
                resultSet.getString("name"));
    }
}
