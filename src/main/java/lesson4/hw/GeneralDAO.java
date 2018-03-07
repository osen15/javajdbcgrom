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


    private static void put(Storage storage, File file, Connection connection) throws Exception {
        long fileIDInStorage;

        try {
            connection.setAutoCommit(false);
            fileIDInStorage = FileDAO.findById(file.getId(), connection).getId();
            storage = StorageDAO.findById(storage.getId(), connection);
            if (fileIDInStorage != file.getId())
                for (String format : storage.getFormatsSupported()) {
                    if (format.equals(file.getFormat()) &&
                            Utils.freePlaceInStorage(storage, FileDAO.findAllFilesByStorageId(storage.getId(), connection))
                                    >= file.getSize()) {
                        FileDAO.save(file, connection);
                    }
                }

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
        long storageFromId;
        long storageToId;

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            storageFromId = StorageDAO.findById(storageFrom.getId(), connection).getId();
            storageToId = StorageDAO.findById(storageTo.getId(), connection).getId();

            if (Utils.freePlaceInStorage(storageTo, FileDAO.findAllFilesByStorageId(storageToId, connection)) <
                    Utils.sizeOfAllFilesinStorage(FileDAO.findAllFilesByStorageId(storageFromId, connection)))

                throw new Exception("storage with id: " + storageToId + " no free space");
            for (File file : FileDAO.findAllFilesByStorageId(storageFromId, connection)) {
                Utils.formatSupport(StorageDAO.findById(storageToId, connection).getFormatsSupported(), file.getFormat());
                file.setStorageId(storageToId);
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
            StorageDAO.findById(storageFrom.getId(), connection);
            storageTo = StorageDAO.findById(storageTo.getId(), connection);
            File file = FileDAO.findById(id, connection);

            if (Utils.freePlaceInStorage(storageTo, FileDAO.findAllFilesByStorageId(storageTo.getId(), connection)) >= file.getSize())
                for (String format : storageTo.getFormatsSupported()) {
                    if (file.getFormat().equals(format)) {
                        file.setStorageId(storageTo.getId());
                        FileDAO.update(file, connection);
                    }
                }


            connection.commit();

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            throw e;
        }

    }


    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}