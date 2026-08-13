import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Material, MaterialRequest } from '../../shared/models/material';

@Injectable({
  providedIn: 'root'
})
export class MaterialService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl = 'http://localhost:8080/api/materials';

  findAll(): Observable<Material[]> {
    return this.http.get<Material[]>(this.apiUrl);
  }

  findById(id: string): Observable<Material> {
    return this.http.get<Material>(`${this.apiUrl}/${id}`);
  }

  findByCode(code: string): Observable<Material> {
    return this.http.get<Material>(`${this.apiUrl}/code/${code}`);
  }

  create(request: MaterialRequest): Observable<Material> {
    return this.http.post<Material>(this.apiUrl, request);
  }

  update(
    id: string,
    request: MaterialRequest
  ): Observable<Material> {
    return this.http.put<Material>(
      `${this.apiUrl}/${id}`,
      request
    );
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}