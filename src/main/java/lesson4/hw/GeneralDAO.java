package lesson4.hw;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public static void transferAll(Storage storageFrom, Storage storageTo) throws Exception {
        try (Connection connection = getConnection()) {
            transferAll(storageFrom, storageTo, connection);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public static void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        try (Connection connection = getConnection()) {
            transferFile(storageFrom, storageTo, id, connection);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }


    private static void put(Storage storage, File file, Connection connection) throws Exception {
        try {
            connection.setAutoCommit(false);
            Utils.freePlaceForFile(StorageDAO.findById(storage.getId(), connection), file);
            if (FileDAO.findById(file.getId(), connection) != null)
                throw new Exception("file with id: " + file.getId() + " already exists");
            FileDAO.save(file);

            connection.commit();

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            connection.rollback();
            e.printStackTrace();
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
            e.printStackTrace();
        }

    }

    private static void transferAll(Storage storageFrom, Storage storageTo, Connection connection) throws Exception {
        long storageFromId;
        long storageToId;

        try {
            connection.setAutoCommit(false);
            storageFromId = StorageDAO.findById(storageFrom.getId(), connection).getId();
            storageToId = StorageDAO.findById(storageTo.getId(), connection).getId();

            if (Utils.sizeOfAllFiles(FileDAO.findAllFilesByStorageId(storageFromId, connection)) >
                    StorageDAO.findById(storageToId, connection).getStorageSize())
                throw new Exception("storage with id: " + storageToId + " no free space");
            for (File file : FileDAO.findAllFilesByStorageId(storageFromId, connection)) {
                Utils.formatSupport(StorageDAO.findById(storageToId, connection).getFormatsSupported(), file.getFormat());
                file.setStorageId(storageToId);
                FileDAO.update(file, connection);
            }


            connection.commit();

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            connection.rollback();
            e.printStackTrace();
        }

    }

    private static void transferFile(Storage storageFrom, Storage storageTo, long id, Connection connection) throws Exception {


        try {
            connection.setAutoCommit(false);
            StorageDAO.findById(storageFrom.getId(), connection);
            storageTo = StorageDAO.findById(storageTo.getId(), connection);
            File file = FileDAO.findById(id, connection);

            Utils.freePlaceForFile(StorageDAO.findById(storageTo.getId(), connection), file);
            for (String format : storageTo.getFormatsSupported()) {
                if (file.getFormat().equals(format)) {
                    file.setStorageId(storageTo.getId());
                    FileDAO.update(file, connection);
                }
            }


            connection.commit();

        } catch (
                SQLException e)

        {
            System.err.println("Something went wrong");
            connection.rollback();
            e.printStackTrace();
        }

    }


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}