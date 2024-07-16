package aigc.backend.repositories;

import java.io.StringReader;

import org.bson.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class CartRepository {
    

    @Autowired
    private MongoTemplate mongoTemplate;

    private final static String collectionName = "cart";

    
    public String getCartItems(String id) throws JsonProcessingException{
        Query query = new Query(Criteria.where("_id").is(id));
        query.fields().exclude("_id");
        Document cartItem = mongoTemplate.findOne(query, Document.class, collectionName);
        
        if (cartItem != null) {
            return cartItem.getString("cart");
        }
        return "false";
    }

    public Boolean saveCartItems(String cartItems, String id) throws JsonProcessingException{
        try {
            Document document = new Document();
            document.append("_id", id);
            JsonReader reader = Json.createReader(new StringReader(cartItems));
            JsonObject jsonObject = reader.readObject();
            JsonArray array = jsonObject.getJsonArray("cart");
            document.append("cart", array.toString());
            mongoTemplate.save(document, collectionName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean clearCart(String id){
        try {
            Query query = new Query(Criteria.where("_id").is(id));        
            mongoTemplate.remove(query, Document.class, collectionName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
