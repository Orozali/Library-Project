package com.orozali.library.controller;

import com.orozali.library.dao.BookDao;
import com.orozali.library.dao.PersonDao;
import com.orozali.library.models.Books;
import com.orozali.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Book;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookDao bookDao;
    private final PersonDao personDao;
    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books",bookDao.index());
        return "book/list";
    }

    @GetMapping("/{book_id}")
    public String showId(@PathVariable("book_id") int id, Model model,@ModelAttribute("person") Person person){
        model.addAttribute("book",bookDao.show(id));

        Optional<Person> bookOwner = bookDao.getBookOwner(id);
        if(bookOwner.isPresent())
            model.addAttribute("owner",bookOwner.get());
        else
            model.addAttribute("people", personDao.index());
        return "book/show";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "book/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Books book,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "book/new";
        bookDao.save(book);
        return "redirect:/book";
    }
    @GetMapping("/{book_id}/edit")
    public String edit(@PathVariable("book_id") int id,Model model){
        model.addAttribute("book", bookDao.show(id));
        return "book/edit";
    }
    @PatchMapping("/{book_id}")
    public String upd(@ModelAttribute("book") @Valid Books books,@PathVariable("book_id")int id
            ,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "book/edit";
        }
        bookDao.update(books,id);
        return "redirect:/book";
    }

    @DeleteMapping("/{book_id}")
    public String delete(@PathVariable("book_id") int id){
        bookDao.delete(id);
        return "redirect:/book";
    }
    @PatchMapping("{book_id}/release")
    public String release(@PathVariable("book_id") int id){
        bookDao.release(id);
        return "redirect:/book/"+id;
    }
    @PatchMapping("{book_id}/assign")
    public String assign(@PathVariable("book_id") int id, @ModelAttribute("person") Person person){
        bookDao.assign(id, person);
        return "redirect:/book/"+id;
    }

}
