CREATE TABLE products
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    company TEXT NOT NULL,
    product_count INTEGER DEFAULT 0,
    price INTEGER
);
   
INSERT INTO products VALUES
(?, 'iPhone 13', 'Apple', 3, 76000),
(?, 'iPhone 12', 'Apple', 3, 51000),
(?, 'iPhone 11', 'Apple', 3, 43000),
(?, 'Galaxy S21', 'Samsung', 2, 56000),
(?, 'Galaxy S20', 'Samsung', 1, 51000),
(?, 'P40 Pro', 'Huawei', 5, 36000),
(?, 'Nokia XR20', 'HMD Global', 2, 45000),
(?, 'T11 Pro', 'Xiaomi', 1, 54000)