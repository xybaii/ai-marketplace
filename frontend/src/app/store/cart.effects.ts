import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, mergeMap, switchMap } from 'rxjs/operators';
import { of } from 'rxjs';
import * as CartActions from '../store/cart.actions';
import { CartService } from '../services/cart.service';

@Injectable()
export class CartEffects {

  constructor(
    private actions$: Actions,
    private cartService: CartService
  ) {}

  loadCart$ = createEffect(() =>
    this.actions$.pipe(
      ofType(CartActions.loadCart),
      switchMap(() => 
        this.cartService.fetchCartItems().pipe(
          map(items => CartActions.loadCartSuccess({ items })),
          catchError(error => of(CartActions.loadCartFailure({ error })))
        )
      )
    )
  );

  saveCart$ = createEffect(() =>
    this.actions$.pipe(
      ofType(CartActions.saveCart),
      mergeMap(action =>
        this.cartService.saveCartItems(action.items).pipe(
          map(() => CartActions.saveCartSuccess()),
          catchError(error => of(CartActions.saveCartFailure({ error })))
        )
      )
    )
  );

  updateCartBackend$ = createEffect(() =>
    this.actions$.pipe(
      ofType(CartActions.updateCartBackend),
      mergeMap(action =>
        this.cartService.updateCartItem(action.itemId, action.quantity).pipe(
          map(() => CartActions.updateCartBackendSuccess()),
          catchError(error => of(CartActions.updateCartBackendFailure({ error })))
        )
      )
    )
  );

  deleteCartItemBackend$ = createEffect(() =>
    this.actions$.pipe(
      ofType(CartActions.deleteCartItemBackend),
      mergeMap(action =>
        this.cartService.deleteCartItem(action.itemId).pipe(
          map(() => CartActions.deleteCartItemBackendSuccess()),
          catchError(error => of(CartActions.deleteCartItemBackendFailure({ error })))
        )
      )
    )
  );
}
