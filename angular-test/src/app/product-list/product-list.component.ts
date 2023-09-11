import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {Product} from "../model/product";
import {ProductService} from "../service/product.service";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent {

  products: Product[] = [];

  constructor(private productService: ProductService,
              private router: Router) {
    this.getProducts();
  }

  getProducts() {
    this.productService.getProductList().subscribe(data => {
      this.products = data;
    })
  }

  updateProduct(id: number) {
    this.router.navigate([{outlets: {right: ['update-product', id]}}]);
  }

  deleteProduct(id: number) {
    this.productService.deleteProduct(id).subscribe(() => {
      this.getProducts();
    });
  }

  productInfo(id: number) {
    this.router.navigate([{outlets: {right: ['product-info', id]}}]);
  }

}
