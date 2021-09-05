package ru.academits.nikolenko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.academits.nikolenko.converter.ContactDtoToContactConverter;
import ru.academits.nikolenko.converter.ContactToContactDtoConverter;
import ru.academits.nikolenko.dto.ContactDto;
import ru.academits.nikolenko.model.ContactValidation;
import ru.academits.nikolenko.model.DeleteResults;
import ru.academits.nikolenko.model.Filter;
import ru.academits.nikolenko.service.ContactService;
import ru.academits.nikolenko.service.PhoneBookService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/phoneBook/rpc/api/v1")
public class PhoneBookController implements PhoneBookInterface {
    private static final Logger logger = LoggerFactory.getLogger(PhoneBookController.class);

    private final ContactDtoToContactConverter contactDtoToContactConverter;
    private final ContactToContactDtoConverter contactToContactDtoConverter;
    private final PhoneBookService contactService;

    public PhoneBookController(ContactDtoToContactConverter contactDtoToContactConverter, ContactToContactDtoConverter contactToContactDtoConverter, ContactService contactService) {
        this.contactDtoToContactConverter = contactDtoToContactConverter;
        this.contactToContactDtoConverter = contactToContactDtoConverter;
        this.contactService = contactService;
    }

    @RequestMapping(value = "getAllContacts", method = RequestMethod.GET)
    @ResponseBody
    public List<ContactDto> getAllContacts() {
        logger.info("Called method getAllContacts in PhoneBookController");
        return contactToContactDtoConverter.convert(contactService.getAllContacts());
    }

    @RequestMapping(value = "addContact", method = RequestMethod.POST)
    @ResponseBody
    public ContactValidation addContact(@RequestBody ContactDto contactDto) {
        logger.info("Called method addContact in PhoneBookController. contactDto = " + contactDto.toString());
        return contactService.addContact(contactDtoToContactConverter.convert(contactDto));
    }

    @RequestMapping(value = "deleteContacts", method = RequestMethod.POST)
    @ResponseBody
    public DeleteResults deleteContacts(@RequestBody Long[] contactsIds) {
        logger.info("Called method deleteContacts in PhoneBookController. contactsIds = " + Arrays.toString(contactsIds));
        return contactService.deleteContacts(contactsIds);
    }

    @RequestMapping(value = "getFilteredContacts", method = RequestMethod.POST)
    @ResponseBody
    public List<ContactDto> getFilteredContacts(@RequestBody Filter filter) {
        logger.info("Called method getFilteredContacts in PhoneBookController. filter = " + filter.getFilter());
        return contactToContactDtoConverter.convert(contactService.getFilteredContacts(filter.getFilter()));
    }
}


