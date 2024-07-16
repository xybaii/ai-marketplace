import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { ReviewService } from '../../services/review.service';
import { ProductReview } from '../../models/models';

@Component({
  selector: 'app-reviewed',
  templateUrl: './reviewed.component.html',
  styleUrl: './reviewed.component.css'
})
export class ReviewedComponent implements OnInit, OnDestroy{

  constructor(private readonly activatedRoute: ActivatedRoute, private readonly reviewService: ReviewService) { }
 

  id: string = '';
  sub$!: Subscription;
  reviews: ProductReview[] = []
  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id']
    
    this.sub$ = this.reviewService.getReviewsByPurchaseId(this.id).subscribe(reviews => {
      this.reviews = reviews
    })
  }
  
  getStars(rating: number): number[] {
    // Return an array from 0 to rating-1
    return Array.from({ length: 5 }, (_, index) => index);
  }

  ngOnDestroy(): void {
    this.sub$.unsubscribe()
   }

}
