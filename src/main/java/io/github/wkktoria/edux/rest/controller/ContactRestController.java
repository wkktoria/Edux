package io.github.wkktoria.edux.rest.controller;

import io.github.wkktoria.edux.constants.EduxConstants;
import io.github.wkktoria.edux.model.Contact;
import io.github.wkktoria.edux.rest.model.Response;
import io.github.wkktoria.edux.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/api/contact", produces = {
        MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE
})
@CrossOrigin(origins = "*")
class ContactRestController {
    private final ContactService contactService;

    @Autowired
    ContactRestController(final ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/getMessagesByStatus")
    List<Contact> getMessagesByStatus(@RequestParam(value = "status") final String status) {
        return contactService.findMessagesWithStatus(status);
    }

    @GetMapping("/getAllMessagesByStatus")
    List<Contact> getAllMessagesByStatus(@RequestBody final Contact contact) {
        if (contact != null && contact.getStatus() != null) {
            return contactService.findMessagesWithStatus(contact.getStatus());
        } else {
            return List.of();
        }
    }

    @PostMapping("/saveMessage")
    ResponseEntity<Response> saveMessage(@RequestHeader("invocationFrom") String invocationFrom,
                                         @Valid @RequestBody final Contact contact) {
        log.info("Header 'invocationFrom' = '{}'", invocationFrom);
        contactService.saveMessageDetails(contact);
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMessage("Message saved successfully.");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMessageSaved", "true")
                .body(response);
    }

    @DeleteMapping("/deleteMessage")
    ResponseEntity<Response> deleteMessage(RequestEntity<Contact> requestEntity) {
        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key, value) -> {
            log.info("Header '{}' = '{}'",
                    key, String.join(" | ", value));
        });
        Contact contact = requestEntity.getBody();
        assert contact != null;
        contactService.deleteMessageDetails(contact.getContactId());
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMessage("Message deleted successfully.");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/closeMessage")
    ResponseEntity<Response> closeMessage(@RequestBody final Contact contactRequest) {
        Response response = new Response();
        Optional<Contact> contact = contactService.findMessageById(contactRequest.getContactId());

        if (contact.isPresent()) {
            contact.get().setStatus(EduxConstants.CLOSED);
            contactService.saveMessageDetails(contact.get());
        } else {
            response.setStatusCode("400");
            response.setStatusMessage("Invalid contact's ID received.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

        response.setStatusCode("200");
        response.setStatusMessage("Message closed successfully.");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
