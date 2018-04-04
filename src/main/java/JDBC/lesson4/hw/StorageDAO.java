package JDBC.lesson4.hw;


import java.sql.*;
import java.util.Arrays;


public class StorageDAO {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.coh7tfxx59uf.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "11111111";


    public static void delete(long id) throws Exception {
        try (Connection connection = getConnection()) {
            delete(id, connection);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public static void delete(long id, Connection connection) throws SQLException {
        try (PreparedStatement statementStr = connection.prepareStatement("DELETE FROM STORAGES WHERE STORAGE_ID = ?")) {
            statementStr.setLong(1, id);
            statementStr.executeUpdate();
            System.out.println("storage with: " + id + "deleted ");
        } catch (SQLException e) {
            throw new SQLException(e.getMessage() + "Issue with storage ID: " + id);
        }
    }

    public static Storage save(Storage storage) throws Exception {
        try (Connection connection = getConnection()) {
            save(storage, connection);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return storage;
    }

    public static void save(Storage storage, Connection connection) throws Exception {

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO STORAGES VALUES (?, ?, ?, ?)")) {
            storagePrepared(preparedStatement, storage);
            preparedStatement.executeUpdate();
            System.out.println("storage with id: " + storage.getId() + "saved");

        } catch (SQLException e) {
            System.err.println("Something went wrong " + storage.getId());
            throw e;
        }
    }


    public static Storage update(Storage storage) throws Exception {
        try (Connection connection = getConnection()) {
            update(storage, connection);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return storage;
    }

    public static void update(Storage storage, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE STORAGES SET ID = ?, FORMATS = ?," +
                " COUNTRY = ?, STORAGE_SIZE = ? WHERE ID = ?")) {
            storagePrepared(preparedStatement, storage);
            preparedStatement.setLong(5, storage.getId());
            preparedStatement.executeUpdate();
            System.out.println("storage with id: " + storage.getId() + " updated");

        } catch (SQLException e) {
            System.err.println("Something went wrong in storage: " + storage.getId());
            throw e;
        }
    }

    public static Storage findById(long id) throws Exception {
        try (Connection connection = getConnection()) {
            return findById(id, connection);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }

    public static Storage findById(long id, Connection connection) throws SQLException {
        Storage storage = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM STORAGES WHERE ID = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                storage = new Storage(
                        resultSet.getLong(1),
                        resultSet.getString(2)
                                .replaceAll("\\[", "").replaceAll("\\]", "").split(","),
                        resultSet.getString(3),
                        resultSet.getLong(4));
            }

            preparedStatement.executeUpdate();
            System.out.println("storage with id: " + id + " found in the table");
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


