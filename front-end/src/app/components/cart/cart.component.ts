import { Component, OnDestroy, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';
import { EMPTY, map, Observable, of, Subscription, switchMap, take } from 'rxjs';
import { CartItem } from '../../models/models';
import { loadStripe, Stripe } from '@stripe/stripe-js';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../../services/user.service';
import { Store } from '@ngrx/store';
import { CartState } from '../../store/cart.reducer';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment.prod';
import { PurchaseService } from '../../services/purchase.service';





@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit, OnDestroy{

  constructor(private readonly cartService: CartService, 
              private readonly http: HttpClient,
              private store: Store<CartState> ,
              private readonly router: Router) {
    this.cartItems$ = this.cartService.getCartItems();
    this.cartTotal$ = this.cartService.getCartTotal();
  }

  cartItems$: Observable<CartItem[]>;
  cartTotal$: Observable<number>;
  stripe: Stripe | null = null;
  stripeKey: string = ''
  sessionId: string | null = null;



  async ngOnInit() {
   
    this.stripe = await loadStripe(environment.STRIPE_API_PUBLICKEY);

  
    this.cartItems$.pipe(
      take(1),
      map(cartItems => cartItems.length === 0),
      switchMap(isEmpty => {
        if (isEmpty) {
          return of(this.loadCart());
        }
        return of(EMPTY);
      })
    ).subscribe();

  }

  loadCart() {
    this.cartService.loadCartFromBackend().subscribe(cartItem => {
      if (Array.isArray(cartItem)) {
      cartItem.forEach(item => this.cartService.addItem(item));
      }
    });
  }

  ngOnDestroy() {
    this.saveCart();
   
  }

  saveCart() {
    this.cartItems$.subscribe(cartItems => {
     this.cartService.saveCartToBackend(cartItems).subscribe(
        {next: () => {},
        error: error => {}
      }
      );
    }
  );
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




  createCheckoutSession() {
    this.cartItems$.subscribe(cartItems => {
      const itemNames = cartItems.map(item => item.item.productname);
      const itemPrices = cartItems.map(item => item.item.price * 100); 
      const itemQuantities = cartItems.map(item => item.quantity);
      const itemId = cartItems.map(item => item.item.id);
      
      this.http.post('/api/purchase/create-session', {
        itemNames: itemNames,
        itemPrices: itemPrices,
        itemQuantities: itemQuantities,
        itemId: itemId
      }).subscribe({next: (session: any) => {
          this.sessionId = session.id;
          this.redirectToCheckout();

      }, error: error => {
 
        this.router.navigate(['/error']);
      }}
      
      );
    });
  }

  redirectToCheckout() {
    if (this.stripe && this.sessionId) {
      this.stripe.redirectToCheckout({
        sessionId: this.sessionId
      }).then((result) => {
        if (result.error) {
          this.router.navigate(['/error']);
        }
      });
    }
  }

}

