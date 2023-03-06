package com.statkevich.receipttask.dao.sql;

import com.statkevich.receipttask.dao.api.DiscountCardDao;
import com.statkevich.receipttask.domain.DiscountCard;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlDiscountCardDao extends SqlBaseDao<DiscountCard, String> implements DiscountCardDao {
    private static final String READ_BY_NUMBER_QUERY = """
            SELECT card_number,discount
            from discount_cards
            WHERE card_number= ?;""";

    private static final String READ_BY_NUMBERS_QUERY = """
            SELECT card_number,discount
            from discount_cards
            WHERE card_number = any (?);""";

    public SqlDiscountCardDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected ResultSet getByKeysInternal(PreparedStatement preparedStatement, List<String> cardNumbers) throws SQLException {
        String[] cardNumbersArray = cardNumbers.toArray(String[]::new);
        Connection connection = preparedStatement.getConnection();
        Array cards = connection.createArrayOf("varchar", cardNumbersArray);
        preparedStatement.setArray(1, cards);
        return preparedStatement.executeQuery();
    }

    @Override
    protected ResultSet getInternal(PreparedStatement preparedStatement, String key) throws SQLException {
        preparedStatement.setString(1, key);
        return preparedStatement.executeQuery();
    }

    @Override
    protected DiscountCard buildEntity(ResultSet resultSet) throws SQLException {
        String cardNumber = resultSet.getString("card_number");
        BigDecimal discount = resultSet.getBigDecimal("discount");

        return new DiscountCard(cardNumber, discount);
    }

    @Override
    protected String getQuery() {
        return READ_BY_NUMBER_QUERY;
    }

    @Override
    protected String getByKeysQuery() {
        return READ_BY_NUMBERS_QUERY;
    }

}
