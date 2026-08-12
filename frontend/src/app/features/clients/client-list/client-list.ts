import { Component, inject, OnInit, signal } from '@angular/core';

import { ClientService } from '../../../core/services/client';
import { Client } from '../../../shared/models/client';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-client-list',
  imports: [RouterLink],
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

  deactivateClient(id: string): void {

    const confirmed = confirm(
      '¿Está seguro de que desea desactivar este cliente?'
    );

    if (!confirmed) {
      return;
    }
    
    this.clientService.delete(id).subscribe({
      next: () => {
        this.clients.update(clients =>
          clients.filter(client => client.id !== id)
        );
      },
      error: (error) => {
        console.error('Error al desactivar cliente', error);
      }
    });
  }
}
