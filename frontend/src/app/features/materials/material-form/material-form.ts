import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { MaterialService } from '../material.service';
import {
  MaterialCategory,
  MaterialRequest,
  MaterialUnit
} from '../../../shared/models/material';

@Component({
  selector: 'app-material-form',
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './material-form.html',
  styleUrl: './material-form.scss'
})
export class MaterialForm implements OnInit {

  private readonly fb = inject(FormBuilder);
  private readonly materialService = inject(MaterialService);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  materialId: string | null = null;

  readonly categories: MaterialCategory[] = [
    'DRYWALL',
    'PAINTING',
    'ELECTRICAL',
    'PLUMBING',
    'CARPENTRY',
    'METALWORK',
    'TOOLS',
    'SAFETY',
    'CONSUMABLE',
    'OTHER'
  ];

  readonly units: MaterialUnit[] = [
    'UNIT',
    'METER',
    'SQUARE_METER',
    'CUBIC_METER',
    'KILOGRAM',
    'LITER',
    'GALLON',
    'BAG',
    'BOX',
    'ROLL',
    'SHEET',
    'BUCKET'
  ];

  readonly materialForm = this.fb.nonNullable.group({
    code: ['', [
      Validators.required,
      Validators.maxLength(30)
    ]],

    name: ['', [
      Validators.required,
      Validators.maxLength(150)
    ]],

    description: ['', [
      Validators.maxLength(500)
    ]],

    supplier: ['', [
      Validators.maxLength(150)
    ]],

    category: ['DRYWALL' as MaterialCategory, [
      Validators.required
    ]],

    weight: [0],

    dimensions: ['', [
      Validators.maxLength(100)
    ]],

    unit: ['UNIT' as MaterialUnit, [
      Validators.required
    ]],

    unitPrice: [0, [
      Validators.required,
      Validators.min(0)
    ]],

    stock: [0, [
      Validators.required,
      Validators.min(0)
    ]],

    active: [true]
  });

  ngOnInit(): void {
    this.materialId = this.route.snapshot.paramMap.get('id');

    if (this.materialId) {
      this.loadMaterial(this.materialId);
    }
  }

  private loadMaterial(id: string): void {
    this.materialService.findById(id).subscribe({
      next: (material) => {
        this.materialForm.patchValue({
          code: material.code,
          name: material.name,
          description: material.description ?? '',
          supplier: material.supplier ?? '',
          category: material.category,
          weight: material.weight ?? 0,
          dimensions: material.dimensions ?? '',
          unit: material.unit,
          unitPrice: material.unitPrice,
          stock: material.stock,
          active: material.active
        });
      },

      error: (error) => {
        console.error('Error al cargar material', error);
      }
    });
  }

  save(): void {

    if (this.materialForm.invalid) {
      this.materialForm.markAllAsTouched();
      return;
    }

    const formValue = this.materialForm.getRawValue();

    const request: MaterialRequest = {
      code: formValue.code,
      name: formValue.name,
      description: formValue.description || null,
      supplier: formValue.supplier || null,
      category: formValue.category,
      weight: formValue.weight,
      dimensions: formValue.dimensions || null,
      unit: formValue.unit,
      unitPrice: formValue.unitPrice,
      stock: formValue.stock,
      active: formValue.active
    };

    if (this.materialId) {

      this.materialService
        .update(this.materialId, request)
        .subscribe({
          next: () => {
            this.router.navigate(['/materials']);
          },

          error: (error) => {
            console.error('Error al actualizar material', error);
          }
        });

    } else {

      this.materialService
        .create(request)
        .subscribe({
          next: () => {
            this.router.navigate(['/materials']);
          },

          error: (error) => {
            console.error('Error al crear material', error);
          }
        });
    }
  }

  goBack(): void {
    this.router.navigate(['/materials']);
  }
}