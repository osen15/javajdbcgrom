package lesson4.hw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Service {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.coh7tfxx59uf.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "11111111";


    public static void put(Storage storage, File file) throws Exception {
        try (Connection connection = getConnection()) {
            transactionPut(storage, file, connection);


        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public static void delete(Storage storage, File file) throws Exception {
        try (Connection connection = getConnection()) {
            transactionDelete(storage, file, connection);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }


    public static void transferAll(Storage storageFrom, Storage storageTo) throws Exception {
        try (Connection connection = getConnection()) {
            transactionTransferAll(storageFrom, storageTo, connection);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }

    public static void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        try (Connection connection = getConnection()) {
            transactionTransferFile(storageFrom, storageTo, id, connection);

        } catch (SQLException e) {
            System.err.println("Something went wrong");
            e.printStackTrace();
        }
    }


    public static void transactionPut(Storage storage, File file, Connection connection) throws Exception {
        Storage storageRes;

        if (storage == null)
            throw new Exception("storage null");
        if (file == null)
            throw new Exception("file null");

        try {

            connection.setAutoCommit(false);
            storageRes = StorageDAO.findById(storage.getId());
            freePlaceForFile(storage, file.getId());
            formatSupport(storageRes.getFormatsSupported(), file.getFormat());
            FileDAO.save(file);

            connection.commit();
        } catch (
                SQLException e)

        {
            System.err.println("Something went wrong ");
            connection.rollback();

        }

    }


    public static void transactionDelete(Storage storage, File file, Connection connection) throws Exception {

        if (storage == null)
            throw new Exception("storage null");
        if (file == null)
            throw new Exception("file null");
        try {

            connection.setAutoCommit(false);
            StorageDAO.findById(storage.getId());  //Пошук стореджа по id
            for (File file1 : FileDAO.findAllFilesByStorageId(storage.getId())) { //Пошук потрібного файлу в стореджі
                if (file1.getId() == file.getId())
                    FileDAO.delete(file.getId()); // видалення файлу
            }

            connection.commit();
        } catch (SQLException e) {
            System.err.println("Something went wrong ");
            connection.rollback();
        }


    }

    public static void transactionTransferAll(Storage storageFrom, Storage storageTo, Connection connection) throws Exception {
        Storage storageToRes;
        if (storageFrom == null)
            throw new Exception("storageFrom null");
        if (storageTo == null)
            throw new Exception("storageTo null");
        try {
            connection.setAutoCommit(false);
            StorageDAO.findById(storageFrom.getId());  //Пошук стореджаФром по id
            storageToRes = StorageDAO.findById(storageTo.getId());    //Пошук стореджаДу по id

            if (freePlaceInStorage(storageTo, FileDAO.findAllFilesByStorageId(storageTo.getId())) //Перевірка чи є місце для файлів, якщо є, то виконувати
                    >= sizeOfAllFiles(FileDAO.findAllFilesByStorageId(storageFrom.getId()))) {

                for (File file : FileDAO.findAllFilesByStorageId(storageFrom.getId())) {
                    formatSupport(storageToRes.getFormatsSupported(), file.getFormat());
                    file.setStorageId(storageTo.getId());
                    FileDAO.update(file);

                }

            } else throw new Exception("no free space in storage: " + storageTo.getId());
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Something went wrong ");
            connection.rollback();
        }


    }

    public static void transactionTransferFile(Storage storageFrom, Storage storageTo, long id, Connection connection) throws Exception {
        Storage storage;
        if (storageFrom == null)
            throw new Exception("storageFrom null");
        if (storageTo == null)
            throw new Exception("storageTo null");
        try {
            connection.setAutoCommit(false);
            StorageDAO.findById(storageFrom.getId());  //Пошук стореджаФром по id
            storage = StorageDAO.findById(storageTo.getId());    //Пошук стореджаДу по id
            freePlaceForFile(storageTo, id);           //Перевірка на вільне місце в стореджі
            for (File file : FileDAO.findAllFilesByStorageId(storageFrom.getId())) {
                formatSupport(storage.getFormatsSupported(), file.getFormat());
                file.setStorageId(storageTo.getId());
                FileDAO.update(file);


            }


            connection.commit();
        } catch (SQLException e) {
            System.err.println("Something went wrong ");
            connection.rollback();
        }


    }

    private static void formatSupport(String[] supportedFormats, String fileFormat) throws Exception {
        for (String format : supportedFormats) {
            if (!format.equals(fileFormat))
                throw new Exception("wrong format of file");

        }

    }


    private static void freePlaceForFile(Storage storage, long id) throws Exception {
        if (storage.getStorageSize() - id < id)
            throw new Exception("there is no place in the storage with id " + storage.getId());

    }


    private static long freePlaceInStorage(Storage storage, List<File> files) {
        long sum = 0;
        for (File file : files) {
            sum += file.getId();
        }

        return storage.getStorageSize() - sum;
    }

    private static long sizeOfAllFiles(List<File> files) {
        long sum = 0;
        for (File file : files) {
            sum += file.getId();
        }

        return sum;
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


}
