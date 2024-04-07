package starter.dao;

import starter.dto.ClassEntityFilter;
import starter.entity.ClassEntity;
import starter.exception.DaoException;
import starter.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class ClassDao implements Dao<Integer, ClassEntity> {
    private static final ClassDao INSTANCE = new ClassDao(TeacherDao.getInstance());
    private final TeacherDao teacherDao;
    private static final String DELETE_SQL = "delete from public.class where id = ? ";
    private static final String SAVE_SQL =
            "insert into public.class (class_name, classroom_teacher) " +
                    "values (?,?) ";
    private static final String UPDATE_SQL = "update  public.class " +
            "set class_name = ?, classroom_teacher = ?" +
            "where id = ?";

    private static final String FIND_ALL_SQL =
            "select class.id as class_id, " +
                    "class.class_name, " +
                    "teacher.id as teacher_id," +
                    "teacher.name," +
                    "teacher.father_name," +
                    "teacher.surname, " +
                    "teacher.sex " +
                    "from public.class " +
                    "left join public.teacher " +
                    "on teacher.id = class.classroom_teacher";

    private static final String FIND_BY_ID_SQL =
            FIND_ALL_SQL + " where public.class.id = ? ";

    public static ClassDao getInstance() {
        return INSTANCE;
    }

    private ClassDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
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
    public ClassEntity save(ClassEntity classEntity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setString(1, classEntity.getClassName());
            preparedStatement.setObject(2, classEntity.getClassroomTeacher().getId());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                classEntity.setId(generatedKeys.getInt("id"));
            }
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
        return classEntity;
    }

    @Override
    public void update(ClassEntity classEntity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(UPDATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, classEntity.getClassName());
            preparedStatement.setObject(2, classEntity.getClassroomTeacher());
            preparedStatement.setInt(3, classEntity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<ClassEntity> findById(Integer id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(FIND_BY_ID_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ClassEntity classEntity = null;
            if (resultSet.next()) {
                classEntity = buildClassEntity(resultSet);
            }
            return Optional.ofNullable(classEntity);

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<ClassEntity> findById(Integer id, Connection connection) {
        return Optional.empty();
    }

    @Override
    public List<ClassEntity> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(FIND_ALL_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ClassEntity> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(buildClassEntity(resultSet));
            }
            return result;

        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    public List<ClassEntity> findAll(ClassEntityFilter filter) {
        List<Object> parameters = buildParameters(filter);

        String where = buildWhere(filter);

        String sql = FIND_ALL_SQL + where;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            List<ClassEntity> result = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(buildClassEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static List<Object> buildParameters(ClassEntityFilter filter) {
        List<Object> parameters = new ArrayList<>();
        if (filter.getClassName() != null) {
            parameters.add(filter.getClassName());
        }
        if (filter.getClassroomTeacher() != null) {
            parameters.add(filter.getClassroomTeacher());
        }
        if (filter.getLimit() != null) {
            parameters.add(filter.getLimit());
        }
        if (filter.getOffset() != null) {
            parameters.add(filter.getOffset());
        }
        return parameters;
    }

    private static String buildWhere(ClassEntityFilter filter) {
        List<String> whereSql = new ArrayList<>();
        if (filter.getClassName() != null) {
            whereSql.add("class_name = ?");
        }
        if (filter.getClassroomTeacher() != null) {
            whereSql.add("classroom_teacher = ?");
        }

        StringBuilder whereString = new StringBuilder();
        String condition = whereSql.stream().
                collect(joining(" AND ", "", ""));
        if (!condition.isEmpty()) {
            whereString.append(" WHERE ").append(condition);
        }
        if (filter.getLimit() != null) {
            whereString.append(" LIMIT ?");
        }
        if (filter.getOffset() != null) {
            whereString.append(" OFFSET ? ");
        }
        return whereString.toString();
    }

    private ClassEntity buildClassEntity(ResultSet resultSet) throws SQLException {
        return new ClassEntity(
                resultSet.getInt("class_id"),
                resultSet.getString("class_name"),
                teacherDao.findById(resultSet.getInt("teacher_id"))
                        .orElseThrow(() -> new IllegalStateException("teacher id is null"))
        );
    }
}

