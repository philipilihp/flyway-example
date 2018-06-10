package de.philipilihp.flyway.entities;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class BookRepository {

    @PersistenceContext(unitName = "BOOK_PU")
    private EntityManager entityManager;

    /**
     * @param book Adds a book to the database.
     */
    public void add(Book book) {
        entityManager.persist(book);
    }

    /**
     * @return Returns the book with the given isbn from the database.
     */
    public Book findById(String isbn) {
        return entityManager.find(Book.class, isbn);
    }

}
