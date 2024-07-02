import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';


import { AuthInterceptor } from './interceptors/auth.interceptors';
import { AuthGuard } from './guards/auth.guards';
import { ProfileComponent } from './components/profilepage/profile/profile.component';
import { HomeComponent } from './components/homepage/home.component';
import { CookieService } from 'ngx-cookie-service';
import { UserService } from './services/user.service';
import { AuthorizationService } from './services/authorization.service';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ProductsComponent } from './components/productlist/products.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { CartComponent } from './components/cart/cart.component';
import { ProductService } from './services/product.service';
import { CartService } from './services/cart.service';
import { StoreModule } from '@ngrx/store';
import { cartReducer } from './store/cart.reducer';
import { EffectsModule } from '@ngrx/effects';
import { CartEffects } from './store/cart.effects';
import { EditProfileComponent } from './components/profilepage/edit-profile/edit-profile.component';
import { SuccessComponent } from './components/payment/success.component';
import { CancelComponent } from './components/payment/cancel.component';
import { PurchaseOrderComponent } from './components/purchase-order/purchase-order.component';






const appRoutes: Routes = [
  { path: 'profile', component: ProfileComponent},
  { path: 'profile/edit/:id', component: EditProfileComponent},
  { path: 'home', component: HomeComponent },
  { path: 'product', component: ProductsComponent},
  { path: 'product/:id', component: ProductDetailComponent},
  { path: 'cart', component: CartComponent},
  { path: 'success', component: SuccessComponent},
  { path: 'cancel', component: CancelComponent},
  { path: 'purchase-order', component: PurchaseOrderComponent},
  { path: '**', redirectTo: '/home', pathMatch: 'full' }
]
@NgModule({
  declarations: [
    AppComponent,
    ProfileComponent,
    EditProfileComponent,
    HomeComponent,
    NavbarComponent,
    ProductsComponent,
    ProductDetailComponent,
    CartComponent,
    SuccessComponent,
    CancelComponent,
    PurchaseOrderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule, ReactiveFormsModule,
    RouterModule.forRoot(appRoutes, {useHash: true}),
    StoreModule.forRoot({ cart: cartReducer }),
    EffectsModule.forRoot([CartEffects])
    
  ],
  providers: [
    CookieService,
    UserService, 
    AuthorizationService,
    ProductService,
    CartService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
