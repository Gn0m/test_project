import {Component} from '@angular/core';
import {OrderService} from "../service/order.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ProductService} from "../service/product.service";
import {Product} from "../model/product";
import {OrderDAO} from "../DAO/order-dao";
import {OrderLine} from "../model/order-line";

@Component({
  selector: 'app-update-order',
  templateUrl: './update-order.component.html',
  styleUrls: ['./update-order.component.css']
})
export class UpdateOrderComponent {

  orderDAO: OrderDAO = new OrderDAO();
  products: Product[] = [];


  constructor(private orderService: OrderService,
              private activityRoute: ActivatedRoute,
              private router: Router, private productService: ProductService) {
    this.orderDAO.id = this.activityRoute.snapshot.params['id'];
    orderService.getOrderDAOById(this.orderDAO.id).subscribe(data => {
      this.orderDAO = data;
    });
    this.getProducts();
  }

  onSubmit() {
    console.log(this.orderDAO.ordersLine.length + " inSubmit");
    this.orderService.updateOrder(this.orderDAO, this.orderDAO.id).subscribe(error => console.log(error));
    this.goToOrderList();
  }

  addOrderLine() {
    this.orderDAO.ordersLine.push(new OrderLine());
  }

  deleteLine(number: number) {
    this.orderDAO.ordersLine.splice(number, 1);
    console.log(this.orderDAO.ordersLine.length);
  }

  goToOrderList() {
    this.router.navigate([{outlets: {left: ['list']}}]);
  }

  private getProducts() {
    this.productService.getProductList().subscribe(data => {
      this.products = data;
    });
  }


}
