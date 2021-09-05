package ru.academits.nikolenko.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.academits.nikolenko.model.Contact;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ContactDaoImpl extends GenericDaoImpl<Contact, Long> implements ContactDao {
    private static final Logger logger = LoggerFactory.getLogger(ContactDao.class);

    public ContactDaoImpl() {
        super(Contact.class);
    }

    @Override
    public List<Contact> getAllContacts() {
        logger.info("Called method getAllContacts in ContactDaoImpl");
        TypedQuery<Contact> q = entityManager.createQuery("SELECT c FROM Contact c WHERE c.isDeleted = false", clazz);

        return q.getResultList();
    }

    @Override
    public List<Contact> findContactContainingString(String filter) {
        TypedQuery<Contact> q = entityManager.createQuery(
                "SELECT c FROM Contact c " +
                "WHERE c.isDeleted = false" +
                        " AND (c.firstName LIKE CONCAT('%',:filter,'%') " +
                        "OR c.lastName LIKE CONCAT('%',:filter,'%') " +
                        "OR c.phone LIKE CONCAT('%',:filter,'%'))", clazz);
        q.setParameter("filter", filter);

        List<Contact> result = q.getResultList();

        logger.info("Find filtered contacts count = " + result.size());

        return result;
    }

    @Override
    public void deleteById (Long id) {
        Contact contact = getById(id);
        contact.setDeleted(true);
        update(contact);

        logger.info("Contact with id = " + id + " was mark as deleted");
    }

    public void deleteAnyContact() {
        List<Contact> contacts = getAllContacts();

        if(contacts.size() > 0) {
            int randomContactIndex = (int) (Math.random() * contacts.size());
            Long contactId = contacts.get(randomContactIndex).getId();

            logger.info("Contact with id = " + contactId + " was chosen to delete");

            deleteById(contactId);
        }
    }
}
