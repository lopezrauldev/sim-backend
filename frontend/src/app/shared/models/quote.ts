import { ProductUnit } from './product';
import { MaterialUnit } from './material';

export interface Quote {
  id: string;
  number: string;
  clientId: string;
  date: string;
  items: QuoteItem[];
  subtotal: number;
  generalExpensesAndProfit: number;
  total: number;
  status: QuoteStatus;
}

export interface QuoteItem {
  id: string;
  productId: string;
  description: string;
  unit: ProductUnit;
  quantity: number;
  unitPrice: number;
  subtotal: number;
}

export interface QuoteRequest {
  clientId: string;
  items: QuoteItemRequest[];
}

export interface QuoteItemRequest {
  productId: string;
  quantity: number;
}

export interface QuoteUpdateRequest {
  items: QuoteItemRequest[];
}

export interface QuoteMaterial {
  materialId: string;
  materialCode: string;
  materialName: string;
  materialUnit: MaterialUnit;
  requiredQuantity: number;
}

export type QuoteStatus =
  | 'SAVED'
  | 'CANCELED';