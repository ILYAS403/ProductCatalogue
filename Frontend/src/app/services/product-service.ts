import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product,CreateProduct } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private apiUrl = 'https://app-productcatalog-backend-ckh0dsdyaba3deb5.westus3-01.azurewebsites.net/api/products';

  constructor(private http: HttpClient) {}

  
  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl);
  }

  searchProducts(query: string): Observable<Product[]> {
    return this.http.get<Product[]>(
      `${this.apiUrl}/search?query=${encodeURIComponent(query)}`
    );
  }

  addProduct(product: CreateProduct): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, product);
  }
}
