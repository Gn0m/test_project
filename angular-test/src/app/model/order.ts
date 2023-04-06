import {OrderLine} from "./order-line";

export class Order {
  id: number;
  client: number;
  date: string;
  address: string;
  ordersLine: OrderLine[] = [];

  constructor() {
    this.id = Number.NaN;
    this.client = Number.NaN;
    this.date = "";
    this.address = "";
  }
}
