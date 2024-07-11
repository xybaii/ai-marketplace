package aigc.backend.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aigc.backend.models.Product;
import aigc.backend.repositories.ProductsRepository;

@Service
public class ProductsService {
    
    @Autowired
    private ProductsRepository prodRepo;

    public List<Product> getAllProducts(){
        return prodRepo.getAllProducts();
    }
    public List<Product> getProductsBySearch(String term){
        return prodRepo.getProductBySearchTerm(term);
    }

    public Product getProductDetailById(String id){
        Product productDetail = prodRepo.getProductDetailById(id);
        if (productDetail == null) {
            return null;
        }
        return productDetail;
    }   
}
