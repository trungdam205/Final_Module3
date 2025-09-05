package com.tcomplex.service;

import com.tcomplex.dao.SpaceDao;
import com.tcomplex.model.Space;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SpaceService {

    private static final Set<String> ALLOWED_STATUS;
    private static final Set<String> ALLOWED_TYPES;
    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    static {
        Set<String> s1 = new HashSet<String>(Arrays.asList("Trống","Hạ tầng","Đầy đủ"));
        Set<String> s2 = new HashSet<String>(Arrays.asList("Văn phòng chia sẻ","Văn phòng trọn gói"));
        ALLOWED_STATUS = Collections.unmodifiableSet(s1);
        ALLOWED_TYPES  = Collections.unmodifiableSet(s2);
    }

    private final SpaceDao dao = new SpaceDao();

    public static class ValidationResult {
        public final Map<String, String> errors = new LinkedHashMap<String, String>();
        public Space space;
        public boolean ok() { return errors.isEmpty(); }
    }

    public ValidationResult validateAndBuild(Map<String, String> p) {
        ValidationResult v = new ValidationResult();

        String code = trim(p.get("code"));
        if (code == null || !code.matches("^[A-Z0-9]{3}-[A-Z0-9]{2}-[A-Z0-9]{2}$"))
            v.errors.put("code", "Mã không đúng định dạng XXX-XX-XX (chỉ A–Z, 0–9)");

        String status = trim(p.get("status"));
        if (status == null || !ALLOWED_STATUS.contains(status)) v.errors.put("status", "Trạng thái không hợp lệ");

        BigDecimal area = parseDecimal(p.get("area"));
        if (area == null || area.compareTo(new BigDecimal("20")) <= 0)
            v.errors.put("area", "Diện tích phải > 20");

        Integer floor = parseInt(p.get("floor"));
        if (floor == null || floor.intValue() < 1 || floor.intValue() > 15)
            v.errors.put("floor", "Tầng phải từ 1 đến 15");

        String type = trim(p.get("type"));
        if (type == null || !ALLOWED_TYPES.contains(type)) v.errors.put("type", "Loại văn phòng không hợp lệ");

        BigDecimal price = parseDecimal(p.get("price"));
        if (price == null || price.compareTo(new BigDecimal("1000000")) <= 0)
            v.errors.put("price", "Giá phải > 1.000.000 VND");

        LocalDate start = parseDate(p.get("startDate"));
        LocalDate end = parseDate(p.get("endDate"));
        if (start == null) v.errors.put("startDate", "Ngày bắt đầu không hợp lệ (dd/MM/yyyy)");
        if (end == null) v.errors.put("endDate", "Ngày kết thúc không hợp lệ (dd/MM/yyyy)");
        if (start != null && end != null && end.isBefore(start.plusMonths(6))) {
            v.errors.put("endDate", "Ngày kết thúc phải sau ngày bắt đầu ít nhất 6 tháng");
        }

        if (v.errors.isEmpty()) {
            Space s = new Space(code, status, area, floor == null ? 0 : floor.intValue(),
                    type, price, start, end);
            v.space = s;
        }
        return v;
    }

    public boolean codeExists(String code) throws Exception {
        return dao.exists(code);
    }

    public void create(Space s) throws Exception {
        dao.insert(s);
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }

    private static BigDecimal parseDecimal(String s) {
        try { return (s == null || s.trim().isEmpty()) ? null : new BigDecimal(s.trim()); }
        catch (Exception e) { return null; }
    }
    private static Integer parseInt(String s) {
        try { return (s == null || s.trim().isEmpty()) ? null : Integer.valueOf(s.trim()); }
        catch (Exception e) { return null; }
    }
    private static LocalDate parseDate(String s) {
        try { return (s == null || s.trim().isEmpty()) ? null : LocalDate.parse(s.trim(), F); }
        catch (Exception e) { return null; }
    }
}
