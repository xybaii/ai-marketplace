import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/models';
import { Subscription } from 'rxjs';
import { CartService } from '../../services/cart.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
})
export class ProductDetailComponent implements OnInit, OnDestroy{
  

  constructor(private readonly activatedRoute: ActivatedRoute, 
              private readonly router: Router, 
              private readonly productService: ProductService,
              private readonly cartService: CartService
            ) { }

  sub$!: Subscription
  productDetail!: Product
  id: string = ''
  stars: number[] = [1, 2, 3, 4, 5];
  quantity: number = 1; 
  
  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id']
   this.sub$ = this.productService.getProductDetailById(this.id).subscribe(
     (data) => {
       this.productDetail = data
     }
   )
  }

  addToCart() {
    const cartItem = {
      item: this.productDetail,
      quantity: this.quantity
    };
    console.log(this.quantity + " add to cart")
    this.cartService.addItem(cartItem); 
    this.router.navigate(['/cart'])
  }

  ngOnDestroy(): void {
    this.sub$.unsubscribe()
  }
}
