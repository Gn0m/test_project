INSERT INTO PUBLIC.ORDERS (client, date, address)
VALUES (1, '2020-11-01', 'г.Ростов-на-Дону,пр-кт 40 летия Победы 75/1'),
       (2, '2020-12-30', 'г.Ростов-на-Дону,ул. Вятская 45'),
       (3, '2021-02-14', 'г.Ростов-на-Дону,пр-кт Шолохова 184'),
       (4, '2022-05-09', 'г.Ростов-на-Дону,ул. Текучева 44');

INSERT INTO PUBLIC.GOODS (NAME, PRICE)
VALUES ('Молоток слесарный 1000г кв. боек деревянная ручка SPARTA', 499),
       ('Гвоздодер шестигранный крашеный 600мм', 449),
       ('Плиткорез 600мм рельсовый MTX PROFESSIONAL MATRIX', 7199),
       ('Струбцина пружинная 100мм TOP TOOLS', 87);

INSERT INTO PUBLIC.ORDER_LINE (ORDER_ID, GOODS_ID, COUNT)
VALUES (1, 1, 25),
       (2, 2, 41),
       (3, 3, 12),
       (4, 4, 5);