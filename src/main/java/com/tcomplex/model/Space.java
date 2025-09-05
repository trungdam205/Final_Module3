package com.tcomplex.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Space {
    private String code;          // XXX-XX-XX
    private String status;        // Trống | Hạ tầng | Đầy đủ
    private BigDecimal area;      // > 20
    private int floor;            // 1..15
    private String type;          // Văn phòng chia sẻ | Văn phòng trọn gói
    private BigDecimal price;     // > 1_000_000 (VND)
    private LocalDate startDate;  // dd/MM/yyyy -> LocalDate
    private LocalDate endDate;

    public Space() {}

    public Space(String code, String status, BigDecimal area, int floor, String type,
                 BigDecimal price, LocalDate startDate, LocalDate endDate) {
        this.code = code;
        this.status = status;
        this.area = area;
        this.floor = floor;
        this.type = type;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters & setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getArea() { return area; }
    public void setArea(BigDecimal area) { this.area = area; }
    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
