import { Routes } from '@angular/router';
import { ClientList } from './features/clients/client-list/client-list';

export const routes: Routes = [
  {
    path: 'clients',
    component: ClientList
  },
  {
    path: '',
    redirectTo: 'clients',
    pathMatch: 'full'
  }
];
