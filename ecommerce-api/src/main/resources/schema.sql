CREATE TABLE roles (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(20) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE users (
  id bigint NOT NULL AUTO_INCREMENT,
  email varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  enabled boolean NOT NULL DEFAULT TRUE,
  PRIMARY KEY (id),
  UNIQUE KEY (username),
  UNIQUE KEY (email)
);

CREATE TABLE user_roles (
  user_id bigint NOT NULL,
  role_id int NOT NULL,
  PRIMARY KEY (user_id,role_id),
  KEY (role_id),
  FOREIGN KEY (role_id) REFERENCES roles (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE(name)
);

CREATE TABLE brands (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE(name)
);

CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT NOT NULL,
    brand_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (brand_id) REFERENCES brands(id)
);

CREATE TABLE product_images (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    original_filename VARCHAR(255) NOT NULL,
    system_filename VARCHAR(255) NOT NULL,
    mimetype VARCHAR(255) NOT NULL,
    display_order INT DEFAULT 0 NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);