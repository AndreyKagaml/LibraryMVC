package ua.library.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.library.models.Book;
import ua.library.models.Person;

import java.util.Collection;
import java.util.List;

@Component
public class PeopleDAO {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> allPersons(){
        return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    public Person personOne(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id = ?", new Object[]{id},
                new PersonMapper()).stream().findAny().orElse(null);
    }

    public void addPerson(Person person){
        jdbcTemplate.update("INSERT INTO Person(name, year) VALUES (?, ?)", person.getName(), person.getYear());
    }

    public void updatePerson(int id, Person person){
        jdbcTemplate.update("UPDATE Person SET name=?, year=? WHERE person_id=?",
                person.getName(), person.getYear(), id);
    }

    public void deletePerson(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }

    public List<Book> getAllBooks(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{id},
                new BookMapper());
    }
}
