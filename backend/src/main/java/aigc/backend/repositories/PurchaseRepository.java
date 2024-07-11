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

    private final static String SQL_SAVE_CHARGE_RECORD = """
            INSERT INTO purchase_orders (purchase_id, user_id, amount, email, receipt_url) VALUES (?, ?, ?, ?, ?)
            """;
    private final static String SQL_GET_PURCHASE_HISTORY = """
            select * from purchase_orders where user_id = ?
            """;


    public String createChargeRecord(Charge charge, String userId) throws StripeException{

        String chargeId = charge.getId();
        long amount = charge.getAmount();
        String email = charge.getBillingDetails().getEmail();
        String receiptUrl = charge.getReceiptUrl();

        String emailContent = """
                Hello\n\n
                Thank you for your payment!\n\n
                Total amount\n\n
                """ + amount + """
                        \n\nYou may view you receipt at\n\n
                        """+ receiptUrl;;

        mailSenderService.sendNewMail(email, "Payment receipt from " + chargeId, emailContent);

        String response = null;
        try {
            jdbcTemplate.update(SQL_SAVE_CHARGE_RECORD, chargeId, userId, amount, email, receiptUrl);
            response = "purchase order saved successfully";
        } catch (DataAccessException e) {
            response = "fail to save purchase order to db";
            e.printStackTrace();
        }
        return response;
    }

  public List<PurchaseOrder> getPurchaseHistory(String id){

        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_PURCHASE_HISTORY, id);
        List<PurchaseOrder> purchaseOrders = new LinkedList<>();
        while (rs.next()) {
            purchaseOrders.add(new PurchaseOrder().rsToModel(rs));
        }
        if (!purchaseOrders.isEmpty() ) {
            return purchaseOrders;
        }
        return null;   
  }
    
    
}
