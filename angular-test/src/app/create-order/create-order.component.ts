import {Component} from '@angular/core';
import {Order} from "../model/order";
import {OrderService} from "../service/order.service";
import {Router} from "@angular/router";
import {ProductService} from "../service/product.service";
import {Product} from "../model/product";
import {OrderLine} from "../model/order-line";

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css']
})
export class CreateOrderComponent {

  order: Order = new Order();
  products: Product[] = [];
  orderLine: OrderLine = new OrderLine();
  productValue: number;

  constructor(private orderService: OrderService,
              private router: Router, private productService: ProductService) {
    this.getProducts();
    this.productValue = Number.NaN;
  }

  onSubmit() {
    this.saveOrder();
  }

  saveOrder() {
    console.log(this.order);
    this.productService.getProductById(this.productValue).subscribe(data => {
      this.orderLine.goods_id = data;
      this.order.ordersLine.push(this.orderLine);
      this.orderService.createOrder(this.order).subscribe(data => {
        console.log(data);
      }, error => console.log(error));
    });
    this.goToOrderList();
  }

  goToOrderList() {
    //this.router.navigate(['list']);
    this.router.navigate([{outlets: {left: ['list']}}]);
  }

  private getProducts() {
    this.productService.getProductList().subscribe(data => {
      this.products = data;
    })
  }
}
