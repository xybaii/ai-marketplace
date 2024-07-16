import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from '../../../services/product.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-product-search-filter',
  templateUrl: './product-search-filter.component.html',
  styleUrl: './product-search-filter.component.css'
})
export class ProductSearchFilterComponent {



  @Output() resetFilterEvent: EventEmitter<void> = new EventEmitter<void>();
  @Output() sortEvent: EventEmitter<string> = new EventEmitter<string>();


  form: FormGroup;
  isDropdownVisible = false;


  constructor(private readonly router: Router, private readonly fb: FormBuilder) {
    this.form = this.fb.group({
      q: ['', Validators.required]
    });
  }

  search() {
    const queryParamValue = this.form.value.q;
    this.router.navigate(['/product'], { queryParams: { q: queryParamValue } });
  }


  resetFilter(): void {
    this.resetFilterEvent.emit();
    this.form.reset(); 
  }

  sort(sortType: string): void {
    this.sortEvent.emit(sortType);
    this.isDropdownVisible = false; 
  }

  toggleDropdown(): void {
    this.isDropdownVisible = !this.isDropdownVisible;
  }


}
