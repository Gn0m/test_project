import {Component} from '@angular/core';
import {OrderService} from "../service/order.service";
import {Order} from "../model/order";
import {ActivatedRoute, Router} from "@angular/router";
import {ProductService} from "../service/product.service";
import {Product} from "../model/product";

@Component({
  selector: 'app-update-order',
  templateUrl: './update-order.component.html',
  styleUrls: ['./update-order.component.css']
})
export class UpdateOrderComponent {

  order: Order = new Order();
  id: number;
  products: Product[] = [];
  productValue: number = Number.NaN;

  constructor(private orderService: OrderService,
              private activityRoute: ActivatedRoute,
              private router: Router, private productService: ProductService) {

    this.id = this.activityRoute.snapshot.params['id'];
    orderService.getOrderById(this.id).subscribe(data => {
      this.order = data;
      this.productValue = data.ordersLine[0].goods_id.id;
    }, error => console.log(error));
    this.getProducts();
  }

  onSubmit() {
    this.updateOrder()
  }

  goToOrderList() {
    //this.router.navigate(['/list']);
    this.router.navigate([{outlets: {left: ['list']}}]);
  }

  updateOrder() {
    this.productService.getProductById(this.productValue).subscribe(product => {
      this.orderService.getOrderById(this.id).subscribe(data => {
        data.address = this.order.address;
        data.date = this.order.date;
        data.client = this.order.client;
        data.ordersLine[0].count = this.order.ordersLine[0].count;
        data.ordersLine[0].goods_id = product;

        this.orderService.updateOrder(data, this.id).subscribe(data => {
          console.log(data);
        }, error => console.log(error));
      });
    });

    this.goToOrderList();
  }

  private getProducts() {
    this.productService.getProductList().subscribe(data => {
      this.products = data;
    })
  }

}
