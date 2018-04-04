package JDBC.lesson4.hw;

public class Demo {
    public static void main(String[] args) throws Exception {
        File file1 = new File(1, "test", "doc", 5, 1);
        File file2 = new File(2, "test", "doc", 5, 1);
        File file3 = new File(5, "test", "jpeg", 5, 1);

      //  FileDAO.save(file3);
      // FileDAO.delete(file3.getId());

        String[] strings = {"doc", "txt"};


        Storage storage1 = new Storage(1, strings, "UKR", 1000000);
        Storage storage3 = new Storage(3, strings, "UKR", 1000000);
       // System.out.println(Utils.formatSupport(StorageDAO.findById(storage1.getId()).getFormatsSupported(), file3.getFormat()));

        //   StorageDAO.save(storage3);
        //  System.out.println(Arrays.toString(StorageDAO.findById(1).getFormatsSupported()));
        // StorageDAO.update(storage1);
        // Controller.put(storage1, file3);


        // System.out.println(FileDAO.findAllFilesByStorageId(storage1.getId()).toString());
        //Controller.delete(storage1,file3);

        // Controller.transferAll(storage1, storage2);
        // Controller.transferFile(storage2, storage1, file3.getId());
        Controller.put(storage1, file3);

    }
}
