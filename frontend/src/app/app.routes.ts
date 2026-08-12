import { Routes } from '@angular/router';
import { ClientList } from './features/clients/client-list/client-list';
import { ClientForm } from './features/clients/client-form/client-form';

export const routes: Routes = [
  {
    path: 'clients',
    component: ClientList
  },
  {
    path: 'clients/new',
    component: ClientForm  
  },
  {
  path: 'clients/:id/edit',
  component: ClientForm
  },
  {
    path: '',
    redirectTo: 'clients',
    pathMatch: 'full'
  }
];
