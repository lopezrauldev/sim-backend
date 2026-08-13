import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  FormArray,
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { Client } from '../../../shared/models/client';
import { Product } from '../../../shared/models/product';

import { ClientService } from '../../../core/services/client';
import { ProductService } from '../../products/product.service';
import { QuoteService } from '../quote.service';

@Component({
  selector: 'app-quote-form',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './quote-form.html',
  styleUrl: './quote-form.scss'
})
export class QuoteForm implements OnInit {

  private readonly fb = inject(FormBuilder);
  private readonly router = inject(Router);

  private readonly clientService = inject(ClientService);
  private readonly productService = inject(ProductService);
  private readonly quoteService = inject(QuoteService);

  private readonly route = inject(ActivatedRoute);

  quoteId: string | null = null;

  readonly clients = signal<Client[]>([]);
  readonly products = signal<Product[]>([]);

  readonly quoteForm = this.fb.nonNullable.group({
    clientId: ['', Validators.required],

    items: this.fb.array([
      this.createItem()
    ])
  });

  ngOnInit(): void {

    this.quoteId =
      this.route.snapshot.paramMap.get('id');

    this.loadClients();
    this.loadProducts();

    if (this.quoteId) {
      this.loadQuote();
    }
  }

  get items(): FormArray {
    return this.quoteForm.controls.items;
  }

  private createItem() {
    return this.fb.nonNullable.group({
      productId: ['', Validators.required],

      quantity: [
        1,
        [
          Validators.required,
          Validators.min(0.01)
        ]
      ]
    });
  }

  addItem(): void {
    this.items.push(
      this.createItem()
    );
  }

  removeItem(index: number): void {

    if (this.items.length === 1) {
      return;
    }

    this.items.removeAt(index);
  }

  private loadClients(): void {

    this.clientService.findAll().subscribe({
      next: (clients) => {
        this.clients.set(
          clients.filter(client => client.active)
        );
      },

      error: (error) => {
        console.error(
          'Error al cargar clientes',
          error
        );
      }
    });
  }

  private loadProducts(): void {

    this.productService.findAll().subscribe({
      next: (products) => {
        this.products.set(
          products.filter(product => product.active)
        );
      },

      error: (error) => {
        console.error(
          'Error al cargar productos',
          error
        );
      }
    });
  }

  getProduct(productId: string): Product | undefined {

    return this.products().find(
      product => product.id === productId
    );
  }

  getItemSubtotal(index: number): number {

    const item = this.items.at(index);

    const productId =
      item.get('productId')?.value as string;

    const quantity =
      Number(item.get('quantity')?.value ?? 0);

    const product = this.getProduct(productId);

    if (!product) {
      return 0;
    }

    return Number(product.unitPrice) * quantity;
  }

  getSubtotal(): number {

    return this.items.controls.reduce(
      (total, _, index) =>
        total + this.getItemSubtotal(index),
      0
    );
  }

  getGeneralExpensesAndProfit(): number {
    return this.getSubtotal() * 0.10;
  }

  getTotal(): number {
    return this.getSubtotal()
      + this.getGeneralExpensesAndProfit();
  }

  save(): void {

    if (this.quoteForm.invalid) {
      this.quoteForm.markAllAsTouched();
      return;
    }

    const value = this.quoteForm.getRawValue();

    const items = value.items.map(item => ({
      productId: item.productId,
      quantity: item.quantity
    }));


    // EDITAR

    if (this.quoteId) {

      this.quoteService
        .update(
          this.quoteId,
          { items }
        )
        .subscribe({

          next: () => {
            this.router.navigate(['/quotes']);
          },

          error: (error) => {
            console.error(
              'Error al actualizar cotización',
              error
            );
          }

        });

      return;
    }


    // CREAR

    this.quoteService
      .create({
        clientId: value.clientId,
        items
      })
      .subscribe({

        next: () => {
          this.router.navigate(['/quotes']);
        },

        error: (error) => {
          console.error(
            'Error al guardar cotización',
            error
          );
        }

      });
  }

  private loadQuote(): void {

    if (!this.quoteId) {
      return;
    }

    this.quoteService
      .findById(this.quoteId)
      .subscribe({

        next: (quote) => {

          this.quoteForm.controls.clientId.setValue(
            quote.clientId
          );

          this.items.clear();

          quote.items.forEach(item => {

            this.items.push(
              this.fb.nonNullable.group({

                productId: [
                  item.productId,
                  Validators.required
                ],

                quantity: [
                  item.quantity,
                  [
                    Validators.required,
                    Validators.min(0.01)
                  ]
                ]

              })
            );

          });

        },

        error: (error) => {
          console.error(
            'Error al cargar cotización',
            error
          );
        }

      });
  }

  goBack(): void {
    this.router.navigate(['/quotes']);
  }
}