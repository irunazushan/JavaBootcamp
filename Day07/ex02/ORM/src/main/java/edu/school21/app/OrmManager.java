package edu.school21.app;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;
import org.reflections.Reflections;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class OrmManager {
    private final DataSource dataSource;

    public OrmManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initialize() throws SQLException {
        Reflections reflections = new Reflections("edu.school21.models");
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(OrmEntity.class);

        for (Class<?> aClass : annotatedClasses) {
            String tableName = getTableName(aClass);
            StringJoiner createTableQuery = new StringJoiner(", ", "DROP TABLE IF EXISTS " + tableName + "; CREATE TABLE " + tableName + " (", ");");
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    createTableQuery.add("id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY");
                } else if (field.isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    String columnName = ormColumn.name();
                    int length = ormColumn.length();
                    String sqlType = getSqlType(field.getType(), length);
                    createTableQuery.add(columnName + " " + sqlType);
                }
            }
            System.out.println("\ncreated SQL script:\n" + createTableQuery + "\n");
            executeSQL(createTableQuery.toString());
        }
    }

    private String getTableName(Class<?> aClass) {
        if (aClass.isAnnotationPresent(OrmEntity.class)) {
            OrmEntity ormEntity = aClass.getAnnotation(OrmEntity.class);
            return ormEntity.table();
        }
        throw new IllegalArgumentException("Provided class is not an ORM Entity");
    }

    private void executeSQL(String query) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            if (ps.execute()) {
                System.out.println("Table created!");
            }
        }
    }

    private static String getSqlType(Class<?> fieldType, int length) {
        if (fieldType == String.class) {
            return "VARCHAR(" + length + ")";
        } else if (fieldType == int.class || fieldType == Integer.class) {
            return "INT";
        } else if (fieldType == long.class || fieldType == Long.class) {
            return "BIGINT";
        } else if (fieldType == double.class || fieldType == Double.class) {
            return "NUMERIC";
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            return "BOOLEAN";
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType.getName());
        }
    }

    public void save(Object entity) throws IllegalAccessException, SQLException {
        Map<String, Object> columnValues = new HashMap<>();
        String query = getQueryForSave(entity, columnValues);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            for (Object value : columnValues.values()) {
                ps.setObject(index++, value);
            }
            if (ps.executeUpdate() > 0) {
                System.out.println("New data inserted to db successfully");
            }
            Field IdField;
            try {
                IdField = entity.getClass().getDeclaredField("id");
                IdField.setAccessible(true);
                IdField.set(entity, getLastMessageId(getTableName(entity.getClass())));
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("This entity doesn't contain an 'id' field");
            }
        }
    }

    private String getQueryForSave(Object entity, Map<String, Object> columnValues) throws IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumn.class)) {
                field.setAccessible(true);
                OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                String columnName = ormColumn.name();
                Object value = field.get(entity);
                columnValues.put(columnName, value);
            }
        }
        StringJoiner insertQuery = new StringJoiner(
                ", ", "INSERT INTO " + getTableName(entity.getClass()) + " (", ") ");
        StringJoiner valuesPart = new StringJoiner(
                ", ", "VALUES (", ");");

        for (String column : columnValues.keySet()) {
            insertQuery.add(column);
            valuesPart.add("?");
        }

        return insertQuery.toString() + valuesPart.toString();
    }

    private long getLastMessageId(String tableName) {
        String query = "SELECT id from " + tableName + " ORDER BY id DESC LIMIT 1";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("id");
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    public void update(Object entity) throws IllegalAccessException, SQLException {
        Map<String, Object> columnValues = new HashMap<>();
        String query = getQueryForUpdate(entity, columnValues);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            int index = 1;
            for (Object value : columnValues.values()) {
                ps.setObject(index++, value);
            }
            if (ps.executeUpdate() > 0) {
                System.out.println("DB updated successfully");
            }
        }
    }

    private String getQueryForUpdate(Object entity, Map<String, Object> columnValues) throws IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();
        Long objId = -1L;
        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumnId.class)) {
                field.setAccessible(true);
                objId = (Long)field.get(entity);
            }
            else if (field.isAnnotationPresent(OrmColumn.class)) {
                field.setAccessible(true);
                OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                String columnName = ormColumn.name();
                Object value = field.get(entity);
                columnValues.put(columnName, value);
            }
        }

        StringJoiner query = new StringJoiner(", ", "UPDATE " + getTableName(entity.getClass()) + " SET ", " WHERE id=" + objId);
        for (String column : columnValues.keySet()) {
            query.add(column + "=?");
        }
        return query.toString();
    }

    public <T> T findById(Long id, Class<T> aClass) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String query = "SELECT * FROM " + getTableName(aClass) + " WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getInstance(rs, aClass);

            }
        }
        throw new RuntimeException("User not found");
    }

    private <T> T  getInstance(ResultSet rs, Class<T> aClass) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Field[] fields = aClass.getDeclaredFields();
        Class<?>[] fieldTypes = new Class[fields.length];
        Object[] values = new Object[fields.length];

        for (int i = 0; i < fieldTypes.length; i++) {
            if (fields[i].isAnnotationPresent(OrmColumnId.class)) {
                fieldTypes[i] = fields[i].getType();
                values[i] = rs.getObject("id");
            }
            else if (fields[i].isAnnotationPresent(OrmColumn.class)) {
                fields[i].setAccessible(true);
                OrmColumn ormColumn = fields[i].getAnnotation(OrmColumn.class);
                String columnName = ormColumn.name();
                fieldTypes[i] = fields[i].getType();
                values[i] = rs.getObject(columnName);
            }
        }
        return aClass.getConstructor(fieldTypes)
                .newInstance(values);
    }
}
