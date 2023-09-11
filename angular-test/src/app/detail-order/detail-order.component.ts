import {Component} from '@angular/core';
import {Order} from "../model/order";
import {ActivatedRoute, Router} from "@angular/router";
import {OrderService} from "../service/order.service";
import {OrderLine} from "../model/order-line";

@Component({
  selector: 'app-detail-order',
  templateUrl: './detail-order.component.html',
  styleUrls: ['./detail-order.component.css']
})
export class DetailOrderComponent {

  id: number;
  order: Order;
  ordersLine: Set<OrderLine>;

  constructor(private route: ActivatedRoute, private service: OrderService, private router: Router) {
    this.id = route.snapshot.params['id'];
    this.order = new Order();
    this.ordersLine = new Set<OrderLine>();
    this.service.getOrderById(this.id).subscribe(data => {
      this.order = data;
      this.ordersLine = data.ordersLine;
    })
  }

  goToOrderList() {
    this.router.navigate([{outlets: {left: ['list']}}]);
  }
}
