package com.lucete.template.info.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.lucete.template.info.model.User;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setName(rs.getString("name"));
        user.setPhone(rs.getString("phone"));
        user.setTeam(rs.getString("team"));
        user.setActive(rs.getInt("active"));
        user.setYear(rs.getInt("year"));
        user.setCreated(rs.getDate("created"));
        user.setUpdated(rs.getDate("updated"));
        return user;
    }
}
