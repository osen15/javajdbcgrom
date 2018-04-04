package JDBC.lesson3;

public class Demo {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();
        Product product = new Product(10, "Testo", "Test descr", 1000);
        //  productDAO.save(product);
        //  System.out.println(productDAO.getProducts());
        // productDAO.delete(10);
        productDAO.update(product);

    }


}
