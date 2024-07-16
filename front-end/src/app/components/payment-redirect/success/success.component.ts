import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CartService } from '../../../services/cart.service';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrl: './success.component.css'
})
export class SuccessComponent implements OnInit, OnDestroy{
  
  sessionId: string | null = null;
  private queryParamsSubscription: Subscription | null = null;

  constructor(private route: ActivatedRoute, private router: Router, private cartService: CartService) {}

  ngOnInit(): void {
    this.queryParamsSubscription = this.route.queryParams.subscribe(params => {
      this.sessionId = params['session_id'];
    });
    
    this.cartService.clearCart().subscribe(
      {next: response => {},
      error: error => {}
    }
    );
   
  }

  ngOnDestroy(): void {
    if (this.queryParamsSubscription) {
      this.queryParamsSubscription.unsubscribe();
    }
  }



}
