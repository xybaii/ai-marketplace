package aigc.backend.models;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class PurchaseOrder {
    
    private String purchase_id;
    private String user_id;
    private Double amount;
    private String email;
    private String receipt_url;
    private Date created_at;
    
    public PurchaseOrder() {
    }

    public PurchaseOrder(String purchase_id, String user_id, Double amount, String email, String receipt_url,
            Date created_at) {
        this.purchase_id = purchase_id;
        this.user_id = user_id;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    @Override
    public String toString() {
        return "PurchaseOrder [purchase_id=" + purchase_id + ", user_id=" + user_id + ", amount=" + amount + ", email="
                + email + ", receipt_url=" + receipt_url + ", created_at=" + created_at + "]";
    }

    public PurchaseOrder rsToModel(SqlRowSet rs) {
        PurchaseOrder po = new PurchaseOrder();
        po.setPurchase_id(rs.getString("purchase_id"));
        po.setUser_id(rs.getString("user_id"));
        po.setAmount(rs.getDouble("amount"));
        po.setEmail(rs.getString("email"));
        po.setReceipt_url(rs.getString("receipt_url"));
        po.setCreated_at(rs.getDate("created_at"));
        return po;
    }
    
    
}
