import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable  } from 'rxjs';

import { Client, ClientRequest} from '../../shared/models/client';

@Injectable({
  providedIn: 'root'
  })
export class ClientService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl = 'http://localhost:8080/api/clients';

  findAll(): Observable<Client[]> {
    return this.http.get<Client[]>(this.apiUrl);
  }

  findById(id: string): Observable<Client> {
  return this.http.get<Client>(`${this.apiUrl}/${id}`);
 }

  create(client: ClientRequest): Observable<Client> {
    return this.http.post<Client>(this.apiUrl, client);
  }

  update(id: string, client: ClientRequest): Observable<Client> {
  return this.http.put<Client>(`${this.apiUrl}/${id}`, client);
  }
  
  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
