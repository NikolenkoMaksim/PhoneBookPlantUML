package ru.academits.nikolenko.dao;

import ru.academits.nikolenko.model.Contact;

import java.util.List;

public interface ContactDao extends GenericDao<Contact, Long>{
    List<Contact> getAllContacts();

    List<Contact> findContactContainingString (String filter);

    void deleteById (Long id);

    void deleteAnyContact();
}
