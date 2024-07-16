package aigc.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import aigc.backend.repositories.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    public String getCart(String id) throws JsonProcessingException{
        return cartRepo.getCartItems(id);
    }

    public Boolean saveCart(String cartItems, String id) throws JsonProcessingException{
        return cartRepo.saveCartItems(cartItems, id);
    }

    public Boolean clearCart(String id){
        return cartRepo.clearCart(id);
    }
}

