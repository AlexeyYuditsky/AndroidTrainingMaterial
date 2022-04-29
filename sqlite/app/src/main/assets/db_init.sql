CREATE TABLE products
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    company TEXT NOT NULL,
    product_count INTEGER,
    price INTEGER
);
   
INSERT INTO products VALUES
(?, 'iPhone 13', 'Apple', 3, 76000),
(?, 'iPhone 12', 'Apple', 2, 51000),
(?, 'Galaxy S21', 'Samsung', 2, 56000),
(?, 'Galaxy S20', 'Samsung', 1, 41000),
(?, 'P40 Pro', 'Huawei', 5, 36000)