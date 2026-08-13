export interface Material {
  id: string;
  code: string;
  name: string;
  description: string | null;
  supplier: string | null;
  category: MaterialCategory;
  weight: number | null;
  dimensions: string | null;
  unit: MaterialUnit;
  unitPrice: number;
  stock: number;
  active: boolean;
}

export interface MaterialRequest {
  code: string;
  name: string;
  description: string | null;
  supplier: string | null;
  category: MaterialCategory;
  weight: number | null;
  dimensions: string | null;
  unit: MaterialUnit;
  unitPrice: number;
  stock: number;
  active: boolean;
}

export type MaterialCategory =
  | 'DRYWALL'
  | 'PAINTING'
  | 'ELECTRICAL'
  | 'PLUMBING'
  | 'CARPENTRY'
  | 'METALWORK'
  | 'TOOLS'
  | 'SAFETY'
  | 'CONSUMABLE'
  | 'OTHER';

export type MaterialUnit =
  | 'UNIT'
  | 'METER'
  | 'SQUARE_METER'
  | 'CUBIC_METER'
  | 'KILOGRAM'
  | 'LITER'
  | 'GALLON'
  | 'BAG'
  | 'BOX'
  | 'ROLL'
  | 'SHEET'
  | 'BUCKET';

  