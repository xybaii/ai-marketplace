import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { CartState } from '../store/cart.reducer';
import { Observable, catchError } from 'rxjs';
import { CartItem } from '../models/models';

import { addItem, clearCart,  removeItem,updateCart,  } from '../store/cart.actions';
import { HttpClient } from '@angular/common/http';
import { selectCartItems, selectCartTotal } from '../store/cart.selector';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private apiUrl = '/api/cart';

  constructor(private store: Store<CartState>, private http: HttpClient) {}


  addItem(item: CartItem) {
    this.store.dispatch(addItem({ item }));
  }

  removeItem(itemId: number) {
    this.store.dispatch(removeItem({ itemId }));
  }

  updateCart(itemId: number, quantity: number) {
    this.store.dispatch(updateCart({ itemId, quantity }));
    
  }

  getCartItems(): Observable<CartItem[]> {
    return this.store.select(selectCartItems);
  }

  getCartTotal(): Observable<number> {
    return this.store.select(selectCartTotal);
  }


   loadCartFromBackend(): Observable<CartItem[]> {
    return this.http.get<CartItem[]>(`${this.apiUrl}`).pipe(
      catchError(error => {
        throw error;
      })
    );
  }

    saveCartToBackend(cartItems: CartItem[]): Observable<any> {
      return this.http.post(`${this.apiUrl}/save`, { cart: cartItems }).pipe(
        catchError(error => {
          throw error;
        })
      );
    }


  clearCart(): Observable<any> {
    this.store.dispatch(clearCart());
    return this.http.delete(`${this.apiUrl}/clear`).pipe( 
      catchError(error => {
        throw error;
      })
    );
  }


}
