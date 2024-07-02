export interface User {
    id: number;
    username: string;
    password: string;
    authorities?: string[];
    firstname: string;
    lastname: string;
    email: string;
  }

export type Product = {
  id: number;
  productname: string;
  description: string;
  tags: string;
  url: string;
  price: number;
  rating: number;
  image: string;
}

export interface CartItem {
  item: Product;
  quantity: number;
}

export interface Cart {
  items: CartItem[];
}

export interface session {
  sessionId: string;
}