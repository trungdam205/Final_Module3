package com.tcomplex.web;

import com.tcomplex.service.SpaceService;
import com.tcomplex.service.SpaceService.ValidationResult;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/spaces/create"})
public class SpaceCreateServlet extends HttpServlet {

    private final SpaceService service = new SpaceService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/space-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Map<String, String> p = new HashMap<String, String>();
        String[] keys = new String[]{"code","status","area","floor","type","price","startDate","endDate"};
        for (int i = 0; i < keys.length; i++) {
            String k = keys[i];
            p.put(k, req.getParameter(k));
        }

        ValidationResult v = service.validateAndBuild(p);
        try {
            if (v.ok()) {
                if (service.codeExists(v.space.getCode())) {
                    Map<String, String> errs = new HashMap<String, String>();
                    errs.put("code", "Mã mặt bằng vừa thêm đã tồn tại");
                    req.setAttribute("errors", errs);
                    req.setAttribute("old", p);
                    req.getRequestDispatcher("/WEB-INF/views/space-form.jsp").forward(req, resp);
                    return;
                }
                service.create(v.space);
                resp.sendRedirect(req.getContextPath() + "/spaces");
            } else {
                req.setAttribute("errors", v.errors);
                req.setAttribute("old", p);
                req.getRequestDispatcher("/WEB-INF/views/space-form.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
