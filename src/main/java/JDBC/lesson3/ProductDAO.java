package JDBC.lesson3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    //CRUD
    //create, read, update, delete
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.coh7tfxx59uf.eu-central-1.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "11111111";

    public Product save(Product product) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCT VALUES (?, ?, ?, ?)")) {
            preparedStatement.setLong(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, product.getPrice());

            int res = preparedStatement.executeUpdate();
            System.out.println("save finished with result " + res);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return product;

    }

    public Product update(Product product) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("UPDATE PRODUCT SET NAME = ?, DESCRIPTION = ?, PRICE = ? WHERE ID = ?")) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, product.getPrice());
            preparedStatement.setLong(4, product.getId());

            int res = preparedStatement.executeUpdate();
            System.out.println("save finished with result " + res);
            return product;
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return null;

    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT  * FROM  PRODUCT");
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

    public Product delete(long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("DELETE FROM PRODUCT WHERE ID = ?")) {
            preparedStatement.setLong(1, id);
            int res = preparedStatement.executeUpdate();
            System.out.println("save finished with result " + res);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

        return null;

    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


    //  public static void main(String[] args) {
    //  try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
    //    Statement statement = connection.createStatement()) {

    // boolean res = statement.execute("INSERT INTO PRODUCT VALUES (3, 'toy7', 'for children', 20)");
    //  System.out.println(res);
    //  boolean res = statement.execute("DELETE FROM PRODUCT WHERE NAME ='toy'");
    //  System.out.println(res);
    //  int res = statement.executeUpdate("INSERT INTO PRODUCT VALUES (1, 'car', 'for children', 70)");
    //    System.out.println(res);
    // int res = statement.executeUpdate("DELETE FROM PRODUCT WHERE NAME ='car'");
    // System.out.println(res);


    // } catch (SQLException e) {
    //    System.err.println("Something went wrong");
    //    e.printStackTrace();
    // }
    // }
}
