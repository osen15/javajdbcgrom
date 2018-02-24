package lesson4.hw;


import java.sql.*;
import java.util.Arrays;


public class StorageDAO {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.coh7tfxx59uf.eu-central-1.rds.amazonaws.com:1521:ORCL";

    private static final String USER = "main";
    private static final String PASS = "11111111";


    public void delete(long id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement statementStr = connection.prepareStatement("DELETE FROM STORAGES WHERE STORAGE_ID = ?")) {
            statementStr.setLong(1, id);
            int res = statementStr.executeUpdate();
            System.out.println("save finished " + res);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage() + "Issue with storage ID: " + id);
        }
    }


    public static void save(Storage storage) throws SQLException {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO STORAGES VALUES (?, ?, ?, ?)")) {

            storagePrepared(preparedStatement, storage);
            int res = preparedStatement.executeUpdate();
            System.out.println("save finished " + res);


        } catch (SQLException e) {
            System.err.println("Something went wrong " + storage.getId());
            throw e;
        }
    }

    public void update(Storage storage) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE STORAGES SET ID = ?, FORMATS = ?," +
                     " COUNTRY = ?, STORAGE_SIZE = ? WHERE ID = ?")) {
            storagePrepared(preparedStatement, storage);
            preparedStatement.setLong(5, storage.getId());
            int res = preparedStatement.executeUpdate();
            System.out.println("update finished " + res);

        } catch (SQLException e) {
            System.err.println("Something went wrong " + storage.getId());
            throw e;
        }
    }

    public static Storage findById(long id) throws SQLException {
        Storage storage = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM STORAGES WHERE ID = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                storage = new Storage(
                        resultSet.getLong(1),
                        resultSet.getString(2).split(" "),
                        resultSet.getString(3),
                        resultSet.getLong(4));
            }

            int res = preparedStatement.executeUpdate();
            System.out.println("storage with " + id + " found " + res);
            return storage;
        } catch (SQLException e) {
            System.err.println("Something went wrong in storage " + id);
            throw e;
        }

    }


    private static void storagePrepared(PreparedStatement preparedStatement, Storage storage) throws SQLException {

        try {
            preparedStatement.setLong(1, storage.getId());
            preparedStatement.setString(2, Arrays.toString(storage.getFormatsSupported()));
            preparedStatement.setString(3, storage.getStorageCountry());
            preparedStatement.setLong(4, storage.getStorageSize());
        } catch (SQLException e) {
            throw new SQLException();
        }

    }


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


}


