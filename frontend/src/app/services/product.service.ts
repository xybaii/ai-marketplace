import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private readonly http: HttpClient) { }

  showAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>('/api/product', { withCredentials: true })
  }

  getProductDetailById(id: string): Observable<Product>{
    return this.http.get<Product>(`/api/product/${id}`, { withCredentials: true })
  }

}
