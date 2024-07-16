package aigc.backend.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.PaymentMethodType;

@Service
public class StripeService {
    
    private String secretKey;

    public StripeService(@Value("${stripe.api.secretKey}") String secretKey) {
      this.secretKey = secretKey;
      Stripe.apiKey = this.secretKey;
    }
  
    public Session createCheckoutSession(List<String> itemNames, List<Long> itemPrices, List<Long> itemQuantities, List<Integer> itemIds,String userId) throws StripeException {
        List<SessionCreateParams.LineItem> sessionItems = new ArrayList<>();
        for (int i = 0; i < itemNames.size(); i++) {
            sessionItems.add(
                SessionCreateParams.LineItem.builder()
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("usd")
                            .setUnitAmount(itemPrices.get(i))
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName(itemNames.get(i))
                                    .build()
                            )
                            .build()
                    )
                    .setQuantity(itemQuantities.get(i))
                    .build()
            );

        }

        Map<String, String> metadata = new HashMap<>();
        metadata.put("user_id", userId);
        String itemNamesString = String.join(",", itemNames);
        metadata.put("items_purchased", itemNamesString);
        String itemIdsString = itemIds.stream()
                              .map(String::valueOf)
                              .collect(Collectors.joining(","));
        metadata.put("item_ids", itemIdsString);
        

        SessionCreateParams.PaymentIntentData paymentIntentData = SessionCreateParams.PaymentIntentData.builder()
            .putAllMetadata(metadata)
            .build();
      


        SessionCreateParams params = SessionCreateParams.builder()
            .addPaymentMethodType(PaymentMethodType.CARD)
            .addAllLineItem(sessionItems)
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .putMetadata("user_id", userId)
            .setPaymentIntentData(paymentIntentData)
            .setSuccessUrl("https://graceful-unity-production.up.railway.app/#/success?session_id={CHECKOUT_SESSION_ID}")
            .setCancelUrl("https://graceful-unity-production.up.railway.app/#/cancel")
            .build();

        return Session.create(params);
    }


  
}
