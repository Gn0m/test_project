import {Component} from '@angular/core';
import {Order} from "../model/order";
import {OrderService} from "../service/order.service";
import {Router} from "@angular/router";
import {ProductService} from "../service/product.service";
import {Product} from "../model/product";
import {OrderLine} from "../model/order-line";
import {OrderDAO} from "../DAO/order-dao";

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css']
})
export class CreateOrderComponent {

  order: Order = new Order();
  products: Product[] = [];
  orderLine: OrderLine = new OrderLine();

  constructor(private orderService: OrderService,
              private router: Router, private productService: ProductService) {
    this.getProducts();
  }

  onSubmit() {
    let order: OrderDAO = new OrderDAO();
    order.convert(this.order);
    this.orderService.createOrder(order).subscribe(error => console.log(error));
    this.goToOrderList();
  }

  saveOrder() {
    console.log(this.orderLine.goods_id.id + " goods_id saveOrder");
    this.productService.getProductById(this.orderLine.goods_id.id).subscribe(data => {
      let line: OrderLine = new OrderLine();
      line.clone(data, this.orderLine.count);
      this.order.addOrderLine(line);
    });
  }

  goToOrderList() {
    this.router.navigate([{outlets: {left: ['list']}}]);
  }

  private getProducts() {
    this.productService.getProductList().subscribe(data => {
      this.products = data;
    })
  }
}
