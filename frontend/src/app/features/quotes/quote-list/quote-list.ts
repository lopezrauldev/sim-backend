import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

import { Quote } from '../../../shared/models/quote';
import { Client } from '../../../shared/models/client';

import { QuoteService } from '../quote.service';
import { ClientService } from '../../../core/services/client';

@Component({
  selector: 'app-quote-list',
  imports: [RouterLink],
  templateUrl: './quote-list.html',
  styleUrl: './quote-list.scss'
})
export class QuoteList implements OnInit {

  private readonly quoteService = inject(QuoteService);
  private readonly clientService = inject(ClientService);

  readonly quotes = signal<Quote[]>([]);
  readonly clients = signal<Client[]>([]);

  ngOnInit(): void {
    this.loadClients();
    this.loadQuotes();
  }

  private loadClients(): void {
    this.clientService.findAll().subscribe({
      next: (clients) => {
        this.clients.set(clients);
      },
      error: (error) => {
        console.error(
          'Error al cargar clientes',
          error
        );
      }
    });
  }

  private loadQuotes(): void {
    this.quoteService.findAll().subscribe({
      next: (quotes) => {
        this.quotes.set(quotes);
      },
      error: (error) => {
        console.error(
          'Error al cargar cotizaciones',
          error
        );
      }
    });
  }

  getClientName(clientId: string): string {

    const client = this.clients().find(
      client => client.id === clientId
    );

    if (!client) {
      return 'Cliente no disponible';
    }

    return client.name
      || client.businessName
      || client.documentNumber;
  }

  cancelQuote(id: string): void {

    this.quoteService.cancel(id).subscribe({
      next: () => {
        this.loadQuotes();
      },
      error: (error) => {
        console.error(
          'Error al cancelar cotización',
          error
        );
      }
    });
  }
}