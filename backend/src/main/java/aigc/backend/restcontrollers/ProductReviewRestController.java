package aigc.backend.restcontrollers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import aigc.backend.Utils.BooleanApiResponse;
import aigc.backend.models.ProductReview;
import aigc.backend.models.PurchaseOrder;
import aigc.backend.services.ProductReviewService;
import aigc.backend.services.PurchaseService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api")
public class ProductReviewRestController {

    @Autowired
    private ProductReviewService reviewService;

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/reviews/{id}")
    public ResponseEntity<PurchaseOrder> getPoForReviewForm(@PathVariable String id) {
        System.out.println(id + " >>> po id");
        
        PurchaseOrder po = purchaseService.getPurchaseOrderByPOID(id);
        if (po != null) {
            System.err.println(po.toString() + " >>> po");
            return ResponseEntity.ok().body(po);
        }
        BooleanApiResponse response = new BooleanApiResponse();
        response.setSuccess(false);
        response.setMessage("BAD REQUEST");
        return ResponseEntity.badRequest().body(null);
    }


    @PostMapping("/reviews/submit")
    public ResponseEntity<String> saveProductReviews(@RequestBody List<ProductReview> productReview) throws JsonProcessingException {
        BooleanApiResponse response = new BooleanApiResponse();
        try {            
            response.setSuccess(reviewService.saveProductReviews(productReview));
            response.setMessage("200 OK");
            return ResponseEntity.ok().body(response.toJsonString());
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("BAD REQUEST");
            return ResponseEntity.badRequest().body(response.toJsonString());
        }
        
    }
    

    @GetMapping("/reviewed/{id}")
    public ResponseEntity<List<ProductReview>> getReviewedReviewsByPO(@PathVariable String id) {
        List<ProductReview> reviewed = reviewService.getReviewedByPoId(id);
        if (reviewed != null && !reviewed.isEmpty()) {
            return ResponseEntity.ok().body(reviewed);
        }
        return ResponseEntity.ok().body(new LinkedList<>());        
    }
    
   
    @GetMapping("/product/reviewed/{id}")
    public ResponseEntity<List<ProductReview>> getReviewedReviewsByProduct(@PathVariable String id) {
        List<ProductReview> reviewed = reviewService.getReviewedByProductId(id);
        if (reviewed != null && !reviewed.isEmpty()) {
            return ResponseEntity.ok().body(reviewed);
        }
        return ResponseEntity.ok().body(new LinkedList<>());        
    }
    
}
