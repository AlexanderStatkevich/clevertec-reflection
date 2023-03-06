package com.statkevich.receipttask.dao.sql;

import com.statkevich.receipttask.dao.api.ProductDao;
import com.statkevich.receipttask.domain.CommonProduct;
import com.statkevich.receipttask.domain.SaleType;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SqlProductDao extends SqlBaseDao<CommonProduct, Long> implements ProductDao {

    private static final String READ_BY_ID_QUERY = """
            SELECT id,name,price,sale_types
            from products
            WHERE id = ? ;""";

    private static final String READ_BY_IDS_QUERY = """
            SELECT id,name,price,sale_types
            from products
            WHERE id = any (?);""";

    public SqlProductDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected ResultSet getByKeysInternal(PreparedStatement preparedStatement, List<Long> keyList) throws SQLException {
        Object[] keyArray = keyList.toArray();
        Connection connection = preparedStatement.getConnection();
        Array keys = connection.createArrayOf("bigint", keyArray);
        preparedStatement.setArray(1, keys);
        return preparedStatement.executeQuery();
    }

    @Override
    protected ResultSet getInternal(PreparedStatement preparedStatement, Long key) throws SQLException {
        preparedStatement.setLong(1, key);
        return preparedStatement.executeQuery();
    }

    @Override
    protected CommonProduct buildEntity(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        BigDecimal price = resultSet.getBigDecimal("price");
        Object sale_types = resultSet.getArray("sale_types").getArray();
        String[] saleTypesArray = (String[]) sale_types;
        Set<SaleType> saleTypes = Arrays.stream(saleTypesArray)
                .map(SaleType::valueOf)
                .collect(Collectors.toSet());

        return CommonProduct.builder()
                .setId(id)
                .setName(name)
                .setPrice(price)
                .setSaleType(saleTypes)
                .build();
    }

    @Override
    protected String getQuery() {
        return READ_BY_ID_QUERY;
    }

    @Override
    protected String getByKeysQuery() {
        return READ_BY_IDS_QUERY;
    }
}
