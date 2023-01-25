package ua.library.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.library.models.Book;
import ua.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> allBooks(){
        return jdbcTemplate.query("SELECT * FROM Book", new BookMapper());
    }

    public Book bookOne(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[]{id},
                new BookMapper()).stream().findAny().orElse(null);
    }

    public void addBook(Book book){
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES (?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public void updateBook(int id, Book book){
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE book_id=?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void deleteBook(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("SELECT Person.* FROM book JOIN person ON  person.person_id = book.person_id WHERE book_id=?", new Object[]{id},
                new PersonMapper()).stream().findAny();
    }

    public void release(int id){
        jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE book_id=?", id);
    }

    public void assign(int id, Person selectedPerson){
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?",selectedPerson.getId(), id);
    }

}
