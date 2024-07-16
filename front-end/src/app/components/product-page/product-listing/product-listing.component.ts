import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../../services/product.service';
import { ProductDexieService } from '../../../services/product-dexie.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Product, productsDexie } from '../../../models/models';
import { v4 as uuidv4 } from 'uuid';

@Component({
  selector: 'app-product-listing',
  templateUrl: './product-listing.component.html',
  styleUrl: './product-listing.component.css'
})
export class ProductListingComponent implements OnInit, OnDestroy{

  constructor(private readonly router: Router, 
              private readonly service: ProductService, 
              private readonly dexieService: ProductDexieService,
              private readonly route: ActivatedRoute) { }
 

  sub$!: Subscription
  productlist: Product[] = []
  originalProductList: Product[] = [];
  stars: number[] = [1, 2, 3, 4, 5]
 
  
  ngOnInit(): void {
    this.clearExpiredIndexDB();

    this.route.queryParams.subscribe(params => {
      const queryParamValue = params['q'];
      if (queryParamValue) {
        this.searchProducts(queryParamValue.toLowerCase());
      } else {
        this.loadProductListFromIndexDB().then(() => {
          this.originalProductList = [...this.productlist];
        });
         
      }
    });
  }

  sortProductListByName(): void {
    this.productlist.sort((a, b) => a.productname.localeCompare(b.productname));
  }

  handleSortEvent(sortType: string): void {
    if (sortType === 'alphabetical') {
      this.sortProductListByName();
    } else if (sortType === 'default') {
      this.resetFilter();
    } else if (sortType === 'rating') {
      this.sortProductListByRating();
    }
  }

  sortProductListByRating(): void {
    this.productlist.sort((a, b) => b.rating - a.rating);
  }

  filterByTag(tag: string): void {
    this.productlist = this.productlist.filter(product => product.tags.includes(tag));
  }

  resetFilter(): void {
    this.productlist = [...this.originalProductList];

    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { q: null },
      queryParamsHandling: 'merge'
    });
  }


  searchProducts(keyword: string): void {
    this.productlist = [];

    this.dexieService.products.toArray()
      .then(entries => {
        let filteredProducts: Product[] = [];

        entries.forEach(entry => {
          const products = entry.products.filter(product =>
            product.description.toLowerCase().includes(keyword)
          );
          filteredProducts = filteredProducts.concat(products);
        });

        if (filteredProducts.length > 0) {
          this.productlist = filteredProducts;
        } else {
          
          this.sub$ = this.service.searchProductByTerm(keyword).subscribe(
            (data) => {
              if (data && data.length > 0) {
                this.productlist = data;
              } else {
                this.productlist = []; 
              }
            },
            (error) => {
              
              this.productlist = []; 
            }
          )
        }
      })
      .catch(err => {
        
        this.productlist = [];
      });
  }

  


  clearExpiredIndexDB() {
    const expiryTime = 30 * 1000;
    this.dexieService.clearExpiredEntries(expiryTime)
      .catch(err => {});
  }
  

  loadProductListFromIndexDB(){
    return this.dexieService.products.toArray()
      .then(entries => {
        if (entries.length === 0) {
          return this.loadProductListFromBackend();
        }
        for (const entry of entries) {
          if (entry.products && entry.products.length > 0) {
            this.productlist = entry.products;
            return this.productlist;
          }
        }
        return this.loadProductListFromBackend();
      })
      .catch(err => {
        return this.loadProductListFromBackend();
      });
  }

  loadProductListFromBackend(){
    this.sub$ = this.service.showAllProducts().subscribe({
      next: data => {
        this.productlist = data

        const productDexie: productsDexie = {
          id: uuidv4().substring(0, 4),
          products: this.productlist,
          timestamp: Date.now()
        }

        this.dexieService.products.add(productDexie)
                                  .catch(err => {})
      },
      error: err => {
        
        this.productlist = []
      }
    })
  }


  ngOnDestroy(): void {
    if (this.sub$) {
      this.sub$.unsubscribe();
    }
  }



}
