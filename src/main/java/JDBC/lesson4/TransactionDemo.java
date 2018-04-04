package JDBC.lesson4;

import JDBC.lesson3.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TransactionDemo {

    //CRUD
    //create, read, update, delete
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.coh7tfxx59uf.eu-central-1.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "11111111";

    public  void save(List<Product> products){
        try (Connection connection = getConnection()){
            saveList(products, connection);

        }catch (SQLException e){
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private void saveList(List<Product> products, Connection connection) throws SQLException {
        long productId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCT VALUES (?, ?, ?, ?)")) {
            connection.setAutoCommit(false);

            for (Product product : products) {
                preparedStatement.setLong(1, product.getId());
                preparedStatement.setString(2, product.getName());
                preparedStatement.setString(3, product.getDescription());
                preparedStatement.setInt(4, product.getPrice());
                productId = product.getId();
                int res = preparedStatement.executeUpdate();
                System.out.println("save finished with result " + res);
            }
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Something went wrong " + productId);
           connection.rollback();
            throw  e;
        }
    }






    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}


