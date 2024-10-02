package io.github.wkktoria.edux.repository;

import io.github.wkktoria.edux.model.Contact;
import io.github.wkktoria.edux.rowmappers.ContactRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        return jdbcTemplate.query(sql, ps -> ps.setString(1, status), new ContactRowMapper());
    }

    public int updateMessageStatus(final int contactId, final String status, String updatedBy) {
        String sql = "update contact_message set status = ?, updated_by = ?, updated_at = ? where contact_id = ?";
        return jdbcTemplate.update(sql, ps -> {
            ps.setString(1, status);
            ps.setString(2, updatedBy);
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(4, contactId);
        });
    }
}
