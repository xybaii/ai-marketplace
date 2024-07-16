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
      public Boolean createChargeRecord(Charge charge) throws StripeException{
        return purchaseRepo.saveChargeAsPurchaseOrder(charge);
    }

    public List<PurchaseOrder> getPurchaseHistory(String id){
      return purchaseRepo.getPurchaseHistory(id);
    }

    public PurchaseOrder getPurchaseOrderByPOID(String purchaseId){
      return purchaseRepo.getPurchaseOrderById(purchaseId);
    }
    
}
