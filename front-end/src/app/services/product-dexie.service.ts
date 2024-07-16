import { Injectable } from '@angular/core';
import Dexie from 'dexie';
import { productsDexie } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class ProductDexieService extends Dexie{

  products!: Dexie.Table<productsDexie, string>;

  constructor() { 
    super('products');
    this.version(1).stores({
      products: 'id, productlist, timestamp'
    });
    this.products = this.table('products');
  }

  async clearExpiredEntries(expiryTime: number) {
    const now = Date.now();
    const expiredEntries = await this.products.where("timestamp").below(now - expiryTime).toArray();
    for (const entry of expiredEntries){
      await this.products.delete(entry.id);
    }
  }
}
