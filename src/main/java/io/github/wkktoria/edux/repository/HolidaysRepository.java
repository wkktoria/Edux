package io.github.wkktoria.edux.repository;

import io.github.wkktoria.edux.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HolidaysRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HolidaysRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Holiday> findAllHolidays() {
        String sql = "select * from holidays";
        RowMapper<Holiday> rowMapper = new BeanPropertyRowMapper<>(Holiday.class);
        return jdbcTemplate.query(sql, rowMapper);
    }
}
