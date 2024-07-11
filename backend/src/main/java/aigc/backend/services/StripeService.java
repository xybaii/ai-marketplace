package aigc.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.PaymentMethodType;

@Service
public class StripeService {
    

    @Value("${stripe.api.secretKey}")
    private String apiKey;


    public StripeService() {
        Stripe.apiKey = "sk_test_51NRpXAIMYYwk1potzIoDnA7PK1IV5UVcEb63GHrhbDrEgY8XNU822fJATb10vlaA61u9tDZ5czUGQFSBWTzQtkWL00Q5hgX4yy";
    }

    public Session createCheckoutSession(List<String> itemNames, List<Long> itemPrices, List<Long> itemQuantities, String userId) throws StripeException {
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

        SessionCreateParams params = SessionCreateParams.builder()
            .addPaymentMethodType(PaymentMethodType.CARD)
            .addAllLineItem(sessionItems)
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .putMetadata("user_id", userId)
            .setSuccessUrl("http://localhost:4200/#/success?session_id={CHECKOUT_SESSION_ID}")
            .setCancelUrl("http://localhost:4200/#/cancel")
            .build();

        return Session.create(params);
    }


  
}
