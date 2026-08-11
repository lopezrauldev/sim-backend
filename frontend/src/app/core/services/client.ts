import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable  } from 'rxjs';

import { Client } from '../../shared/models/client';

@Injectable({
  providedIn: 'root'
  })
export class ClientService {

  private readonly http = inject(HttpClient);

  private readonly apiUrl = 'http://localhost:8080/api/clients';

  findAll(): Observable<Client[]> {
    return this.http.get<Client[]>(this.apiUrl);
    }
  }
