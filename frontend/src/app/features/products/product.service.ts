import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import {
  Product,
  ProductRequest
} from '../../shared/models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl =
    'http://localhost:8080/api/products';

  findAll(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl);
  }

  findById(id: string): Observable<Product> {
    return this.http.get<Product>(
      `${this.apiUrl}/${id}`
    );
  }

  create(request: ProductRequest): Observable<Product> {
    return this.http.post<Product>(
      this.apiUrl,
      request
    );
  }

  update(
    id: string,
    request: ProductRequest
  ): Observable<Product> {
    return this.http.put<Product>(
      `${this.apiUrl}/${id}`,
      request
    );
  }

  deactivate(id: string): Observable<void> {
    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );
  }

  activate(id: string): Observable<Product> {
    return this.http.patch<Product>(
      `${this.apiUrl}/${id}/activate`,
      {}
    );
  }
}