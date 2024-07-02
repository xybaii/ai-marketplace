import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-cancel',
  templateUrl: './cancel.component.html',
  styleUrl: './cancel.component.css'
})
export class CancelComponent{

  message: string;

  constructor() {
    this.message = 'Your payment has been cancelled. You can continue shopping or try again.';
  }


}
