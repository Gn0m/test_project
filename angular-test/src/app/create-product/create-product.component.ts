import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {Product} from "../model/product";
import {ProductService} from "../service/product.service";

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css']
})
export class CreateProductComponent {
  product: Product = new Product();

  constructor(private productService: ProductService,
              private router: Router) {
  }

  onSubmit() {
    this.saveOrder();
  }

  saveOrder() {
    this.productService.createProduct(this.product).subscribe(error => console.log(error));
    this.goToProductList();
  }

  goToProductList() {
    this.router.navigate([{outlets: {right: ['product-list']}}]);
  }
}
