package aigc.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import aigc.backend.models.PurchaseOrder;
import aigc.backend.repositories.PurchaseRepository;

@Service
public class PurchaseService {
    
    @Autowired
    private PurchaseRepository purchaseRepo;

      @Transactional    
      public String createChargeRecord(Charge charge, String userId) throws StripeException{
        String response = purchaseRepo.createChargeRecord(charge, userId);
        return response;
    }

    public List<PurchaseOrder> getPurchaseHistory(String id){
      return purchaseRepo.getPurchaseHistory(id);
    }
    
}
