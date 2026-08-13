import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import {
  Quote,
  QuoteMaterial,
  QuoteRequest,
  QuoteUpdateRequest
} from '../../shared/models/quote';

@Injectable({
  providedIn: 'root'
})
export class QuoteService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl =
    'http://localhost:8080/api/quotes';

  findAll(): Observable<Quote[]> {
    return this.http.get<Quote[]>(
      this.apiUrl
    );
  }

  findById(id: string): Observable<Quote> {
    return this.http.get<Quote>(
      `${this.apiUrl}/${id}`
    );
  }

  create(request: QuoteRequest): Observable<Quote> {
    return this.http.post<Quote>(
      this.apiUrl,
      request
    );
  }

  update(
    id: string,
    request: QuoteUpdateRequest
  ): Observable<Quote> {

    return this.http.put<Quote>(
      `${this.apiUrl}/${id}`,
      request
    );
  }

  cancel(id: string): Observable<Quote> {
    return this.http.patch<Quote>(
      `${this.apiUrl}/${id}/cancel`,
      {}
    );
  }

  getRequiredMaterials(
    id: string
  ): Observable<QuoteMaterial[]> {

    return this.http.get<QuoteMaterial[]>(
      `${this.apiUrl}/${id}/materials`
    );
  }
}