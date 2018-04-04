package JDBC.lesson2;

import java.sql.*;

public class Solution {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.coh7tfxx59uf.eu-central-1.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "11111111";

    public ResultSet getAllProducts() {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            return statement.executeQuery("SELECT * FROM PRODUCT");


        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getProductByPrice() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            return statement.executeQuery("SELECT * FROM PRODUCT WHERE PRICE <= 100");

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;

    }

    public ResultSet getProductsByDescription() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            return statement.executeQuery("SELECT * FROM PRODUCT WHERE LENGTH (DESCRIPTION) < 50");

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;

    }

    public void saveProduct() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("INSERT INTO PRODUCT VALUES (999, 'toy', 'for children', 60)");
            System.out.println(resultSet);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public void deleteProducts() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("DELETE FROM PRODUCT WHERE NAME='toy'");
            System.out.println(resultSet);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public void increasePrice() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("UPDATE PRODUCT SET PRICE = PRICE + 100  WHERE PRICE < 970");
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public void changeDescription() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE LENGTH(DESCRIPTION)>100");
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String description = resultSet.getString(3);
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PRODUCT SET DESCRIPTION = ? WHERE ID = ?");
                if (description.lastIndexOf(".") > 0) {
                    description.substring(0, description.lastIndexOf("."));
                    preparedStatement.setString(1, description);
                    preparedStatement.setLong(2, id);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

}



