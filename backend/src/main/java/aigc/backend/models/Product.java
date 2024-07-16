package aigc.backend.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Product {

    private Integer id;
    private String productname;
    private String description;
    private String tags;
    private String url;
    private Double price;
    private Integer rating;
    private String image;
    
    
    public Product() {
    }

    public Product(Integer id, String productname, String description, String tags, String url, Double price,
            Integer rating, String image) {
        this.id = id;
        this.productname = productname;
        this.description = description;
        this.tags = tags;
        this.url = url;
        this.price = price;
        this.rating = rating;
        this.image = image;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getProductname() {
        return productname;
    }


    public void setProductname(String productname) {
        this.productname = productname;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getTags() {
        return tags;
    }


    public void setTags(String tags) {
        this.tags = tags;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public Double getPrice() {
        return price;
    }


    public void setPrice(Double price) {
        this.price = price;
    }


    public Integer getRating() {
        return rating;
    }


    public void setRating(Integer rating) {
        this.rating = rating;
    }


    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }
    
    public Product rsToModel(SqlRowSet rs) {

        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setProductname(rs.getString("productname"));
        product.setDescription(rs.getString("description"));
        product.setTags(rs.getString("tags"));
        product.setUrl(rs.getString("url"));
        product.setPrice(rs.getDouble("price"));
        product.setRating(rs.getInt("rating"));
        product.setImage(rs.getString("image"));

        return product;
    }
    
}
