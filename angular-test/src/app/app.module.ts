import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {ListComponent} from './list/list.component';
import {HttpClientModule} from "@angular/common/http";
import {CreateOrderComponent} from './create-order/create-order.component';
import {AppRoutingModule} from "./app-routing.module";
import {FormsModule} from "@angular/forms";
import {UpdateOrderComponent} from './update-order/update-order.component';
import {DetailOrderComponent} from './detail-order/detail-order.component';
import {MatTabsModule} from "@angular/material/tabs";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { CreateProductComponent } from './create-product/create-product.component';
import { ProductListComponent } from './product-list/product-list.component';
import { DetailProductComponent } from './detail-product/detail-product.component';
import { UpdateProductComponent } from './update-product/update-product.component';
import {MatSelectModule} from "@angular/material/select";
import {NgSelectModule} from "@ng-select/ng-select";
import {MatTableModule} from "@angular/material/table";

@NgModule({
  declarations: [
    AppComponent,
    ListComponent,
    CreateOrderComponent,
    UpdateOrderComponent,
    DetailOrderComponent,
    CreateProductComponent,
    ProductListComponent,
    DetailProductComponent,
    UpdateProductComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        MatTabsModule,
        BrowserAnimationsModule,
        MatSelectModule,
        NgSelectModule,
        MatTableModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {

}
