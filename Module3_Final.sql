-- Đăng nhập: mysql -u root -p
CREATE DATABASE tcomplex CHARACTER SET utf8mb4;
CREATE USER 'tcomplex'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON tcomplex.* TO 'tcomplex'@'localhost';
FLUSH PRIVILEGES;
USE tcomplex;

CREATE TABLE space (
  code       VARCHAR(10) PRIMARY KEY,
  status     ENUM('Trống','Hạ tầng','Đầy đủ') NOT NULL,
  area_m2    DECIMAL(10,2) NOT NULL,
  floor_no   INT NOT NULL,
  type       ENUM('Văn phòng chia sẻ','Văn phòng trọn gói') NOT NULL,
  price_vnd  DECIMAL(15,0) NOT NULL,
  start_date DATE NOT NULL,
  end_date   DATE NOT NULL
);
CREATE INDEX idx_space_type       ON space(type);
CREATE INDEX idx_space_floor      ON space(floor_no);
CREATE INDEX idx_space_price      ON space(price_vnd);
