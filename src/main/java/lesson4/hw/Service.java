package lesson4.hw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Service {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.coh7tfxx59uf.eu-central-1.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "11111111";
    private static Connection connection = null;

    public static void transactionPut(Storage storage, File file) throws Exception {
        Connection connection = getConnection();
        if (storage == null)
            throw new Exception("storage null");
        if (file == null)
            throw new Exception("file null");
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            StorageDAO.findById(storage.getId());  //Пошук стореджа по id
            freePlaceForFile(storage, file.getId());              //Обчислення вільного місця
            for (File file1 : FileDAO.findAllFilesByStorageId(storage.getId())) { //Пошук дублікату
                if (theSameID(file1.getId(), file.getId()))
                    throw new Exception("files the same");
            }
            FileDAO.save(file); //збереження файлу

            connection.commit();
        } catch (SQLException e) {
            System.err.println("Something went wrong ");
            connection.rollback();
        }


    }


    public static void transactionDelete(Storage storage, File file) throws Exception {

        if (storage == null)
            throw new Exception("storage null");
        if (file == null)
            throw new Exception("file null");
        try (Connection connection = getConnection()) {

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

    public static void transactionTransferAll(Storage storageFrom, Storage storageTo) throws Exception {
        if (storageFrom == null)
            throw new Exception("storageFrom null");
        if (storageTo == null)
            throw new Exception("storageTo null");
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            StorageDAO.findById(storageFrom.getId());  //Пошук стореджаФром по id
            StorageDAO.findById(storageTo.getId());    //Пошук стореджаДу по id

            if (freePlaceInStorage(storageTo, FileDAO.findAllFilesByStorageId(storageTo.getId())) //Перевірка чи є місце для файлів, якщо є, то виконувати
                    >= sizeOfAllFiles(FileDAO.findAllFilesByStorageId(storageFrom.getId()))) {

                for (File file : FileDAO.findAllFilesByStorageId(storageFrom.getId())) {
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

    public static void transactionTransferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        if (storageFrom == null)
            throw new Exception("storageFrom null");
        if (storageTo == null)
            throw new Exception("storageTo null");
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            StorageDAO.findById(storageFrom.getId());  //Пошук стореджаФром по id
            StorageDAO.findById(storageTo.getId());    //Пошук стореджаДу по id
            freePlaceForFile(storageTo, id);           //Перевірка на вільне місце в стореджі
            for (File file : FileDAO.findAllFilesByStorageId(storageFrom.getId())) {
                if (theSameID(file.getId(), id)) {
                    file.setStorageId(storageTo.getId());
                    FileDAO.update(file);
                }

            }


            connection.commit();
        } catch (SQLException e) {
            System.err.println("Something went wrong ");
            connection.rollback();
        }


    }


    private static void freePlaceForFile(Storage storage, long id) throws Exception {
        if (storage.getStorageSize() - id < id)
            throw new Exception("there is no place in the storage with id " + storage.getId());

    }

    private static boolean theSameID(long fileId1, long fileId2) {
        return fileId1 == fileId2;

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
