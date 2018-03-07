package lesson4.hw;

import lesson3.Product;

import java.sql.*;
import java.util.ArrayList;


public class FileDAO {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.coh7tfxx59uf.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "11111111";


    public static File save(File file) throws Exception {
        try (Connection connection = getConnection()) {
            return save(file, connection);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return  null;
    }

    public static File save(File file, Connection connection) throws Exception {

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO FILES VALUES (?, ?, ?, ?, ?)")) {
            filePrepared(preparedStatement, file);
            preparedStatement.executeUpdate();
            System.out.println("file with id: " + file.getId() + " saved");
        } catch (SQLException e) {
            System.err.println("Something went wrong " + file.getId());
            throw e;
        }

        return file;
    }


    public static void delete(long id) throws Exception {
        try (Connection connection = getConnection()) {
            delete(id, connection);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

    }


    public static void delete(long id, Connection connection) throws SQLException {
        try (PreparedStatement statementStr = connection.prepareStatement("DELETE FROM FILES WHERE ID = ?")) {
            statementStr.setLong(1, id);
            statementStr.executeUpdate();
            System.out.println("file with id: " + id + "deleted");
        } catch (SQLException e) {
            throw new SQLException("Something went wrong fileID: " + id);
        }
    }


    public static void update(File file) throws Exception {
        try (Connection connection = getConnection()) {
            update(file, connection);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }

    }


    public static void update(File file, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE FILES SET ID = ?, FILE_NAME = ?," +
                " FILE_FORMAT = ?, FILE_SIZE = ?, STORAGE_ID = ? WHERE ID = ?")) {

            filePrepared(preparedStatement, file);
            preparedStatement.setLong(6, file.getId());

            preparedStatement.executeUpdate();
            System.out.println("File with id: " + file.getId() + "updated");

        } catch (SQLException e) {
            System.err.println("Something went wrong " + file.getId());
            throw e;
        }
    }


    public static File findById(long id) throws Exception {
        try (Connection connection = getConnection()) {
            return findById(id, connection);
        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
        return null;
    }


    public static File findById(long id, Connection connection) throws Exception {
        File file = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM FILES WHERE FILE_ID = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                file = new File(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getLong(4),
                        resultSet.getLong(5));
            }

            int res = preparedStatement.executeUpdate();
            System.out.println("file with ID:" + id + " found. Result: " + res);
            return file;
        } catch (SQLException e) {
            System.err.println("Something went wrong " + id);
            throw e;
        }

    }


    public static ArrayList<File> findAllFilesByStorageId(long id,Connection connection) throws Exception {
        ArrayList<File> files = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FILES WHERE STORAGE_ID = ?")) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                File file = new File(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getLong(4),
                        resultSet.getLong(5));

                files.add(file);
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage() + "Something went wrong");
        }

        return files;
    }

    private static void filePrepared(PreparedStatement preparedStatement, File file) throws SQLException {

        try {
            preparedStatement.setLong(1, file.getId());
            preparedStatement.setString(2, file.getName());
            preparedStatement.setString(3, file.getFormat());
            preparedStatement.setLong(4, file.getSize());
            preparedStatement.setLong(5, file.getStorageId());
        } catch (SQLException e) {
            throw new SQLException();
        }

    }


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


}
