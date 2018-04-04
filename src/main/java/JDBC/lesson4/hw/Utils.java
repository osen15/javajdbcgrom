package JDBC.lesson4.hw;


import java.util.List;

public class Utils {


    public static void formatSupport(String[] supportedFormats, String fileFormat) throws Exception {
        for (String format : supportedFormats) {
            if (!format.equals(fileFormat))
                throw new Exception("wrong format of file");

        }

    }


    public static long freePlaceInStorage(Storage storage, List<File> files) {
        long sum = 0;
        for (File file : files) {
            sum += file.getSize();
        }

        return storage.getStorageSize() - sum;
    }

    public static long sizeOfAllFilesinStorage(List<File> files) {
        long sum = 0;
        for (File file : files) {
            sum += file.getId();
        }

        return sum;
    }


}
