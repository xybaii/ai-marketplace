package aigc.backend.models;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class PurchaseOrder {
    
    private String purchase_id;
    private Integer user_id;
    private String items_purchased;
    private String item_ids;
    private Double amount;
    private String email;
    private String receipt_url;
    private Date created_at;
    
    public PurchaseOrder() {
    }

    public PurchaseOrder(String purchase_id, Integer user_id, String items_purchased, String item_ids, Double amount,
            String email, String receipt_url, Date created_at) {
        this.purchase_id = purchase_id;
        this.user_id = user_id;
        this.items_purchased = items_purchased;
        this.item_ids = item_ids;
        this.amount = amount;
        this.email = email;
        this.receipt_url = receipt_url;
        this.created_at = created_at;
    }



    public String getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(String purchase_id) {
        this.purchase_id = purchase_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
    
    public String getItems_purchased() {
        return items_purchased;
    }

    public void setItems_purchased(String items_purchased) {
        this.items_purchased = items_purchased;
    } 

    public String getItem_ids() {
        return item_ids;
    }

    public void setItem_ids(String item_ids) {
        this.item_ids = item_ids;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReceipt_url() {
        return receipt_url;
    }

    public void setReceipt_url(String receipt_url) {
        this.receipt_url = receipt_url;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public PurchaseOrder rsToModel(SqlRowSet rs) {
        PurchaseOrder po = new PurchaseOrder();
        po.setPurchase_id(rs.getString("purchase_id"));
        po.setUser_id(rs.getInt("user_id"));
        po.setItems_purchased(rs.getString("items_purchased"));
        po.setItem_ids(rs.getString("item_ids"));
        po.setAmount(rs.getDouble("amount"));
        po.setEmail(rs.getString("email"));
        po.setReceipt_url(rs.getString("receipt_url"));
        po.setCreated_at(rs.getDate("created_at"));
        return po;
    }

    
}
