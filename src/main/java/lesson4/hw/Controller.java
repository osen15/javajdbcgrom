package lesson4.hw;


public class Controller {


    public static void put(Storage storage, File file) throws Exception {
        GeneralDAO.put(storage, file);


    }

    public static void delete(Storage storage, File file) throws Exception {
        GeneralDAO.delete(storage, file);


    }

    public static void transferAll(Storage storageFrom, Storage storageTo) throws Exception {
        GeneralDAO.transferAll(storageFrom, storageTo);


    }

    public static void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        GeneralDAO.transferFile(storageFrom, storageTo, id);

    }


}
