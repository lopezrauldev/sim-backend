import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { Product } from '../../../shared/models/product';
import { Material } from '../../../shared/models/material';
import { ProductMaterial } from '../../../shared/models/product-material';

import { ProductService } from '../product.service';
import { ProductMaterialService } from '../product-material.service';
import { MaterialService } from '../../materials/material.service';

@Component({
  selector: 'app-product-material-list',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './product-material-list.html',
  styleUrl: './product-material-list.scss'
})
export class ProductMaterialList implements OnInit {

  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly fb = inject(FormBuilder);

  private readonly productService = inject(ProductService);
  private readonly materialService = inject(MaterialService);
  private readonly productMaterialService =
    inject(ProductMaterialService);

  productId = '';

  readonly product = signal<Product | null>(null);
  readonly productMaterials = signal<ProductMaterial[]>([]);
  readonly materials = signal<Material[]>([]);

  editingId: string | null = null;

  readonly materialForm = this.fb.nonNullable.group({
    materialId: ['', Validators.required],

    baseQuantity: [0, [
      Validators.required,
      Validators.min(0.001)
    ]]
  });

  ngOnInit(): void {

    const id = this.route.snapshot.paramMap.get('id');

    if (!id) {
      this.router.navigate(['/products']);
      return;
    }

    this.productId = id;

    this.loadProduct();
    this.loadProductMaterials();
    this.loadMaterials();
  }

  private loadProduct(): void {

    this.productService
      .findById(this.productId)
      .subscribe({
        next: (product) => {
          this.product.set(product);
        },

        error: (error) => {
          console.error(
            'Error al cargar producto',
            error
          );
        }
      });
  }

  private loadProductMaterials(): void {

    this.productMaterialService
      .findByProductId(this.productId)
      .subscribe({
        next: (materials) => {
          this.productMaterials.set(materials);
        },

        error: (error) => {
          console.error(
            'Error al cargar materiales del producto',
            error
          );
        }
      });
  }

  private loadMaterials(): void {

    this.materialService
      .findAll()
      .subscribe({
        next: (materials) => {
          this.materials.set(
            materials.filter(material => material.active)
          );
        },

        error: (error) => {
          console.error(
            'Error al cargar materiales',
            error
          );
        }
      });
  }

  availableMaterials(): Material[] {

    const assignedMaterialIds = new Set(
      this.productMaterials().map(
        productMaterial => productMaterial.materialId
      )
    );

    return this.materials().filter(
      material => !assignedMaterialIds.has(material.id)
    );
  }

  saveMaterial(): void {

    if (this.materialForm.invalid) {
      this.materialForm.markAllAsTouched();
      return;
    }

    const value = this.materialForm.getRawValue();

    if (this.editingId) {

      this.productMaterialService
        .updateBaseQuantity(
          this.productId,
          this.editingId,
          {
            baseQuantity: value.baseQuantity
          }
        )
        .subscribe({
          next: () => {
            this.resetForm();
            this.loadProductMaterials();
          },

          error: (error) => {
            console.error(
              'Error al actualizar cantidad',
              error
            );
          }
        });

      return;
    }

    this.productMaterialService
      .addMaterial(
        this.productId,
        {
          materialId: value.materialId,
          baseQuantity: value.baseQuantity
        }
      )
      .subscribe({
        next: () => {
          this.resetForm();
          this.loadProductMaterials();
        },

        error: (error) => {
          console.error(
            'Error al agregar material',
            error
          );
        }
      });
  }

  editMaterial(productMaterial: ProductMaterial): void {

    this.editingId = productMaterial.id;

    this.materialForm.patchValue({
      materialId: productMaterial.materialId,
      baseQuantity: productMaterial.baseQuantity
    });
  }

  removeMaterial(productMaterialId: string): void {

    this.productMaterialService
      .removeMaterial(
        this.productId,
        productMaterialId
      )
      .subscribe({
        next: () => {
          this.loadProductMaterials();
        },

        error: (error) => {
          console.error(
            'Error al retirar material',
            error
          );
        }
      });
  }

  cancelEdit(): void {
    this.resetForm();
  }

  private resetForm(): void {

    this.editingId = null;

    this.materialForm.reset({
      materialId: '',
      baseQuantity: 0
    });
  }

  goBack(): void {
    this.router.navigate(['/products']);
  }
}