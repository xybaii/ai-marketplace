package aigc.backend.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import aigc.backend.models.ProductReview;

@Repository
public class ProductReviewRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private final static String SQL_SAVE_PRODUCT_REVIEWS = """
        INSERT INTO product_reviews (purchase_id, product_id, product_name, user_id, rating, review) VALUES (?, ?, ?, ?, ?, ?)
        """;

    private final static String SQL_GET_REVIEWS_BY_PO_ID = """
        SELECT * FROM product_reviews where purchase_id = ?
        """;

    private final static String SQL_GET_REVIEWS_BY_PRODUCT_ID = """
        SELECT * FROM product_reviews where product_id = ?    
        """;

    public Boolean saveProductReviews(List<ProductReview> productReviews){
        if (productReviews == null || productReviews.isEmpty()) {
            return false; // No reviews to save
        }
        try {
            // Use batchUpdate for efficient bulk insertion
            jdbcTemplate.batchUpdate(SQL_SAVE_PRODUCT_REVIEWS, 
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(@SuppressWarnings("null") PreparedStatement ps, int i) throws SQLException {
                        ProductReview productReview = productReviews.get(i);
                        ps.setString(1, productReview.getPurchase_id());
                        ps.setInt(2, productReview.getProduct_id());
                        ps.setString(3, productReview.getProduct_name());
                        ps.setInt(4, productReview.getUser_id());
                        ps.setInt(5, productReview.getRating());
                        ps.setString(6, productReview.getReview());
                    }
    
                    @Override
                    public int getBatchSize() {
                        return productReviews.size(); // Number of records to batch
                    }
                }
            );
            return true; // Successfully saved
        } catch (DataAccessException e) {
            e.printStackTrace(); // Handle exception (e.g., logging)
            return false; // Indicate failure
        }
    }  
    
    
    public List<ProductReview> getReviewsByPoId(String id){
        
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_REVIEWS_BY_PO_ID, id);
        List<ProductReview> reviews = new LinkedList<>();

        while (rs.next()) {
            reviews.add(new ProductReview().rsToModel(rs));
        }
        if (reviews.isEmpty()) {
            return null;
        }
        return reviews;
    }

    public List<ProductReview> getReviewsByProductId(String id){
        
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_REVIEWS_BY_PRODUCT_ID, id);
        List<ProductReview> reviews = new LinkedList<>();

        while (rs.next()) {
            reviews.add(new ProductReview().rsToModel(rs));
        }
        if (reviews.isEmpty()) {
            return null;
        }
        return reviews;
    }
}
