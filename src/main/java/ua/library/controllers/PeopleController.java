package ua.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.library.DAO.PeopleDAO;
import ua.library.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleDAO peopleDAO;

    @Autowired
    public PeopleController(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @GetMapping()
    public String showAllPeople(Model model){
        model.addAttribute("people", peopleDAO.allPersons());
        return "people/allPeople";
    }

    @GetMapping("{id}")
    public String showPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleDAO.personOne(id));
        model.addAttribute("booksPerson", peopleDAO.getAllBooks(id));
        return "people/showOne";
    }

    @GetMapping("/new")
    public String addPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/newPerson";
    }

    @PostMapping()
    public String savePerson(@ModelAttribute("person") Person person){
        peopleDAO.addPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleDAO.personOne(id));
        return "people/editPerson";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        peopleDAO.updatePerson(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        peopleDAO.deletePerson(id);
        return "redirect:/people";
    }
}
