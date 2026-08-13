import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import {
  ProductMaterial,
  ProductMaterialQuantityRequest,
  ProductMaterialRequest
} from '../../shared/models/product-material';

@Injectable({
  providedIn: 'root'
})
export class ProductMaterialService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl =
    'http://localhost:8080/api/products';

  findByProductId(
    productId: string
  ): Observable<ProductMaterial[]> {

    return this.http.get<ProductMaterial[]>(
      `${this.apiUrl}/${productId}/materials`
    );
  }

  addMaterial(
    productId: string,
    request: ProductMaterialRequest
  ): Observable<ProductMaterial> {

    return this.http.post<ProductMaterial>(
      `${this.apiUrl}/${productId}/materials`,
      request
    );
  }

  updateBaseQuantity(
    productId: string,
    productMaterialId: string,
    request: ProductMaterialQuantityRequest
  ): Observable<ProductMaterial> {

    return this.http.patch<ProductMaterial>(
      `${this.apiUrl}/${productId}/materials/${productMaterialId}`,
      request
    );
  }

  removeMaterial(
    productId: string,
    productMaterialId: string
  ): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/${productId}/materials/${productMaterialId}`
    );
  }
}