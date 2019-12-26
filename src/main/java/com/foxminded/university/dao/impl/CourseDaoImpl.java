package com.foxminded.university.dao.impl;

import com.foxminded.university.dao.Connector;
import com.foxminded.university.entity.Course;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDaoImpl extends CrudDaoImpl<Course> {

    private static final String SAVE_QUERY = "INSERT INTO courses (course_id, course_name, " +
            "course_description) VALUES (?,?,?);";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM courses WHERE course_id = ?";
    private static final String UPDATE_QUERY = "UPDATE courses SET course_name = ?, course_description = ? " +
            "WHERE course_id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM courses WHERE course_id = ?";

    public CourseDaoImpl(Connector connector) {
        super(connector, SAVE_QUERY, FIND_BY_ID_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    protected Course mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Course.builder()
                .withCourseId(resultSet.getInt(1))
                .withCourseName(resultSet.getString(2))
                .withCourseDescription(resultSet.getString(3))
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Course entity) throws SQLException {
        preparedStatement.setInt(1, entity.getCourseId());
        preparedStatement.setString(2, entity.getCourseName());
        preparedStatement.setString(3, entity.getCourseDescription());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, Course entity) throws SQLException {
        preparedStatement.setString(1, entity.getCourseName());
        preparedStatement.setString(2, entity.getCourseDescription());
        preparedStatement.setInt(3, entity.getCourseId());
    }
}
