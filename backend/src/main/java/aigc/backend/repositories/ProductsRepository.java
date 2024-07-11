package aigc.backend.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import aigc.backend.models.Product;

@Repository
public class ProductsRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_GET_ALL_PRODUCTS = """
            select * from products
            """;
    private final static String SQL_GET_PRODUCT_BY_ID = """
            select * from products where id = ?
            """;
    
    private final static String SQL_GET_PRODUCT_BY_SEARCH = """
            SELECT * FROM products WHERE LOWER(description) LIKE ?
            """;

    public List<Product> getAllProducts(){
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_ALL_PRODUCTS);
        List<Product> productList = new LinkedList<>();
        while (rs.next()) {
            productList.add(new Product().rsToModel(rs));
        }
        return productList;
    }

    public List<Product> getProductBySearchTerm(String term){
        String q = "%" + term + "%";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_PRODUCT_BY_SEARCH, q.toLowerCase());
        List<Product> productList = new LinkedList<>();
        while (rs.next()) {
            productList.add(new Product().rsToModel(rs));
        }
        return productList;
    }

    public Product getProductDetailById(String id) {
    
        List<Product> productCollect = new LinkedList<>();
    
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_PRODUCT_BY_ID, Integer.parseInt(id));
        while (rs.next()) {
            productCollect.add(new Product().rsToModel(rs));
        }
        for (Product product : productCollect) {
            System.out.println(product);
        }
        return productCollect.isEmpty() ? null : productCollect.get(0); 
    }
}
