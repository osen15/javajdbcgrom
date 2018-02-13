package lesson3.hw;

import lesson3.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.coh7tfxx59uf.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "11111111";

    public List<Product> findProductsByPrice(int price, int delta) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("SELECT * FROM PRODUCT WHERE PRICE >= ? AND PRICE <=?")) {
            preparedStatement.setInt(1, price - delta);
            preparedStatement.setInt(2, price + delta);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4));
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;

    }

    public List<Product> findProductsByName(String word) throws Exception {
        List<Product> products = new ArrayList<>();
        checkWord(word);
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM  PRODUCT");
            while (resultSet.next()) {
                int count = 0;
                Product product = new Product(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4));
                for (String srt : stringToArray(product.getName())) {
                    if (srt.equals(word))
                        count++;
                }
                if (count == 1)
                    products.add(product);
            }
            return products;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;

    }

    public List<Product> findProductsWithEmptyDescription() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT  * FROM  PRODUCT");
            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4));
                if (product.getDescription() == null) {
                    products.add(product);
                }
            }

            return products;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }


        return null;
    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private void checkWord(String word) throws Exception {
        for (char ch : word.toCharArray())
            if (!Character.isLetter(ch))
                throw new Exception("should only be letters " + word);
        if (word.length() < 3)
            throw new Exception("lenght too short " + word);
    }

    private String[] stringToArray(String string) {
        String[] strings = string.split(" ");
        return strings;
    }
}
