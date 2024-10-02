package io.github.wkktoria.edux.repository;

import io.github.wkktoria.edux.model.Contact;
import io.github.wkktoria.edux.rowmappers.ContactRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ContactRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveContactMessage(final Contact contact) {
        String sql = "insert into contact_message(name, phone_number, email, subject, message, status, created_at, created_by)"
                + "values (?, ?, ?, ?, ?, ?, ?, ?);";

        return jdbcTemplate.update(sql, contact.getName(), contact.getPhoneNumber(), contact.getEmail(),
                contact.getSubject(), contact.getMessage(),
                contact.getStatus(), contact.getCreatedAt(), contact.getCreatedBy());
    }

    public List<Contact> findMessagesWithStatus(final String status) {
        String sql = "select * from contact_message where status = ?";
        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement ps) throws SQLException {
                ps.setString(1, status);
            }
        }, new ContactRowMapper());
    }
}
