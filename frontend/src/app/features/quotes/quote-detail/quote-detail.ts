import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Quote, QuoteMaterial } from '../../../shared/models/quote';
import { Client } from '../../../shared/models/client';

import { QuoteService } from '../quote.service';
import { ClientService } from '../../../core/services/client';

@Component({
  selector: 'app-quote-detail',
  imports: [],
  templateUrl: './quote-detail.html',
  styleUrl: './quote-detail.scss'
})
export class QuoteDetail implements OnInit {

  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  private readonly quoteService = inject(QuoteService);
  private readonly clientService = inject(ClientService);

  readonly quote = signal<Quote | null>(null);
  readonly client = signal<Client | null>(null);
  readonly materials = signal<QuoteMaterial[]>([]);

  quoteId = '';

  ngOnInit(): void {

    const id = this.route.snapshot.paramMap.get('id');

    if (!id) {
      this.router.navigate(['/quotes']);
      return;
    }

    this.quoteId = id;

    this.loadQuote();
    this.loadMaterials();
  }

  private loadQuote(): void {

    this.quoteService.findById(this.quoteId).subscribe({

      next: (quote) => {

        this.quote.set(quote);

        this.loadClient(quote.clientId);
      },

      error: (error) => {
        console.error(
          'Error al cargar cotización',
          error
        );
      }
    });
  }

  private loadClient(clientId: string): void {

    this.clientService.findById(clientId).subscribe({

      next: (client) => {
        this.client.set(client);
      },

      error: (error) => {
        console.error(
          'Error al cargar cliente',
          error
        );
      }
    });
  }

  private loadMaterials(): void {

    this.quoteService
      .getRequiredMaterials(this.quoteId)
      .subscribe({

        next: (materials) => {
          this.materials.set(materials);
        },

        error: (error) => {
          console.error(
            'Error al cargar materiales requeridos',
            error
          );
        }
      });
  }

  getClientName(): string {

    const currentClient = this.client();

    if (!currentClient) {
      return 'Cargando...';
    }

    return currentClient.name
      || currentClient.businessName
      || currentClient.documentNumber;
  }

  goBack(): void {
    this.router.navigate(['/quotes']);
  }
}