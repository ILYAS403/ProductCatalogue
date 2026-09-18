export interface Product {
  id: number;
  name: string;
  category: string;
  price: number;
  createdAt: string;
}

export interface CreateProduct {
  name: string;
  category: string;
  price: number;
}

export interface LoginRequest {
  username: string;
  password: string;
}