package de.philipilihp.flyway.app;

import de.philipilihp.flyway.entities.Book;
import de.philipilihp.flyway.entities.BookRepository;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

/**
 * Startup class that writes all available books into the database.
 */
@Startup
@Singleton
public class Libary {

    @Inject
    private BookRepository bookRepository;

    @PostConstruct
    public void setup() {
        Set<Book> books = new HashSet<Book>();
        books.add(new Book("978-3442715732", "Juli Zeh", "Unterleuten", "btb Verlag"));
        books.add(new Book("978-3462050660", "Benjamin von Stuckrad-Barre", "Panikherz", "KiWi-Taschenbuch"));

        books.stream()
                .filter(book -> bookRepository.findById(book.getIsbn()) == null)
                .forEach(book -> bookRepository.add(book));
    }

}
