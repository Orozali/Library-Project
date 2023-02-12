package com.orozali.library.controller;

import com.orozali.library.dao.PersonDao;
import com.orozali.library.models.Person;
import com.orozali.library.utils.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDao personDao;
    private final PersonValidator validator;
    @Autowired
    public PersonController(PersonDao personDao, PersonValidator validator) {
        this.personDao = personDao;

        this.validator = validator;
    }

    @GetMapping()
    public String list(Model model){
        model.addAttribute("people",personDao.index());
        return "person/list";
    }
    @GetMapping("/{person_id}")
    public String showId(@PathVariable("person_id") int id, Model model){
        model.addAttribute("person",personDao.show(id));
        model.addAttribute("books",personDao.getBooksByPersonId(id));
        return "person/show";
    }
    @GetMapping("/new")
    public String form(Model model){
        model.addAttribute("person",new Person());
        return "person/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person
            , BindingResult bindingResult)
    {
        validator.validate(person,bindingResult);
        if(bindingResult.hasErrors()){
            return "person/new";
        }
        personDao.add(person);
        return "redirect:/people";
    }
    @GetMapping("/{person_id}/edit")
    public String edit(Model model, @PathVariable("person_id") int id){
        model.addAttribute("person", personDao.show(id));
        return "person/edit";
    }
    @PatchMapping("/{person_id}")
    public String update(@ModelAttribute("person") @Valid Person person,@PathVariable("person_id") int id
            ,BindingResult bindingResult){
        validator.validate(person,bindingResult);
        if(bindingResult.hasErrors()){
            return "person/edit";
        }
        personDao.update(id,person);
        return "redirect:/people";
    }
    @DeleteMapping("/{person_id}")
    public String delete(@PathVariable("person_id")int id){
        personDao.delete(id);
        return "redirect:/people";
    }


}
