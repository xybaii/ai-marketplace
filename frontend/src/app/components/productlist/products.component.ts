import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Subscription } from 'rxjs';
import { Product } from '../../models/models';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit, OnDestroy{

  constructor(private readonly router: Router, private readonly service: ProductService) { }
 

  sub$!: Subscription
  productlist: Product[] = []
  
  ngOnInit(): void {
   this.sub$ = this.service.showAllProducts().subscribe(
     (data) => {
       this.productlist = data
     }
   )
  }

  ngOnDestroy(): void {
    this.sub$.unsubscribe()
  }

  stars = [1, 2, 3, 4, 5];

}
