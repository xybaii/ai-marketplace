import { Component, NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CartComponent } from './components/cart/cart.component';
import { HomeComponent } from './components/home/home.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { SuccessComponent } from './components/payment-redirect/success/success.component';
import { CancelComponent } from './components/payment-redirect/cancel/cancel.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { ProfilePageComponent } from './components/profile-page/profile-page.component';
import { PurchaseOrderComponent } from './components/purchase-order/purchase-order.component';
import { ShellComponentComponent } from './components/shell-component/shell-component.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { StoreModule } from '@ngrx/store';
import { cartReducer } from './store/cart.reducer';
import { EffectsModule } from '@ngrx/effects';
import { CartEffects } from './store/cart.effects';
import { CookieService } from 'ngx-cookie-service';
import { UserService } from './services/user.service';
import { AuthorizationService } from './services/authorization.service';
import { ProductService } from './services/product.service';
import { CartService } from './services/cart.service';
import { DateFormatPipe } from './date-format.pipe';
import { ServiceWorkerModule } from '@angular/service-worker';
import { ProfileEditComponent } from './components/profile-edit/profile-edit.component';
import { ProductDexieService } from './services/product-dexie.service';
import { ProductPageComponent } from './components/product-page/product-page.component';
import { ProductSearchFilterComponent } from './components/product-page/product-search-filter/product-search-filter.component';
import { ProductListingComponent } from './components/product-page/product-listing/product-listing.component';
import { authGuard } from './route-guard/auth-guard';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ReviewFormComponent } from './components/review-form/review-form.component';
import { LoginService } from './services/login.service';
import { PurchaseService } from './services/purchase.service';
import { RegisterService } from './services/register.service';
import { ReviewService } from './services/review.service';
import { ThemeService } from './services/theme.service';
import { ReviewedComponent } from './components/reviewed/reviewed.component';
import { ErrorComponent } from './components/error/error.component';








const appRoutes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent  },
  { path: 'register', component: RegisterComponent  },
  { path: 'cart', component: CartComponent, canActivate: [authGuard] },
  { path: 'profile', component: ProfilePageComponent, canActivate: [authGuard]},
  { path: 'profile/edit/:id', component: ProfileEditComponent, canActivate: [authGuard] },
  { path: 'purchase-order', component: PurchaseOrderComponent, canActivate: [authGuard]  },
  { path: 'reviews/:id', component: ReviewFormComponent, canActivate: [authGuard] },
  { path: 'reviewed/:id', component: ReviewedComponent, canActivate: [authGuard] },
  { path: 'success', component: SuccessComponent, canActivate: [authGuard]},
  { path: 'cancel', component: CancelComponent, canActivate: [authGuard] },
  { path: 'product/:id', component: ProductDetailComponent, canActivate: [authGuard] },
  { path: 'product', component: ProductPageComponent, canActivate: [authGuard] },
  { path: 'error', component: ErrorComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' }

]
@NgModule({
  declarations: [
    AppComponent,
    CartComponent,
    HomeComponent,
    NavbarComponent,
    SuccessComponent,
    CancelComponent,
    ProductDetailComponent,
    ProfilePageComponent,
    PurchaseOrderComponent,
    DateFormatPipe,
    ShellComponentComponent,
    ProfileEditComponent,
    ProductPageComponent,
    ProductSearchFilterComponent,
    ProductListingComponent,
    LoginComponent,
    RegisterComponent,
    ReviewFormComponent,
    ReviewedComponent,
    ErrorComponent,
    
  ],

  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule, ReactiveFormsModule,
    AppRoutingModule,
    RouterModule.forRoot(appRoutes, {useHash: true}),
    StoreModule.forRoot({ cart: cartReducer }),
    EffectsModule.forRoot([ CartEffects ]),
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !isDevMode(),
      registrationStrategy: 'registerWhenStable:30000'
    }),

  ],
  providers: [
    CookieService,
    UserService,
    AuthorizationService,
    ProductService,
    CartService,
    LoginService,
    PurchaseService,
    RegisterService,
    ReviewService,
    ThemeService,
    ProductDexieService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
