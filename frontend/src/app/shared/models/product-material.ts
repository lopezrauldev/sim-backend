import { MaterialUnit } from './material';

export interface ProductMaterial {
  id: string;
  productId: string;
  materialId: string;
  materialCode: string;
  materialName: string;
  materialUnit: MaterialUnit;
  baseQuantity: number;
}

export interface ProductMaterialRequest {
  materialId: string;
  baseQuantity: number;
}

export interface ProductMaterialQuantityRequest {
  baseQuantity: number;
}