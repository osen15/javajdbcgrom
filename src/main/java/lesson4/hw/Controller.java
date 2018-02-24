package lesson4.hw;


public class Controller {


    public static void put(Storage storage, File file) throws Exception {
        Service.transactionPut(storage, file);

    }

    public static void delete(Storage storage, File file) throws Exception {
        Service.transactionDelete(storage, file);

    }

    public static void transferAll(Storage storageFrom, Storage storageTo) throws Exception {
        Service.transactionTransferAll(storageFrom, storageTo);

    }

    public static void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        Service.transactionTransferFile(storageFrom, storageTo, id);
    }


}
