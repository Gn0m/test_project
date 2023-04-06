import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {ListComponent} from "./list/list.component";
import {CreateOrderComponent} from "./create-order/create-order.component";
import {UpdateOrderComponent} from "./update-order/update-order.component";
import {DetailOrderComponent} from "./detail-order/detail-order.component";
import {ProductListComponent} from "./product-list/product-list.component";
import {CreateProductComponent} from "./create-product/create-product.component";
import {UpdateProductComponent} from "./update-product/update-product.component";
import {DetailProductComponent} from "./detail-product/detail-product.component";

const routes: Routes = [
  {path: 'list', component: ListComponent, outlet: 'left'},
  {path: 'list/create-order', component: CreateOrderComponent, outlet: 'left'},
  {path: '', redirectTo: 'list', pathMatch: 'full', outlet: 'left'},
  {path: 'update-order/:id', component: UpdateOrderComponent, outlet: 'left'},
  {path: 'order-info/:id', component: DetailOrderComponent, outlet: 'left'},
  {path: 'product-list', component: ProductListComponent, outlet: 'right'},
  {path: 'product-list/create-product', component: CreateProductComponent, outlet: 'right'},
  {path: '', redirectTo: 'product-list', pathMatch: 'full', outlet: 'right'},
  {path: 'update-product/:id', component: UpdateProductComponent, outlet: 'right'},
  {path: 'product-info/:id', component: DetailProductComponent, outlet: 'right'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
