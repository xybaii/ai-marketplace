import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PurchaseOrder } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {

  constructor(private readonly http: HttpClient) { }

  getPurchaseHistory(): Observable<PurchaseOrder[]> {
    return this.http.get<any>('/api/purchase/history', { withCredentials: true })
  }

  getPurchaseById(id: string): Observable<PurchaseOrder> {
    return this.http.get<any>(`/api/reviews/${id}`, { withCredentials: true })
  }
  
}
