import { Component, OnDestroy, OnInit } from '@angular/core';
import { PurchaseOrder } from '../../models/models';
import { Subscription } from 'rxjs';
import { PurchaseService } from '../../services/purchase.service';
import { ReviewService } from '../../services/review.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-purchase-order',
  templateUrl: './purchase-order.component.html',
  styleUrl: './purchase-order.component.css'
})
export class PurchaseOrderComponent implements OnInit, OnDestroy{

  constructor(private readonly purchaseService: PurchaseService, private readonly reviewService: ReviewService, private readonly router: Router) { }
  
  purchaseOrders: PurchaseOrder[] = [];
  sub$: Subscription = new Subscription();

  ngOnInit(): void {
    this.sub$.add(
      this.purchaseService.getPurchaseHistory().subscribe(purchaseOrders => {
        this.purchaseOrders = purchaseOrders
      })
    )

  }

  review(id: string): void {
    this.sub$.add(
      this.reviewService.getReviewsByPurchaseId(id).subscribe(reviews => {
        if (reviews.length < 1) {
          this.router.navigate(['/reviews', id])
        } else {
          this.router.navigate(['/reviewed', id])
        }
      })
    )
  }

  ngOnDestroy(): void {
    this.sub$.unsubscribe()
  }
  

}
