import { Component, inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { ProductService } from '../product.service';
import {
  ProductRequest,
  ProductUnit
} from '../../../shared/models/product';

@Component({
  selector: 'app-product-form',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './product-form.html',
  styleUrl: './product-form.scss'
})
export class ProductForm implements OnInit {

  private readonly fb = inject(FormBuilder);
  private readonly productService = inject(ProductService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  productId: string | null = null;

  readonly units: ProductUnit[] = [
    'UND',
    'M',
    'M2',
    'M3',
    'KG',
    'GL',
    'LT',
    'JGO'
  ];

  readonly productForm = this.fb.nonNullable.group({
    code: ['', [
      Validators.required,
      Validators.maxLength(30)
    ]],

    name: ['', [
      Validators.required,
      Validators.maxLength(150)
    ]],

    description: ['', [
      Validators.maxLength(300)
    ]],

    unit: ['M2' as ProductUnit, [
      Validators.required
    ]],

    unitPrice: [0, [
      Validators.required,
      Validators.min(0)
    ]]
  });

  ngOnInit(): void {
    this.productId = this.route.snapshot.paramMap.get('id');

    if (this.productId) {
      this.loadProduct(this.productId);
    }
  }

  private loadProduct(id: string): void {
    this.productService.findById(id).subscribe({
      next: (product) => {
        this.productForm.patchValue({
          code: product.code,
          name: product.name,
          description: product.description ?? '',
          unit: product.unit,
          unitPrice: product.unitPrice
        });
      },

      error: (error) => {
        console.error('Error al cargar producto', error);
      }
    });
  }

  save(): void {

    if (this.productForm.invalid) {
      this.productForm.markAllAsTouched();
      return;
    }

    const formValue = this.productForm.getRawValue();

    const request: ProductRequest = {
      code: formValue.code,
      name: formValue.name,
      description: formValue.description || null,
      unit: formValue.unit,
      unitPrice: formValue.unitPrice
    };

    if (this.productId) {

      this.productService
        .update(this.productId, request)
        .subscribe({
          next: () => {
            this.router.navigate(['/products']);
          },

          error: (error) => {
            console.error(
              'Error al actualizar producto',
              error
            );
          }
        });

    } else {

      this.productService
        .create(request)
        .subscribe({
          next: () => {
            this.router.navigate(['/products']);
          },

          error: (error) => {
            console.error(
              'Error al crear producto',
              error
            );
          }
        });
    }
  }

  goBack(): void {
    this.router.navigate(['/products']);
  }
}