package Hibernate.lesson2;

import Hibernate.lesson1.Product;


public class Demo {
    public static void main(String[] args) {
        Product product1 = new Product();
        product1.setName("robocop new");
        product1.setDescription("for children");
        product1.setPrice(1000);


        Product product2 = new Product();
        product2.setId(15);
        product2.setName("robocop new2");
        product2.setDescription("for children");
        product2.setPrice(1000);


        Product product3 = new Product();
        product3.setId(17);
        product3.setName("robocop new12345");
        product3.setDescription("for children");
        product3.setPrice(5556);


        // delete(product4);
        //  ProductDAO.save(product1);
        //  ProductDAO.update(product3);
        // ProductDAO.delete(product2);
        //  List<Product> products = Arrays.asList(product1, product2, product3);
        //  ProductDAO.saveAll(products);
        //  ProductDAO.deleteAll(products);
        //  ProductDAO.updateAll(products);


    }
}
