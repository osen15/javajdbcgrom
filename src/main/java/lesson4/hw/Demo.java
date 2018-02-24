package lesson4.hw;

public class Demo {
    public static void main(String[] args) throws Exception {
        //  File file1 = new File(1,"test", "doc",5,1);
        //  File file2 = new File(2,"test", "doc",5,1);
        File file3 = new File(3, "test", "doc", 5, 1);

        //  FileDAO.save(file3);
        //  FileDAO.delete(file3.getId());

        String[] strings = {"doc"};

        Storage storage1 = new Storage(1, strings, "UKR", 1000000);
        Storage storage2 = new Storage(2, strings, "UKR", 1000000);

        // StorageDAO.save(storage2);
        // StorageDAO.findById(1);

        // Controller.put(storage1, file3);


        // System.out.println(FileDAO.findAllFilesByStorageId(storage1.getId()).toString());
        //Controller.delete(storage1,file3);

        // Controller.transferAll(storage1, storage2);
        Controller.transferFile(storage2, storage1, file3.getId());
    }
}
