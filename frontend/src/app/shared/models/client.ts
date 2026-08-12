export interface Client {
    id: string;
    type: 'PERSONA' | 'EMPRESA';
    documentNumber: string;
    name: string | null;
    businessName: string | null;
    address: string | null;
    department: string | null;
    province: string | null;
    district: string | null;
    email: string | null;
    phone: string | null;
    active: boolean;
}

export interface ClientRequest {
  type: 'PERSONA' | 'EMPRESA';
  documentNumber: string;
  name: string | null;
  businessName: string | null;
  address: string | null;
  department: string | null;
  province: string | null;
  district: string | null;
  email: string | null;
  phone: string | null;
}
