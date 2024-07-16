package aigc.backend.models;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class ProductReview {
    

    private String purchase_id;
    private Integer product_id;
    private String product_name;
    private Integer user_id;
    private Integer rating;
    private String review;
    private Date created_at;
    
    public ProductReview() {
    }



    public ProductReview(String purchase_id, Integer product_id, String product_name, Integer user_id, Integer rating,
            String review, Date created_at) {
        this.purchase_id = purchase_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.user_id = user_id;
        this.rating = rating;
        this.review = review;
        this.created_at = created_at;
    }



    public String getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(String purchase_id) {
        this.purchase_id = purchase_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }     

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    } 

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
    
    public ProductReview rsToModel(SqlRowSet rs){
        ProductReview pr = new ProductReview();
        pr.setPurchase_id(rs.getString("purchase_id"));
        pr.setProduct_id(rs.getInt("product_id"));
        pr.setProduct_name(rs.getString("product_name"));
        pr.setUser_id(rs.getInt("user_id"));
        pr.setRating(rs.getInt("rating"));
        pr.setReview(rs.getString("review"));
        pr.setCreated_at(rs.getDate("created_at"));
        return pr;
    }



 

}
