import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Product, ProductReview } from '../../models/models';
import { forkJoin, Subscription } from 'rxjs';
import { CartService } from '../../services/cart.service';
import { ReviewService } from '../../services/review.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
})
export class ProductDetailComponent implements OnInit, OnDestroy{
  

  constructor(private readonly activatedRoute: ActivatedRoute, 
              private readonly router: Router, 
              private readonly productService: ProductService,
              private readonly reviewService: ReviewService,
              private readonly cartService: CartService
            ) { }

  sub$!: Subscription
  productDetail!: Product
  reviews: ProductReview[] = []
  id: string = ''
  stars: number[] = [1, 2, 3, 4, 5];
  quantity: number = 1 
  
  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id']
    this.sub$ = forkJoin({
      productDetail: this.productService.getProductDetailById(this.id),
      reviews: this.reviewService.getReviewsByProductId(this.id)
    }).subscribe({
      next: (data) => {
        this.productDetail = data['productDetail']
        this.reviews = data['reviews']
      }
    })
    
    this.productService.getProductDetailById(this.id).subscribe(
     (data) => {
       this.productDetail = data
     }
   )
  }

  getStars(rating: number): number[] {
    // Return an array from 0 to rating-1
    return Array.from({ length: 5 }, (_, index) => index);
  }

  addToCart() {
    const cartItem = {
      item: this.productDetail,
      quantity: this.quantity
    };
    
    this.cartService.addItem(cartItem); 
    this.router.navigate(['/cart'])
  }

  ngOnDestroy(): void {
    this.sub$.unsubscribe()
  }
}
