package aigc.backend.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import aigc.backend.models.PurchaseOrder;
import aigc.backend.services.MailSenderService;



@Repository
public class PurchaseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MailSenderService mailSenderService;

    private final static String SQL_SAVE_CHARGE_AS_PO = """
            INSERT INTO purchase_orders (purchase_id, user_id, items_purchased, item_ids, amount, email, receipt_url) VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
    private final static String SQL_GET_ALL_PO_BY_USER_ID = """
            select * from purchase_orders where user_id = ? ORDER BY created_at DESC
            """;
    private final static String SQL_GET_PO_BY_PO_ID = """
            select * from purchase_orders where purchase_id = ?
            """;


    public Boolean saveChargeAsPurchaseOrder(Charge charge) throws StripeException{

        String string_user_id = charge.getMetadata().get("user_id");
        Integer user_id = Integer.parseInt(string_user_id);
        String purchase_id = charge.getId();
        long amount = charge.getAmount();
        String email = charge.getBillingDetails().getEmail();
        String receiptUrl = charge.getReceiptUrl();
        String items_purchased = charge.getMetadata().get("items_purchased");
        String items_ids = charge.getMetadata().get("item_ids");

        String emailContent = "Hello\n\n Thank you for your payment!\n\n Total amount \n " + "$ "+ amount/100 + "\n\nYou may view you receipt at " + receiptUrl;
        mailSenderService.sendNewMail(email, "Payment receipt from AI Marketplace Order No. " + purchase_id, emailContent);

        try {
            jdbcTemplate.update(SQL_SAVE_CHARGE_AS_PO, purchase_id, user_id, items_purchased, items_ids, amount, email, receiptUrl);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

  public List<PurchaseOrder> getPurchaseHistory(String id){

        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_ALL_PO_BY_USER_ID, id);
        List<PurchaseOrder> purchaseOrders = new LinkedList<>();
        while (rs.next()) {
            purchaseOrders.add(new PurchaseOrder().rsToModel(rs));
        }
        if (!purchaseOrders.isEmpty() ) {
            return purchaseOrders;
        }
        return null;   
  }

  public PurchaseOrder getPurchaseOrderById(String purchaseId){

    SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_PO_BY_PO_ID, purchaseId);
        List<PurchaseOrder> purchaseOrder = new LinkedList<>();
        while (rs.next()) {
            purchaseOrder.add(new PurchaseOrder().rsToModel(rs));
        }
        return purchaseOrder.isEmpty() ? null : purchaseOrder.get(0); 
  }
    
    
}
