package lesson3.hw.testSpeed;

import lesson3.Product;

import java.sql.*;
import java.util.Random;

public class Solution {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.coh7tfxx59uf.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "11111111";
    long count = 0;

    public void testSavePerformance() {
        Random random = new Random();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TEST_SPEED VALUES (?, ?, ?)")) {
            preparedStatement.setLong(1, count++);
            preparedStatement.setString(2, "test");
            preparedStatement.setInt(3, random.nextInt(1000000000));

            int res = preparedStatement.executeUpdate();
            System.out.println("save finished with result " + res);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public void testDeleteByIdPerformance() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("DELETE FROM TEST_SPEED WHERE ID = ?")) {
            preparedStatement.setLong(1, count++);
            int res = preparedStatement.executeUpdate();
            System.out.println("save finished with result " + res);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }


    }

    public void testDeletePerformance() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("DELETE FROM TEST_SPEED WHERE ID <= 1000")) {
            int res = preparedStatement.executeUpdate();
            System.out.println("save finished with result " + res);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }


    public void testSelectByIdPerformance() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("SELECT * FROM TEST_SPEED WHERE ID = ?")) {
            preparedStatement.setLong(1, count++);
            int res = preparedStatement.executeUpdate();
            System.out.println("save finished with result " + res);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }


    }

    public void testSelectPerformance() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("SELECT * FROM TEST_SPEED WHERE ID <= 1000")) {
            int res = preparedStatement.executeUpdate();
            System.out.println("save finished with result " + res);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public void deleteAllDataInTable() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            int res = statement.executeUpdate("TRUNCATE TABLE TEST_SPEED");
            System.out.println(res);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

}
