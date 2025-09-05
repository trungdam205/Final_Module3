package com.tcomplex.dao;

import com.tcomplex.model.Space;
import com.tcomplex.util.DB;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpaceDao {

    private Space map(ResultSet rs) throws SQLException {
        Space s = new Space();
        s.setCode(rs.getString("code"));
        s.setStatus(rs.getString("status"));
        s.setArea(rs.getBigDecimal("area_m2"));
        s.setFloor(rs.getInt("floor_no"));
        s.setType(rs.getString("type"));
        s.setPrice(rs.getBigDecimal("price_vnd"));
        s.setStartDate(rs.getDate("start_date").toLocalDate());
        s.setEndDate(rs.getDate("end_date").toLocalDate());
        return s;
    }

    public boolean exists(String code) throws SQLException {
        String sql = "SELECT 1 FROM space WHERE code = ?";
        Connection c = DB.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            try {
                ps.setString(1, code);
                ResultSet rs = ps.executeQuery();
                try {
                    return rs.next();
                } finally { rs.close(); }
            } finally { ps.close(); }
        } finally { c.close(); }
    }

    public int insert(Space s) throws SQLException {
        String sql =
                "INSERT INTO space(code,status,area_m2,floor_no,type,price_vnd,start_date,end_date) " +
                        "VALUES (?,?,?,?,?,?,?,?)";
        Connection c = DB.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            try {
                ps.setString(1, s.getCode());
                ps.setString(2, s.getStatus());
                ps.setBigDecimal(3, s.getArea());
                ps.setInt(4, s.getFloor());
                ps.setString(5, s.getType());
                ps.setBigDecimal(6, s.getPrice());
                ps.setDate(7, Date.valueOf(s.getStartDate()));
                ps.setDate(8, Date.valueOf(s.getEndDate()));
                return ps.executeUpdate();
            } finally { ps.close(); }
        } finally { c.close(); }
    }

    public int delete(String code) throws SQLException {
        String sql = "DELETE FROM space WHERE code = ?";
        Connection c = DB.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            try {
                ps.setString(1, code);
                return ps.executeUpdate();
            } finally { ps.close(); }
        } finally { c.close(); }
    }

    public List<Space> search(String type, Integer floor,
                              BigDecimal minPrice, BigDecimal maxPrice,
                              int limit, int offset) throws SQLException {
        StringBuilder sb = new StringBuilder("SELECT * FROM space WHERE 1=1");
        List<Object> params = new ArrayList<Object>();

        if (type != null && !type.trim().isEmpty()) { sb.append(" AND type = ?"); params.add(type); }
        if (floor != null) { sb.append(" AND floor_no = ?"); params.add(floor); }
        if (minPrice != null) { sb.append(" AND price_vnd >= ?"); params.add(minPrice); }
        if (maxPrice != null) { sb.append(" AND price_vnd <= ?"); params.add(maxPrice); }

        sb.append(" ORDER BY area_m2 ASC LIMIT ? OFFSET ?");
        params.add(Integer.valueOf(limit));
        params.add(Integer.valueOf(offset));

        Connection c = DB.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement(sb.toString());
            try {
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }
                ResultSet rs = ps.executeQuery();
                try {
                    List<Space> out = new ArrayList<Space>();
                    while (rs.next()) out.add(map(rs));
                    return out;
                } finally { rs.close(); }
            } finally { ps.close(); }
        } finally { c.close(); }
    }

    public List<Space> listAllSortedByArea() throws SQLException {
        String sql = "SELECT * FROM space ORDER BY area_m2 ASC";
        Connection c = DB.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    List<Space> out = new ArrayList<Space>();
                    while (rs.next()) out.add(map(rs));
                    return out;
                } finally { rs.close(); }
            } finally { ps.close(); }
        } finally { c.close(); }
    }

}
