package com.tcomplex.web;

import com.tcomplex.dao.SpaceDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/spaces/delete")
public class SpaceDeleteServlet extends HttpServlet {
    private final SpaceDao dao = new SpaceDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Hỗ trợ cả hai tên tham số: id hoặc code
        String code = firstNonBlank(req.getParameter("id"), req.getParameter("code"));

        if (code != null) {
            try {
                dao.delete(code.trim());
            } catch (Exception e) {
                // Có thể log ra để debug, tránh nuốt lỗi
                // e.printStackTrace();
            }
        }
        resp.sendRedirect(req.getContextPath() + "/spaces");
    }

    private static String firstNonBlank(String a, String b) {
        if (a != null && !a.trim().isEmpty()) return a;
        if (b != null && !b.trim().isEmpty()) return b;
        return null;
    }
}
