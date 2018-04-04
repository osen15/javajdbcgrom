package JDBC.lesson2;

import java.sql.*;
import java.util.Date;

public class JDBCFirstStep {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.coh7tfxx59uf.eu-central-1.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "11111111";

    public static void main(String[] args) {


        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {
            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                System.out.println("Class " + JDBC_DRIVER + " not found");
            }
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Orders")) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    String productName = resultSet.getString(2);
                    int price = resultSet.getInt(3);
                    Date dateOrdered = resultSet.getDate(4);
                    Date dateConfirmed = resultSet.getDate(5);
                    Order order = new Order(id, productName, price, dateOrdered, dateConfirmed);
                    System.out.println(order);
                }
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

    }
}
