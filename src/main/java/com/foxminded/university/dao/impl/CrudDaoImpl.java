package com.foxminded.university.dao.impl;

import com.foxminded.university.dao.Connector;
import com.foxminded.university.dao.CrudDao;
import com.foxminded.university.dao.DataBaseRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public abstract class CrudDaoImpl<E> implements CrudDao<E, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrudDaoImpl.class);

    private static final BiConsumer<PreparedStatement, String> STRING_CONSUMER
            = (PreparedStatement pr, String param) -> {
        try {
            pr.setString(1, param);
        } catch (SQLException e) {
            LOGGER.error("Conflicting parameter in statement");
            throw new DataBaseRuntimeException("Conflicting parameter in statement", e);
        }
    };

    private static final BiConsumer<PreparedStatement, Integer> INT_CONSUMER
            = (PreparedStatement pr, Integer param) -> {
        try {
            pr.setInt(1, param);
        } catch (SQLException e) {
            LOGGER.error("Conflicting parameter in statement");
            throw new DataBaseRuntimeException("Conflicting parameter in statement", e);
        }
    };

    protected final Connector connector;
    private final String saveQuery;
    private final String findByIdQuery;
    private final String updateQuery;
    private final String deleteByIdQuery;

    public CrudDaoImpl(Connector connector, String saveQuery, String findByIdQuery,
                       String updateQuery, String deleteByIdQuery) {
        this.connector = connector;
        this.saveQuery = saveQuery;
        this.findByIdQuery = findByIdQuery;
        this.updateQuery = updateQuery;
        this.deleteByIdQuery = deleteByIdQuery;
    }

    @Override
    public void save(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveQuery)) {

            insert(preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Insertion has failed", e);
            throw new DataBaseRuntimeException("Insertion has failed", e);
        }
    }

    @Override
    public Optional<E> findById(Integer id) {
        List<E> elements = findByIntParam(id, findByIdQuery);
        return elements.isEmpty() ? Optional.empty() : Optional.ofNullable(elements.get(0));
    }

    @Override
    public void update(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            updateValues(preparedStatement, entity);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Update has failed", e);
            throw new DataBaseRuntimeException("Update has failed", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        deleteByIntParam(id, deleteByIdQuery);
    }

    protected List<E> findByIntParam(Integer id, String query) {
        return findByParam(id, query, INT_CONSUMER);
    }

    protected void deleteByIntParam(Integer id, String query) {
        deleteByParam(id, query, INT_CONSUMER);
    }

    protected List<E> findByStringParam(String param, String query) {
        return findByParam(param, query, STRING_CONSUMER);
    }

    private <P> List<E> findByParam(P param, String query, BiConsumer<PreparedStatement, P> consumer) {
        List<E> elements = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            consumer.accept(preparedStatement, param);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    elements.add(mapResultSetToEntity(resultSet));
                }
            }
            return elements;
        } catch (SQLException e) {
            LOGGER.error("Can't find element", e);
            throw new DataBaseRuntimeException("Can't find element", e);
        }
    }

    private <P> void deleteByParam(P param, String query, BiConsumer<PreparedStatement, P> consumer) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            consumer.accept(preparedStatement, param);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't find element", e);
            throw new DataBaseRuntimeException("Can't find element", e);
        }
    }

    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract void insert(PreparedStatement preparedStatement, E entity) throws SQLException;

    protected abstract void updateValues(PreparedStatement preparedStatement, E entity) throws SQLException;
}
