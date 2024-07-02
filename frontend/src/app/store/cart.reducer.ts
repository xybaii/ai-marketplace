import { Action, createReducer, on } from "@ngrx/store";
import { addItem, clearCart, removeItem, updateCart } from "./cart.actions";
import { CartItem } from "../models/models";

export interface CartState {
    items: CartItem[];
  }
  
  export const initialState: CartState = {
    items: []
  };
  
  const _cartReducer = createReducer(
    initialState,
    on(addItem, (state, { item }) => {
      const existingItemIndex = state.items.findIndex(cartItem => cartItem.item.id === item.item.id);
      if (existingItemIndex !== -1) {
        const updatedItems = state.items.map((cartItem, index) =>
          index === existingItemIndex ? { ...cartItem, quantity: cartItem.quantity + item.quantity } : cartItem
        );
        return { ...state, items: updatedItems };
      }
      return { ...state, items: [...state.items, item] };
    }),

    on(removeItem, (state, { itemId }) => ({
      ...state,
      items: state.items.filter(cartItem => cartItem.item.id !== itemId)
    })),
    on(updateCart, (state, { itemId, quantity }) => {
      const updatedItems = state.items.map(cartItem =>
        cartItem.item.id === itemId ? { ...cartItem, quantity } : cartItem
      );
      return { ...state, items: updatedItems };
    }),
    
    on(clearCart, state => ({
      ...state,
      items: []
    }))
  );
  
  export function cartReducer(state: CartState | undefined, action: Action) {
    return _cartReducer(state, action);
  }