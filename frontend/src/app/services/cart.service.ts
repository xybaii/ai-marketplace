import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { CartState } from '../store/cart.reducer';
import { Observable, catchError } from 'rxjs';
import { CartItem } from '../models/models';
import { selectCartItems, selectCartTotal } from '../store/cart.selectors';
import { addItem, clearCart, removeItem, updateCart } from '../store/cart.actions';
import { HttpClient } from '@angular/common/http';

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

  clearCart() {
    this.store.dispatch(clearCart());
  }

  getCartItems(): Observable<CartItem[]> {
    return this.store.select(selectCartItems);
  }

  getCartTotal(): Observable<number> {
    return this.store.select(selectCartTotal);
  }



  // Backend integration methods
  fetchCartItems(): Observable<CartItem[]> {
    return this.http.get<CartItem[]>(`${this.apiUrl}/items`).pipe(
      catchError(error => {
        console.error('Error fetching cart items', error);
        throw error; 
      })
    );
  }

  saveCartItems(cartItems: CartItem[]): Observable<any> {
    return this.http.post(`${this.apiUrl}/save`, cartItems).pipe(
      catchError(error => {
        console.error('Error saving cart items', error);
        throw error; 
      })
    );
  }

  updateCartItem(itemId: number, quantity: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/update/${itemId}`, { quantity }).pipe(
      catchError(error => {
        console.error(`Error updating cart item ${itemId}`, error);
        throw error; 
      })
    );
  }

  deleteCartItem(itemId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${itemId}`).pipe(
      catchError(error => {
        console.error(`Error deleting cart item ${itemId}`, error);
        throw error; 
      })
    );
  }
}
