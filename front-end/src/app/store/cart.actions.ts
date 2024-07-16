import { createAction, props } from "@ngrx/store";
import { CartItem } from "../models/models";


export const addItem = createAction('[Cart] Add Item', props<{ item: CartItem }>());
export const removeItem = createAction('[Cart] Remove Item', props<{ itemId: number }>());
export const updateCart = createAction('[Cart] Update Cart', props<{ itemId: number, quantity: number }>());
export const clearCart = createAction('[Cart] Clear Cart');

export const loadCart = createAction('[Cart] Load Cart');
export const loadCartSuccess = createAction('[Cart] Load Cart Success', props<{ cartItems: CartItem[] }>());
export const loadCartFailure = createAction('[Cart] Load Cart Failure');

export const saveCart = createAction('[Cart] Save Cart', props<{ cartItems: CartItem[] }>());
export const saveCartSuccess = createAction('[Cart] Save Cart Success');
export const saveCartFailure = createAction('[Cart] Save Cart Failure');
