package JDBC.lesson4.hw;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class GeneralDAO {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.coh7tfxx59uf.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "11111111";

    public static void put(Storage storage, File file) throws Exception {
        try (Connection connection = getConnection()) {
            put(storage, file, connection);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public static void delete(Storage storage, File file) throws Exception {
        try (Connection connection = getConnection()) {
            delete(storage, file, connection);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }


    private static void put(Storage storage, File file, Connection connection) throws Exception {


        try {
            connection.setAutoCommit(false);
            file = FileDAO.findById(file.getId(), connection);
            storage = StorageDAO.findById(storage.getId(), connection);
            validation(storage, file, connection);
            FileDAO.save(file, connection);


            connection.commit();

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            connection.rollback();
            throw e;
        }

    }


    private static void delete(Storage storage, File file, Connection connection) throws Exception {
        try {
            connection.setAutoCommit(false);

            if (StorageDAO.findById(storage.getId(), connection) != null)

                FileDAO.delete(FileDAO.findById(file.getId(), connection).getId());

            connection.commit();

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            connection.rollback();
            throw e;
        }

    }

    public static void transferAll(Storage storageFrom, Storage storageTo) throws Exception {


        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            storageFrom = StorageDAO.findById(storageFrom.getId(), connection);

            storageTo = StorageDAO.findById(storageTo.getId(), connection);

            ArrayList<File> filesFrom = FileDAO.findAllFilesByStorageId(storageFrom.getId(), connection);

            ArrayList<File> filesTo = FileDAO.findAllFilesByStorageId(storageTo.getId(), connection);


            if (Utils.freePlaceInStorage(storageTo, filesTo) <
                    Utils.sizeOfAllFilesinStorage(filesFrom))
                throw new Exception("storage with id: " + storageTo.getId() + " no free space");

            for (File file : filesFrom){
                Utils.formatSupport(storageTo.getFormatsSupported(), file.getFormat());
            }



            for (File file : filesFrom) {
                file.setStorageId(storageTo.getId());
                FileDAO.update(file, connection);
            }


            connection.commit();

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            throw e;
        }

    }

    public static void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {


        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            storageTo = StorageDAO.findById(storageTo.getId(), connection);
            File file = FileDAO.findById(id, connection);
            validation(storageTo, file, connection);
            file.setStorageId(storageTo.getId());
            FileDAO.update(file, connection);

            connection.commit();

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            throw e;
        }

    }


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private static void validation(Storage storage, File file, Connection connection) throws Exception {
        if (file.getStorageId() == storage.getId())
            throw new Exception("file with ID: " + file.getStorageId() + " already exists");
        Utils.formatSupport(storage.getFormatsSupported(), file.getFormat());
        if (Utils.freePlaceInStorage(storage, FileDAO.findAllFilesByStorageId(storage.getId(), connection)) < file.getSize())
            throw new Exception("there is no free space in the storage with ID: " + storage.getId());


    }
}