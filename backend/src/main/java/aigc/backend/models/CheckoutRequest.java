package aigc.backend.models;

import java.util.List;

public class CheckoutRequest {
    
    private List<String> itemNames;
    private List<Long> itemPrices;
    private List<Long> itemQuantities;
    private String userId;
    
    
    public CheckoutRequest() {
    }


    public CheckoutRequest(List<String> itemNames, List<Long> itemPrices, List<Long> itemQuantities, String userId) {
        this.itemNames = itemNames;
        this.itemPrices = itemPrices;
        this.itemQuantities = itemQuantities;
        this.userId = userId;
    }


    public List<String> getItemNames() {
        return itemNames;
    }


    public void setItemNames(List<String> itemNames) {
        this.itemNames = itemNames;
    }


    public List<Long> getItemPrices() {
        return itemPrices;
    }


    public void setItemPrices(List<Long> itemPrices) {
        this.itemPrices = itemPrices;
    }


    public List<Long> getItemQuantities() {
        return itemQuantities;
    }


    public void setItemQuantities(List<Long> itemQuantities) {
        this.itemQuantities = itemQuantities;
    }


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    

    
}
