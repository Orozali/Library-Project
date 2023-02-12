package com.orozali.library.dao;

import com.orozali.library.models.Books;
import com.orozali.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Books> index(){
        return jdbcTemplate.query("Select * from Book",new BeanPropertyRowMapper<>(Books.class));
    }
    public Books show(int id){
        return jdbcTemplate.query("select * from book where book_id=?",new Object[]{id}
                ,new BeanPropertyRowMapper<>(Books.class)).stream().findAny().orElse(null);
    }
    public void save(Books books){
        jdbcTemplate.update("INSERT INTO Book(name,author,year) VALUES (?,?,?)",books.getName(),books.getAuthor(),books.getYear());
    }
    public void update(Books books, int id){
        jdbcTemplate.update("UPDATE Book Set name=?,author=?,year=? where book_id=?",books.getName(),books.getAuthor(),books.getYear(),id);
    }
    public void delete(int id){
        jdbcTemplate.update("delete from book where book_id=?",id);
    }
    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("select Person.* from Book Join Person on Book.person_id = Person.person_id where Book.book_id=?"
        ,new Object[]{id},new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    public void release(int id){
        jdbcTemplate.update("update Book set person_id=null where book_id=?",id);
    }
    public void assign(int id, Person person){
        jdbcTemplate.update("UPDATE Book SET person_id = ? where book_id=?",person.getPerson_id(),id);
    }
}
