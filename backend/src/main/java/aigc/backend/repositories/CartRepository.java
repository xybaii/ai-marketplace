package aigc.backend.repositories;

import java.io.StringReader;

import org.bson.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;



@Repository
public class CartRepository {
    

    @Autowired
    private MongoTemplate mongoTemplate;

    private final String collectionName = "cart";

    @SuppressWarnings("null")
    public String getCartItems(String id) throws JsonProcessingException{
        Query query = new Query(Criteria.where("_id").is(id));
        query.fields().exclude("_id");
        Document cartItem = mongoTemplate.findOne(query, Document.class, collectionName);
        if (cartItem.isEmpty()) {
            return null;
        }
        String jsonString = cartItem.getString("cart");
    
        return jsonString;
    }

    public void saveCartItems(String cartItems, String id) throws JsonProcessingException{
        Document document = new Document();
        document.append("_id", id);
        JsonReader reader = Json.createReader(new StringReader(cartItems));
        JsonObject jsonObject = reader.readObject();
        JsonArray array = jsonObject.getJsonArray("cart");
        ObjectMapper mapper = new ObjectMapper();
        String cart = mapper.writeValueAsString(array);
        document.append("cart", array.toString());
        mongoTemplate.save(document, collectionName);
    }

    public void clearCart(String id){
        Query query = new Query(Criteria.where("_id").is(id));
        System.out.println("clear cart on repo");
        try {
            mongoTemplate.remove(query, Document.class, collectionName);
            System.err.println("clear success on repo");
        } catch (Exception e) {

         
            e.printStackTrace();
        }
    }
}
