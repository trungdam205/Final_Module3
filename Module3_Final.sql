CREATE DATABASE tcomplx;
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
INSERT INTO space (code, status, area_m2, floor_no, type, price_vnd, start_date, end_date) VALUES
                                                                                               ('A01-01-01', 'Trống',    25.00,  1, 'Văn phòng chia sẻ',  1500000, '2025-01-01', '2025-12-31'),
                                                                                               ('A01-01-02', 'Hạ tầng',  35.50,  2, 'Văn phòng chia sẻ',  2000000, '2025-02-01', '2026-01-31'),
                                                                                               ('A01-01-03', 'Đầy đủ',   45.00,  3, 'Văn phòng trọn gói', 3500000, '2025-03-01', '2026-02-28'),
                                                                                               ('B02-02-01', 'Trống',    60.00,  5, 'Văn phòng trọn gói', 5000000, '2025-01-15', '2026-01-14'),
                                                                                               ('B02-02-02', 'Đầy đủ',   22.00,  4, 'Văn phòng chia sẻ',  1800000, '2025-04-01', '2026-03-31'),
                                                                                               ('C03-03-01', 'Hạ tầng',  80.00, 10, 'Văn phòng trọn gói', 7000000, '2025-05-01', '2026-04-30');
CREATE INDEX idx_space_type       ON space(type);
CREATE INDEX idx_space_floor      ON space(floor_no);
CREATE INDEX idx_space_price      ON space(price_vnd);
