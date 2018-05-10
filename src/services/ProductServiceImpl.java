package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Product;

public class ProductServiceImpl implements ProductService {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/product_manager";
    static final String DB_USERNAME = "root";
    static final String DB_PASSWORD = "aptx4869";

    public ProductServiceImpl () {}

    public List<Product> findAll() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        Connection connection = null;
        Statement statement = null;

        log("Connecting to Database...");
        connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        log("Creating Database query...");
        statement = connection.createStatement();
        String sql;
        sql = "select `id`, `code`, `name` from product";

        log("Query is in process...");
        ResultSet resultSet = statement.executeQuery(sql);

        List<Product> products = new ArrayList<>();
        log("Collecting result...");
        while (resultSet.next()) {
            Product product = new Product();
            product.setId(resultSet.getInt("id"));
            product.setCode(resultSet.getString("code"));
            product.setName(resultSet.getString("name"));

            products.add(product);
        }
        log("Collecting result is done. Closing connects...");
        resultSet.close();
        statement.close();
        connection.close();

        return products;
    }
    private void log(String message) {
        System.out.println("ProductServiceImpl: " + message);
    }
}
