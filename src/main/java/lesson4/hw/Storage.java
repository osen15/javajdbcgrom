package lesson4.hw;

import java.util.Arrays;

public class Storage {
    private  long id;
    private String[] formatsSupported;
    private String storageCountry;
    private long storageSize;

    public Storage(long id, String[] formatsSupported, String storageCountry, long storageSize) {
        this.id = id;
        this.formatsSupported = formatsSupported;
        this.storageCountry = storageCountry;
        this.storageSize = storageSize;
    }

    public long getId() {
        return id;
    }

    public String[] getFormatsSupported() {
        return formatsSupported;
    }

    public String getStorageCountry() {
        return storageCountry;
    }

    public long getStorageSize() {
        return storageSize;
    }
}
