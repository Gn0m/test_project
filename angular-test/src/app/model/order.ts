import {OrderLine} from "./order-line";

export class Order {
  id: number = Number.NaN;
  client: number = Number.NaN;
  date: string = " ";
  address: string = " ";
  ordersLine: Set<OrderLine> = new Set<OrderLine>();


  constructor() {
    // TODO document why this constructor is empty
  }

  public addOrderLine(orderLine: OrderLine) {
    let exist: boolean = false;
    let firstExist: boolean = false;

    if (this.ordersLine.size > 0) {

      this.ordersLine.forEach(value => {

        if (this.equals(value, orderLine)) {
          firstExist = true;
          exist = false;
          value.fold(orderLine.count)
        } else {
          exist = true;
        }

      });

      if (exist && !firstExist) {
        this.ordersLine.add(orderLine);
        exist = false;
      }

      firstExist = false;
    } else {
      this.ordersLine.add(orderLine);
    }

  }

  private equals(value1: OrderLine, value2: OrderLine) {
    return value1.goods_id.id == value2.goods_id.id;
  }

}
