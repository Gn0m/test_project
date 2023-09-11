import {Component} from '@angular/core';
import {Order} from "../model/order";
import {OrderService} from "../service/order.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent {

  orders: Order[] = [];

  constructor(private orderService: OrderService,
              private router: Router) {
    this.getOrders();
  }

  getOrders() {
    this.orderService.getOrderList().subscribe(data => {
      this.orders = data;
      console.log(data)
    })
  }

  updateOrder(id: number) {
    this.router.navigate([{outlets: {left: ['update-order', id]}}]);
  }

  deleteOrder(id: number) {
    this.orderService.deleteOrder(id).subscribe(data => {
      console.log(data)
      this.getOrders();
    });
  }

  orderInfo(id: number) {
    this.router.navigate([{outlets: {left: ['order-info', id]}}]);
  }

}
