import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Order} from "../model/order";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private baseUrl = "http://localhost:8080/orders";


  constructor(private httpClient: HttpClient) {
  }

  getOrderList(): Observable<Order[]> {
    return this.httpClient.get<Order[]>(`${this.baseUrl}/all`);
  }

  createOrder(order: Order): Observable<Order> {
    console.log(order);
    // @ts-ignore
    return this.httpClient.post(`${this.baseUrl}`, order);
  }

  getOrderById(id: number): Observable<Order> {
    // @ts-ignore
    return this.httpClient.get(`${this.baseUrl}/${id}`);
  }

  updateOrder(order: Order, id: number): Observable<Order> {
    // @ts-ignore
    return this.httpClient.patch(`${this.baseUrl}/${id}`, order);
  }

  deleteOrder(id: number): Observable<Object> {
    // @ts-ignore
    return this.httpClient.delete(`${this.baseUrl}/${id}`);
  }
}
