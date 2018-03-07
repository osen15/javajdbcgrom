package lesson4.hw;


import java.util.List;

public class Utils {


    public static void formatSupport(String[] supportedFormats, String fileFormat) throws Exception {
        for (String format : supportedFormats) {
            if (!format.equals(fileFormat))
                throw new Exception("wrong format of file");

        }

    }


    public static void freePlaceForFile(Storage storage, File file) throws Exception {
        if (storage.getStorageSize() - file.getSize() < file.getSize())
            throw new Exception("there is no place in the storage with id " + storage.getId());

    }


    private static long freePlaceInStorage(Storage storage, List<File> files) {
        long sum = 0;
        for (File file : files) {
            sum += file.getSize();
        }

        return storage.getStorageSize() - sum;
    }

    public static long sizeOfAllFiles(List<File> files) {
        long sum = 0;
        for (File file : files) {
            sum += file.getId();
        }

        return sum;
    }


}
