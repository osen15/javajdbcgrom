package lesson4.hw;

public class File {
    private long id;
    private String name;
    private String format;
    private long size;
    private long storageId;

    public File(long id, String name, String format, long size, long storageId) throws Exception {
        this.id = id;
        this.name = lengthCheck(name);
        this.format = format;
        this.size = size;
        this.storageId = storageId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFormat() {
        return format;
    }

    public long getSize() {
        return size;
    }

    public long getStorageId() {
        return storageId;
    }

    public void setStorageId(long storageId) {
        this.storageId = storageId;
    }


    private String lengthCheck(String name) throws Exception {
        if (name.length() > 10)
            throw new Exception("too large file name: " + name);
        return name;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", format='" + format + '\'' +
                ", size=" + size +
                ", storageId=" + storageId +
                '}';
    }
}
