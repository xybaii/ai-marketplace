import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrl: './success.component.css'
})
export class SuccessComponent implements OnInit, OnDestroy{
  
  sessionId: string | null = null;
  private queryParamsSubscription: Subscription | null = null;

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    console.log('SuccessComponent initialized');
    this.queryParamsSubscription = this.route.queryParams.subscribe(params => {
      console.log('Query Params:', params);
      this.sessionId = params['session_id'];
      if (this.sessionId) {
        console.log('Transaction Successful with session ID:', this.sessionId);
      } else {
        console.log('Session ID not found in query params');
      }
    });
  }

  ngOnDestroy(): void {
    if (this.queryParamsSubscription) {
      this.queryParamsSubscription.unsubscribe();
    }
  }

  navigateToOrderPurchase(): void {
    if (this.sessionId) {
      this.router.navigate(['/purchase-order']);
    }
  }

}
