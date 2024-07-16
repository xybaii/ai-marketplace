import { Component, OnDestroy, OnInit } from '@angular/core';
import { PurchaseService } from '../../services/purchase.service';
import { ProductReview, PurchaseOrder } from '../../models/models';
import { Subscription } from 'rxjs';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ReviewService } from '../../services/review.service';

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrl: './review-form.component.css'
})
export class ReviewFormComponent implements OnInit, OnDestroy {

  purchaseOrders!: PurchaseOrder;
  sub$: Subscription = new Subscription();
  reviewForm!: FormGroup;
  id: string = '';

  constructor(
    private readonly purchaseService: PurchaseService,
    private readonly reviewService: ReviewService,
    private readonly fb: FormBuilder,
    private readonly activatedRoute: ActivatedRoute,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id'];
    

    this.reviewForm = this.fb.group({
      reviews: this.fb.array([])
    });

    this.sub$.add(
      this.purchaseService.getPurchaseById(this.id).subscribe(purchaseOrder => {
        this.purchaseOrders = purchaseOrder;
        this.initializeReviewForm();
      })
    )
   
  }

  initializeReviewForm(): void {
    const reviewsArray = this.reviewForm.get('reviews') as FormArray;
    reviewsArray.clear(); 

    const itemIds = this.purchaseOrders.item_ids.split(',').map(id => +id);
    const itemNames = this.purchaseOrders.items_purchased.split(',');

    itemIds.forEach((productId, index) => {
      reviewsArray.push(this.fb.group({
        purchase_id: [this.purchaseOrders.purchase_id],
        product_id: [productId],
        product_name: [itemNames[index]],
        user_id: [this.purchaseOrders.user_id],  
        rating: [null, [Validators.required, Validators.min(1), Validators.max(5)]],
        review: ['', Validators.required]
      }));
    });
  }

  get reviews(): FormArray {
    return this.reviewForm.get('reviews') as FormArray;
  }

  onSubmit(): void {
    if (this.reviewForm.valid) {
      const reviews: ProductReview[] = this.reviewForm.value.reviews;
      this.sub$.add(
        this.reviewService.saveProductReviews(reviews).subscribe(() => {
          this.reviewForm.reset();
          this.initializeReviewForm();
          this.router.navigate(['/purchase-order']);
        })
      )
    } else {
      this.router.navigate(['/error']);
    }
  }

  ngOnDestroy(): void {
    if (this.sub$) {
      this.sub$.unsubscribe();
    }
  }
}