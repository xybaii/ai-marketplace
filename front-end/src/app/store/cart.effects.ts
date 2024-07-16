import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import { of } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';
import * as CartActions from '../store/cart.actions'; 
import { CartService } from '../services/cart.service'; 
import { CartState } from '../store/cart.reducer';

@Injectable()
export class CartEffects {

  constructor(
    private actions$: Actions,
    private cartService: CartService,
    private store: Store<CartState>
  ) {}

  loadCart$ = createEffect(() =>
    this.actions$.pipe(
      ofType('[Cart] Load Cart'),
      mergeMap(() => 
        this.cartService.loadCartFromBackend().pipe(
          map(cartItems => CartActions.loadCartSuccess({ cartItems })),
          catchError(() => of(CartActions.loadCartFailure()))
        )
      )
    )
  );

  saveCart$ = createEffect(() =>
    this.actions$.pipe(
      ofType(CartActions.saveCart),
      mergeMap(action => 
        this.cartService.saveCartToBackend(action.cartItems).pipe(
          map(() => CartActions.saveCartSuccess()),
          catchError(() => of(CartActions.saveCartFailure()))
        )
      )
    )
  );
 
}
