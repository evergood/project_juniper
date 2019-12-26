package com.foxminded.university.dao.impl;

import com.foxminded.university.dao.Connector;
import com.foxminded.university.dao.DataBaseRuntimeException;
import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.entity.Group;
import com.foxminded.university.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentDaoImpl extends CrudDaoImpl<Student> implements StudentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentDaoImpl.class);

    private static final String SAVE_QUERY = "INSERT INTO students (student_id, first_name, last_name, group_id)" +
            " values(?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM students WHERE student_id = ?";
    private static final String UPDATE_QUERY = "UPDATE students SET student_id =?, first_name=?," +
            "last_name=?, group_id=? WHERE student_id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM students WHERE student_id = ?";
    private static final String FIND_BY_COURSENAME_QUERY = "SELECT students.student_id, students.first_name," +
            "students.last_name,  students.group_id\n" +
            "FROM courses JOIN studentcourses  ON courses.course_id = studentcourses.course_id\n" +
            "JOIN students ON studentcourses.student_id = students.student_id\n" +
            "WHERE courses.course_name = ?\n" +
            "ORDER BY students.student_id; ";
    private static final String ADD_STUDENT_TO_COURSE_QUERY = "INSERT INTO studentcourses " +
            "SELECT  ?, courses.course_id FROM courses WHERE courses.course_name = ?;";
    private static final String DELETE_STUDENT_FROM_COURSE_QUERY = "DELETE FROM  studentcourses \n" +
            "WHERE student_id = ? and course_id =" +
            "(SELECT courses.course_id FROM courses WHERE courses.course_name = ?);";

    public StudentDaoImpl(Connector connector) {
        super(connector, SAVE_QUERY, FIND_BY_ID_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    protected Student mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Student.Builder()
                .withId(resultSet.getInt(1))
                .withFirstName(resultSet.getString(2))
                .withLastName(resultSet.getString(3))
                .withGroup(new Group(resultSet.getInt(4)))
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Student entity) throws SQLException {
        preparedStatement.setInt(1, entity.getStudentId());
        preparedStatement.setString(2, entity.getFirstName());
        preparedStatement.setString(3, entity.getLastName());
        preparedStatement.setObject(4, entity.getGroup() == null ? null : entity.getGroup().getGroupId());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, Student entity) throws SQLException {
        preparedStatement.setInt(1, entity.getStudentId());
        preparedStatement.setString(2, entity.getFirstName());
        preparedStatement.setString(3, entity.getLastName());
        preparedStatement.setObject(4, entity.getGroup() == null ? null : entity.getGroup().getGroupId());
        preparedStatement.setInt(5, entity.getStudentId());
    }

    @Override
    public List<Student> findStudentsByCourseName(String courseName) {
        return findByStringParam(courseName, FIND_BY_COURSENAME_QUERY);
    }

    @Override
    public void addStudentToCourse(Integer studentId, String courseName) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_STUDENT_TO_COURSE_QUERY)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, courseName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Insertion is failed", e);
            throw new DataBaseRuntimeException("Insertion is failed", e);
        }
    }

    @Override
    public void deleteStudentFromCourse(Integer studentId, String courseName) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT_FROM_COURSE_QUERY)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, courseName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Deletion is failed", e);
            throw new DataBaseRuntimeException("Deletion is failed", e);
        }
    }
}
