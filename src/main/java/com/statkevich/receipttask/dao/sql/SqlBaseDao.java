package com.statkevich.receipttask.dao.sql;

import com.statkevich.receipttask.dao.api.BaseDao;
import com.statkevich.receipttask.exceptions.DataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SqlBaseDao<E, K> implements BaseDao<E, K> {

    private final DataSource dataSource;

    public SqlBaseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public E get(K key) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getQuery());
             ResultSet resultSet = getInternal(preparedStatement, key)) {

            boolean hasNext = resultSet.next();
            if (hasNext) {
                return buildEntity(resultSet);
            } else {
                throw new DataAccessException("Entity with key " + key + " not found");
            }
        } catch (SQLException e) {
            throw new DataAccessException("SQLException Get method :" + String.join("\n",
                    Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n"))));
        }
    }

    @Override
    public List<E> getByKeys(List<K> keyList) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getByKeysQuery());
             ResultSet resultSet = getByKeysInternal(preparedStatement, keyList)) {
            ArrayList<E> entityList = new ArrayList<>();
            while (resultSet.next()) {
                E entity = buildEntity(resultSet);
                entityList.add(entity);
            }
            return entityList;
        } catch (SQLException e) {
            throw new DataAccessException("SQLException GetByKeys method :" + e);
        }
    }

    protected abstract ResultSet getByKeysInternal(PreparedStatement preparedStatement, List<K> keyList) throws SQLException;

    protected abstract ResultSet getInternal(PreparedStatement preparedStatement, K key) throws SQLException;

    protected abstract E buildEntity(ResultSet resultSet) throws SQLException;

    protected abstract String getQuery();

    protected abstract String getByKeysQuery();

}
