package starter.dao;

import starter.entity.ActivityEntity;
import starter.entity.ClassEntity;
import starter.entity.TeacherEntity;
import starter.exception.DaoException;
import starter.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ActivityDao implements Dao<Integer, ActivityEntity> {
    private static final ActivityDao INSTANCE = new ActivityDao();
    private static final String FIND_ALL_SQL = " select id, name" +
            " from public.activity ";

    private static final String FIND_BY_ID_SQL =
            FIND_ALL_SQL + " where school.public.activity.id = ? ";

    public static ActivityDao getInstance() {
        return INSTANCE;
    }


    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public ActivityEntity save(ActivityEntity activityEntity) {
        return null;
    }

    @Override
    public void update(ActivityEntity activityEntity) {

    }

    @Override
    public Optional<ActivityEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(FIND_BY_ID_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ActivityEntity activityEntity = null;
            if (resultSet.next()) {
                activityEntity = buildActivityEntity(resultSet);
            }
            return Optional.ofNullable(activityEntity);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<ActivityEntity> findById(Integer id, Connection connection) {
        return Optional.empty();
    }

    @Override
    public List<ActivityEntity> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(FIND_ALL_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ActivityEntity> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(buildActivityEntity(resultSet));
            }
            return result;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private ActivityEntity buildActivityEntity(ResultSet resultSet) throws SQLException {
        return new ActivityEntity(
                resultSet.getInt("id"),
                resultSet.getString("name"));
    }

}
