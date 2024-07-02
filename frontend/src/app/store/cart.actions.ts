import { createAction, props } from "@ngrx/store";
import { CartItem } from "../models/models";

// Actions for managing the cart locally

export const addItem = createAction('[Cart] Add Item', props<{ item: CartItem }>());
export const removeItem = createAction('[Cart] Remove Item', props<{ itemId: number }>());
export const updateCart = createAction('[Cart] Update Cart', props<{ itemId: number, quantity: number }>());
export const clearCart = createAction('[Cart] Clear Cart');



// Actions for interacting with the backend

export const loadCart = createAction('[Cart] Load Cart');
export const loadCartSuccess = createAction('[Cart] Load Cart Success', props<{ items: CartItem[] }>());
export const loadCartFailure = createAction('[Cart] Load Cart Failure', props<{ error: any }>());

export const saveCart = createAction('[Cart] Save Cart', props<{ items: CartItem[] }>());
export const saveCartSuccess = createAction('[Cart] Save Cart Success');
export const saveCartFailure = createAction('[Cart] Save Cart Failure', props<{ error: any }>());

export const updateCartBackend = createAction('[Cart] Update Cart Backend', props<{ itemId: number, quantity: number }>());
export const updateCartBackendSuccess = createAction('[Cart] Update Cart Backend Success');
export const updateCartBackendFailure = createAction('[Cart] Update Cart Backend Failure', props<{ error: any }>());

export const deleteCartItemBackend = createAction('[Cart] Delete Cart Item Backend', props<{ itemId: number }>());
export const deleteCartItemBackendSuccess = createAction('[Cart] Delete Cart Item Backend Success');
export const deleteCartItemBackendFailure = createAction('[Cart] Delete Cart Item Backend Failure', props<{ error: any }>());