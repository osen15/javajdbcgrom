package JDBC.lesson4;

import JDBC.lesson3.Product;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        Product product1 = new Product(112, "sss", "sss", 20);
        Product product2 = new Product(113, "sss", "sss", 20);
        Product product3 = new Product(113, "sss", "sss", 20);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        TransactionDemo transactionDemo = new TransactionDemo();
        transactionDemo.save(products);
    }
}
