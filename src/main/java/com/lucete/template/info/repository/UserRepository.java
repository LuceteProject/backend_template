package com.lucete.template.info.repository;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.stereotype.Repository;
import com.lucete.template.info.model.User;

@Repository
public class UserRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserRowMapper userRowMapper;

    public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userRowMapper = new UserRowMapper();
    }

    public List<User> findList() {
        // 쿼리파라미터를 넘길 필요가 없기 때문에 instance를 던짐
        // 조회된 데이터를 userRowMapper를 통해 매핑해줌
        String sql = "select * from user limit 1000";


        RowMapper<User> cityMapper = (rs, rowNum) -> {
            User user = new User();
            user.setName(rs.getString("name"));
            user.setPhone(rs.getString("phone"));
            user.setActive(rs.getInt("active"));
            user.setTeam(rs.getString("team"));
            user.setCreated(rs.getDate("created"));
            user.setUpdated(rs.getDate("updated"));
            user.setYear(rs.getInt("year"));
            return user;
        };

        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(), userRowMapper);
    }
}