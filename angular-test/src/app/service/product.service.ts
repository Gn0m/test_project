import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Product} from "../model/product";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrl = "http://localhost:8080/goods";


  constructor(private httpClient: HttpClient) {
  }

  getProductList(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(`${this.baseUrl}/all`);
  }

  createProduct(product: Product): Observable<Product> {
    // @ts-ignore
    return this.httpClient.post(`${this.baseUrl}`, product);
  }

  getProductById(id: number): Observable<Product> {
    // @ts-ignore
    return this.httpClient.get(`${this.baseUrl}/${id}`);
  }

  updateProduct(product: Product, id: number): Observable<Product> {
    // @ts-ignore
    return this.httpClient.patch(`${this.baseUrl}/${id}`, product);
  }

  deleteProduct(id: number): Observable<Object> {
    // @ts-ignore
    return this.httpClient.delete(`${this.baseUrl}/${id}`);
  }
}
