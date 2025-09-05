package com.tcomplex.web;

import com.tcomplex.dao.SpaceDao;
import com.tcomplex.model.Space;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(urlPatterns = "/spaces")
public class SpaceListServlet extends HttpServlet {
    private final SpaceDao dao = new SpaceDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        // Lấy & chuẩn hóa tham số
        String type = trimToNull(req.getParameter("type")); // "Văn phòng chia sẻ" | "Văn phòng trọn gói" | null
        Integer floor = parseInt(req.getParameter("floor")); // 1..15 | null
        BigDecimal minPrice = parseDecimal(req.getParameter("minPrice"));
        BigDecimal maxPrice = parseDecimal(req.getParameter("maxPrice"));

        // Ràng buộc hợp lệ đơn giản
        if (floor != null && (floor.intValue() < 1 || floor.intValue() > 15)) {
            floor = null; // bỏ qua nếu ngoài khoảng
        }
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            // hoán đổi nếu nhập ngược
            BigDecimal tmp = minPrice; minPrice = maxPrice; maxPrice = tmp;
        }

        try {
            List<Space> spaces;
            boolean noFilter = (type == null && floor == null && minPrice == null && maxPrice == null);

            if (noFilter) {
                // Không có điều kiện -> lấy tất cả (sắp xếp theo diện tích)
                spaces = dao.listAllSortedByArea();
            } else {
                // Có ít nhất 1 điều kiện -> tìm kiếm (đủ 4 loại và có thể kết hợp)
                spaces = dao.search(type, floor, minPrice, maxPrice, 200, 0);
            }

            // Giữ lại tham số để hiển thị lại trên modal tìm kiếm
            req.setAttribute("type", type);
            req.setAttribute("floor", floor);
            req.setAttribute("minPrice", minPrice);
            req.setAttribute("maxPrice", maxPrice);

            req.setAttribute("spaces", spaces);
            req.getRequestDispatcher("/WEB-INF/views/space-list.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /* ===================== helpers (Java 8 compatible) ===================== */

    private static String trimToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private static Integer parseInt(String s) {
        try {
            if (s == null) return null;
            String t = s.trim();
            return t.isEmpty() ? null : Integer.valueOf(t);
        } catch (Exception e) {
            return null;
        }
    }

    private static BigDecimal parseDecimal(String s) {
        try {
            if (s == null) return null;
            String t = s.trim();
            return t.isEmpty() ? null : new BigDecimal(t);
        } catch (Exception e) {
            return null;
        }
    }
}
