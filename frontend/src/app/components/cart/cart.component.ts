import { Component, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';
import { Observable } from 'rxjs';
import { CartItem } from '../../models/models';
import { loadStripe, Stripe } from '@stripe/stripe-js';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit{

  cartItems$: Observable<CartItem[]>;
  cartTotal$: Observable<number>;

  constructor(private readonly cartService: CartService, private readonly http: HttpClient ) {
    this.cartItems$ = this.cartService.getCartItems();
    this.cartTotal$ = this.cartService.getCartTotal();
  }


  async ngOnInit() {
    this.stripe = await loadStripe(environment.STRIPE_PUBLIC_KEY); 
  }


  onAddItem(item: CartItem) {
    this.cartService.addItem(item);
  }

  onRemoveItem(itemId: number) {
    this.cartService.removeItem(itemId);
  }

  onUpdateItem(itemId: number, quantity: number) {
    this.cartService.updateCart(itemId, quantity);
  }

  onClearCart() {
    this.cartService.clearCart();
  }

  stripe: Stripe | null = null;
  sessionId: string | null = null;


  createCheckoutSession() {
    this.cartItems$.subscribe(cartItems => {
      const itemNames = cartItems.map(item => item.item.productname);
      const itemPrices = cartItems.map(item => item.item.price * 100); 
      const itemQuantities = cartItems.map(item => item.quantity);

      this.http.post('/api/payment/create-session', {
        itemNames: itemNames,
        itemPrices: itemPrices,
        itemQuantities: itemQuantities
      }).subscribe((session: any) => {
        this.sessionId = session.id;
        this.redirectToCheckout();
      });
    });
  }

  redirectToCheckout() {
    if (this.stripe && this.sessionId) {
      this.stripe.redirectToCheckout({
        sessionId: this.sessionId
      }).then((result) => {
        if (result.error) {
          console.error(result.error.message);
        }
      });
    }
  }

}
