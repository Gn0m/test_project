import {Product} from "./product";

export class OrderLine {
  goods_id: Product = new Product();
  count: number = Number.NaN;

  constructor() {
  }

  public sum() {
    return this.goods_id.price * this.count
  }

  public clone(product: Product, count: number) {
    this.count = count;
    this.goods_id = product;
  }

  public fold(count: number) {
    this.count += count;
  }


}
