INSERT INTO products(id, title, price)
VALUES
(1, 'Гарри Поттер', 1000),
(2, 'Замок Даркулы', 1000),
(3, 'Уэнсдей', 1000),
(4, 'Лазертаг', 1000),
(5, 'Among US', 1000);

ALTER SEQUENCE product_seq RESTART WITH 6;