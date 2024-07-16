package aigc.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aigc.backend.models.ProductReview;
import aigc.backend.repositories.ProductReviewRepository;

@Service
public class ProductReviewService {
    
    @Autowired
    private ProductReviewRepository reviewRepo;

    public Boolean saveProductReviews(List<ProductReview> listOfReviews){
            return reviewRepo.saveProductReviews(listOfReviews);
    }

    public List<ProductReview> getReviewedByPoId(String id){
        return reviewRepo.getReviewsByPoId(id);
    }

    public List<ProductReview> getReviewedByProductId(String id){
        return reviewRepo.getReviewsByProductId(id);
    }
}
