export interface User {
    id: number;
    username: string;
    password: string;
    authorities?: string[];
    firstname: string;
    lastname: string;
    email: string;
  }

export interface loginRequest {
  username: string;
  password: string;
}

export interface registerRequest {
  username: string;
  password: string;
  firstname: string;
  lastname: string;
  email: string;
}

export interface booleanApiResponse {
  success: boolean;
  message: string;
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

export type productsDexie = {
  id: string;
  products: Product[];
  timestamp: number;
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

export interface PurchaseOrder {
  purchase_id: string;
  user_id: number;
  items_purchased: string;
  item_ids: string;
  amount: number;
  email: string;
  receipt_url:string;
  created_at: number;
}

export interface ProductReview {
  purchase_id: string;
  product_id: string;
  product_name: string;
  user_id: number;
  rating: number;
  review: string;
  created_at: number;
}


export interface Slider {
  image: string
  url: string
}