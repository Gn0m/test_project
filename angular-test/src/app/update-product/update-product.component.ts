import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Product} from "../model/product";
import {ProductService} from "../service/product.service";

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css']
})
export class UpdateProductComponent {

  product: Product = new Product();
  id: number;

  constructor(private productService: ProductService,
              private activityRoute: ActivatedRoute,
              private router: Router) {
    this.id = this.activityRoute.snapshot.params['id'];
    productService.getProductById(this.id).subscribe(data => {
      this.product = data;
    }, error => console.log(error));
  }

  onSubmit() {
    this.productService.updateProduct(this.product, this.id).subscribe(data => {
        console.log(data)
        this.goToProductList();
      }
      , error => console.log(error));
  }

  goToProductList() {
    //this.router.navigate(['/list']);
    this.router.navigate([{outlets: {right: ['product-list']}}]);
  }
}
