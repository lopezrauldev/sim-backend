import { Routes } from '@angular/router';
import { MainLayout } from './layout/main-layout/main-layout';
import { ClientList } from './features/clients/client-list/client-list';
import { ClientForm } from './features/clients/client-form/client-form';
import { MaterialList } from './features/materials/material-list/material-list';
import { MaterialForm } from './features/materials/material-form/material-form';
import { ProductList } from './features/products/product-list/product-list';
import { ProductForm } from './features/products/product-form/product-form';
import { ProductMaterialList } from './features/products/product-material-list/product-material-list';
import { QuoteList } from './features/quotes/quote-list/quote-list';
import { QuoteForm } from './features/quotes/quote-form/quote-form';
import { QuoteDetail } from './features/quotes/quote-detail/quote-detail';

export const routes: Routes = [
  {
    path: '',
    component: MainLayout,
    children: [
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
        path: 'materials',
        component: MaterialList
      },
      {
        path: 'materials/new',
        component: MaterialForm
      },
      {
        path: 'materials/:id/edit',
        component: MaterialForm
      },


      {
        path: 'products',
        component: ProductList
      },
      {
        path: 'products/new',
        component: ProductForm
      },
      {
        path: 'products/:id/edit',
        component: ProductForm
      },

      {
        path: 'products/:id/materials',
        component: ProductMaterialList
      },

      {
        path: 'quotes',
        component: QuoteList
      },
      {
        path: 'quotes/new',
        component: QuoteForm
      },

      {
        path: 'quotes/:id/edit',
        component: QuoteForm
      },

      {
        path: 'quotes/:id',
        component: QuoteDetail
      },

      {
        path: '',
        redirectTo: 'clients',
        pathMatch: 'full'
      }
    ]
  }
];
