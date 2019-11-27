package com.foxminded.university.dao.impl;

import com.foxminded.university.dao.Connector;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.entity.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GroupDaoImpl extends CrudDaoImpl<Group> implements GroupDao {

    private static final String SAVE_QUERY = "INSERT INTO groups (group_id, group_name) VALUES (?, ?);";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM groups WHERE group_id = ?";
    private static final String UPDATE_QUERY = "UPDATE groups SET group_name = ? WHERE group_id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM groups WHERE group_id = ?";
    private static final String FIND_GROUPS_BY_STUDENT_COUNT = "SELECT counting.id, counting.group_name, " +
            "COUNT(*) as count \n" +
            "FROM (\n" +
            "SELECT groups.group_id as id, groups.group_name, students.group_id\n" +
            "FROM groups \n" +
            "JOIN students ON groups.group_id = students.group_id\n" +
            "WHERE students.group_id IS NOT NULL) AS counting\n" +
            "GROUP BY counting.id, counting.group_name\n" +
            "HAVING count > ?\n" +
            "ORDER BY id;";

    public GroupDaoImpl(Connector connector) {
        super(connector, SAVE_QUERY, FIND_BY_ID_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    protected Group mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return new Group(resultSet.getInt(1), resultSet.getString(2));
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Group entity) throws SQLException {
        preparedStatement.setInt(1, entity.getGroupId());
        preparedStatement.setString(2, entity.getGroupName());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, Group entity) throws SQLException {
        preparedStatement.setString(1, entity.getGroupName());
        preparedStatement.setInt(2, entity.getGroupId());
    }

    @Override
    public List<Group> findGroupsByStudentsCount(Integer count) {
        return findByIntParam(count, FIND_GROUPS_BY_STUDENT_COUNT);
    }
}
