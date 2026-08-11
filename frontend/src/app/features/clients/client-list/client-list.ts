import { Component, inject, OnInit, signal } from '@angular/core';

import { ClientService } from '../../../core/services/client';
import { Client } from '../../../shared/models/client';

@Component({
  selector: 'app-client-list',
  imports: [],
  templateUrl: './client-list.html',
  styleUrl: './client-list.scss',
})
export class ClientList implements OnInit {

  private readonly clientService = inject(ClientService);

  clients = signal<Client[]>([]);

  ngOnInit(): void {
    this.clientService.findAll().subscribe({
      next: (clients) => {
        console.log('Clientes cargados:', clients);
        this.clients.set(clients);
      },
      error: (error) => {
        console.error('Error al cargar clientes', error);
      }
    });
  }
}
