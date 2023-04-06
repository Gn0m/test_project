CREATE TABLE PUBLIC.orders
(
    id      INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    client  INT,
    date    DATE,
    address VARCHAR(255)
);
CREATE TABLE PUBLIC.order_line
(
    id       INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    order_id INT,
    goods_id INT,
    count    INT
);
CREATE TABLE PUBLIC.goods
(
    id    INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name  VARCHAR(255),
    price DOUBLE
);

ALTER TABLE PUBLIC.ORDER_LINE ADD FOREIGN KEY (order_id) REFERENCES ORDERS(id);
ALTER TABLE PUBLIC.ORDER_LINE ADD FOREIGN KEY (goods_id) REFERENCES GOODS(id);