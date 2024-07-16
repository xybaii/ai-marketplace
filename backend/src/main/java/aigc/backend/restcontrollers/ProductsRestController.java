package aigc.backend.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aigc.backend.models.Product;
import aigc.backend.services.ProductsService;


@RestController
@RequestMapping("/api")
public class ProductsRestController {

    @Autowired
    private ProductsService prodService;

    @GetMapping("/product")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> productList = prodService.getAllProducts();
        if (productList != null) {
            return ResponseEntity.ok().body(productList);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> getProductsBySearch(@RequestParam String q){
        List<Product> productList = prodService.getProductsBySearch(q);
        if (productList != null) {
            return ResponseEntity.ok().body(productList);
        }
        return ResponseEntity.ok().body(null);
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductDetailById(@PathVariable String id) {
        Product productDetail = prodService.getProductDetailById(id);
        if (productDetail != null){
            return ResponseEntity.ok().body(productDetail);
        }
        return ResponseEntity.badRequest().body(null);
    }
    
    
}
