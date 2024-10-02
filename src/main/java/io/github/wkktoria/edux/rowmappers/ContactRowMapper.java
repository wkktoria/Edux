package io.github.wkktoria.edux.rowmappers;

import io.github.wkktoria.edux.model.Contact;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactRowMapper implements RowMapper<Contact> {
    @Override
    public Contact mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        Contact contact = new Contact();
        contact.setContactId(rs.getInt("contact_id"));
        contact.setName(rs.getString("name"));
        contact.setPhoneNumber(rs.getString("phone_number"));
        contact.setEmail(rs.getString("email"));
        contact.setSubject(rs.getString("subject"));
        contact.setMessage(rs.getString("message"));
        contact.setStatus(rs.getString("status"));
        contact.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        contact.setCreatedBy(rs.getString("created_by"));

        if (null != rs.getTimestamp("updated_at")) {
            contact.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        contact.setUpdatedBy(rs.getString("updated_by"));

        return contact;
    }
}
