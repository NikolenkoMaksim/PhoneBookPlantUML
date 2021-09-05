package ru.academits.nikolenko.controller;

import org.springframework.web.bind.annotation.RequestBody;
import ru.academits.nikolenko.dto.ContactDto;
import ru.academits.nikolenko.model.ContactValidation;
import ru.academits.nikolenko.model.DeleteResults;
import ru.academits.nikolenko.model.Filter;

import java.util.List;

public interface PhoneBookInterface {
    List<ContactDto> getAllContacts();

    ContactValidation addContact(@RequestBody ContactDto contactDto);

    DeleteResults deleteContacts(@RequestBody Long[] contactsIds);

    List<ContactDto> getFilteredContacts(@RequestBody Filter filter);
}
