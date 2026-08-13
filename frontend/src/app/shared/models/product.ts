export interface Product {
  id: string;
  code: string;
  name: string;
  description: string | null;
  unit: ProductUnit;
  unitPrice: number;
  active: boolean;
}

export interface ProductRequest {
  code: string;
  name: string;
  description: string | null;
  unit: ProductUnit;
  unitPrice: number;
}

export type ProductUnit =
  | 'UND'
  | 'M'
  | 'M2'
  | 'M3'
  | 'KG'
  | 'GL'
  | 'LT'
  | 'JGO';