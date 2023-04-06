import {Product} from "./product";

export class OrderLine {
  goods_id: Product;
  count: Number;

  constructor() {
    this.goods_id = new Product();
    this.count = Number.NaN
  }

}
