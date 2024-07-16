import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ProductReview } from '../models/models';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private readonly http: HttpClient) { }

  saveProductReviews(reviews: ProductReview[]): Observable<any> {
    return this.http.post('/api/reviews/submit', reviews, { withCredentials: true })
  }

  getReviewsByPurchaseId(id: string): Observable<ProductReview[]> {
    return this.http.get<any>(`/api/reviewed/${id}`, { withCredentials: true })
  }

  getReviewsByProductId(id: string): Observable<ProductReview[]> {
    return this.http.get<any>(`/api/product/reviewed/${id}`, { withCredentials: true })
  }

}
