package com.lucete.template.info.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.lucete.template.info.model.User;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setPhone(rs.getString("phone"));
        user.setTeam(rs.getString("team"));
        user.setPassword(rs.getString("password"));
        user.setStatus(rs.getBoolean("status"));
        user.setSemester(rs.getInt("semester"));
        user.setTeam_code(rs.getInt("team_code"));
        user.setPermission(rs.getInt("permission"));
        user.setCreated(rs.getDate("created"));
        user.setUpdated(rs.getDate("updated"));
        return user;
    }
}
