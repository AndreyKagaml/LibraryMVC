package ua.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.library.DAO.BookDAO;
import ua.library.DAO.PeopleDAO;
import ua.library.models.Book;
import ua.library.models.Person;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PeopleDAO peopleDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PeopleDAO peopleDAO) {
        this.bookDAO = bookDAO;
        this.peopleDAO = peopleDAO;
    }

    @GetMapping()
    public String allBooks(Model model){
        model.addAttribute("books", bookDAO.allBooks());
        return "books/allBooks";
    }

    @GetMapping("{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDAO.bookOne(id));

        Optional<Person> bookOwner = bookDAO.getBookOwner(id);
        if(bookOwner.isPresent()){
            model.addAttribute("owner", bookOwner.get());
        }
        else
        {
            model.addAttribute("people", peopleDAO.allPersons());
        }
        return "books/showOne";
    }

    @GetMapping("/new")
    public String addBook(Model model){
        model.addAttribute("book", new Book());
        return "books/newBook";
    }

    @PostMapping()
    public String saveToDB(@ModelAttribute("book") Book book){
        bookDAO.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDAO.bookOne(id));
        return "books/editBook";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") Book book){
        bookDAO.updateBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson){
        bookDAO.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id){
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }
}
