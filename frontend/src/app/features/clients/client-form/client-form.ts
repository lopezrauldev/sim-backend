import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { ClientService } from '../../../core/services/client';
import { ClientRequest } from '../../../shared/models/client';

@Component({
  selector: 'app-client-form',
  imports: [ReactiveFormsModule],
  templateUrl: './client-form.html',
  styleUrl: './client-form.scss'
})
export class ClientForm implements OnInit {

  private readonly fb = inject(FormBuilder);
  private readonly clientService = inject(ClientService);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);

  clientId: string | null = null;

  clientForm = this.fb.group({
    type: ['', Validators.required],
    documentNumber: ['', Validators.required],
    name: [''],
    businessName: [''],
    address: [''],
    department: [''],
    province: [''],
    district: [''],
    email: ['', Validators.email],
    phone: ['']
  });

  ngOnInit(): void {
    this.clientId = this.route.snapshot.paramMap.get('id');

    this.clientForm.controls.type.valueChanges.subscribe(type => {
      const nameControl = this.clientForm.controls.name;
      const businessNameControl = this.clientForm.controls.businessName;
      const documentControl = this.clientForm.controls.documentNumber;

      if (type === 'PERSONA') {
        nameControl.setValidators(Validators.required);
        businessNameControl.clearValidators();

        documentControl.setValidators([
          Validators.required,
          Validators.pattern(/^\d{8}$/)
        ]);

        businessNameControl.setValue('');
      }

      if (type === 'EMPRESA') {
        businessNameControl.setValidators(Validators.required);
        nameControl.clearValidators();

        documentControl.setValidators([
          Validators.required,
          Validators.pattern(/^\d{11}$/)
        ]);

        nameControl.setValue('');
      }

      nameControl.updateValueAndValidity();
      businessNameControl.updateValueAndValidity();
      documentControl.updateValueAndValidity();
    });

    if (this.clientId) {
      this.loadClient(this.clientId);
    }
  }

  private loadClient(id: string): void {
    this.clientService.findById(id).subscribe({
      next: (client) => {
        this.clientForm.patchValue({
          type: client.type,
          documentNumber: client.documentNumber,
          name: client.name,
          businessName: client.businessName,
          address: client.address,
          department: client.department,
          province: client.province,
          district: client.district,
          email: client.email,
          phone: client.phone
        });
      },
      error: (error) => {
        console.error('Error al cargar cliente', error);
      }
    });
  }

  save(): void {
    if (this.clientForm.invalid) {
      return;
    }

    const formValue = this.clientForm.getRawValue();

    const client: ClientRequest = {
      type: formValue.type as 'PERSONA' | 'EMPRESA',
      documentNumber: formValue.documentNumber!,
      name: formValue.name,
      businessName: formValue.businessName,
      address: formValue.address,
      department: formValue.department,
      province: formValue.province,
      district: formValue.district,
      email: formValue.email,
      phone: formValue.phone
    };

    if (this.clientId) {
      this.clientService.update(this.clientId, client).subscribe({
        next: () => {
          this.router.navigate(['/clients']);
        },
        error: (error) => {
          console.error('Error al actualizar cliente', error);
        }
      });

      return;
    }

    this.clientService.create(client).subscribe({
      next: () => {
        this.router.navigate(['/clients']);
      },
      error: (error) => {
        console.error('Error al crear cliente', error);
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/clients']);
  }
}