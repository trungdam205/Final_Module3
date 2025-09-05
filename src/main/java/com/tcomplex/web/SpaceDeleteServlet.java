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
        String code = req.getParameter("code");
        if (code != null && !code.trim().isEmpty()) {
            try { dao.delete(code); } catch (Exception ignore) {}
        }
        resp.sendRedirect(req.getContextPath() + "/spaces");
    }
}
