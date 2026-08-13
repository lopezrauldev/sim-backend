import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

import { Material } from '../../../shared/models/material';
import { MaterialService } from '../material.service';

@Component({
  selector: 'app-material-list',
  imports: [RouterLink],
  templateUrl: './material-list.html',
  styleUrl: './material-list.scss'
})
export class MaterialList implements OnInit {

  private readonly materialService = inject(MaterialService);

  readonly materials = signal<Material[]>([]);

  ngOnInit(): void {
    this.loadMaterials();
  }

  loadMaterials(): void {
    this.materialService.findAll().subscribe({
      next: (materials) => {
        this.materials.set(materials);
      },
      error: (error) => {
        console.error('Error al cargar materiales', error);
      }
    });
  }

  deleteMaterial(id: string): void {
    this.materialService.delete(id).subscribe({
      next: () => {
        this.loadMaterials();
      },
      error: (error) => {
        console.error('Error al desactivar material', error);
      }
    });
  }
}
