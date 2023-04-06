import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Product} from "../model/product";
import {ProductService} from "../service/product.service";

@Component({
  selector: 'app-detail-product',
  templateUrl: './detail-product.component.html',
  styleUrls: ['./detail-product.component.css']
})
export class DetailProductComponent {

  id: number;
  product: Product;

  constructor(private route: ActivatedRoute, private service: ProductService, private router: Router) {
    this.id = route.snapshot.params['id'];
    this.product = new Product();
    this.service.getProductById(this.id).subscribe(data => {
      this.product = data;
    })
  }

  goToProductList() {
    //this.router.navigate(['list']);
    this.router.navigate([{outlets: {right: ['product-list']}}]);
  }
}
