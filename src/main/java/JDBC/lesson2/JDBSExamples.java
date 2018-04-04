package JDBC.lesson2;

import java.sql.*;

public class JDBSExamples {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.coh7tfxx59uf.eu-central-1.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "11111111";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            // boolean res = statement.execute("INSERT INTO PRODUCT VALUES (3, 'toy7', 'for children', 20)");
            //  System.out.println(res);
            //  boolean res = statement.execute("DELETE FROM PRODUCT WHERE NAME ='toy'");
            //  System.out.println(res);
            //  int res = statement.executeUpdate("INSERT INTO PRODUCT VALUES (1, 'car', 'for children', 70)");
            //    System.out.println(res);
            int res = statement.executeUpdate("DELETE FROM PRODUCT WHERE NAME ='car'");
            System.out.println(res);


        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }
}
