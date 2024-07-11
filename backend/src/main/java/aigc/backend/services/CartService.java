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

    public void saveCart(String cartItems, String id) throws JsonProcessingException{
        cartRepo.saveCartItems(cartItems, id);
    }

    public void clearCart(String id){
        cartRepo.clearCart(id);
    }
}

