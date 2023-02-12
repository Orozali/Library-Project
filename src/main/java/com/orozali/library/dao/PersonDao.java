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
public class PersonDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Person> index(){
        return jdbcTemplate.query("Select * from Person",new BeanPropertyRowMapper<>(Person.class));
    }
    public Person show(int id){
        return jdbcTemplate.query("select * from person where person_id=?",new Object[]{id}
                ,new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }
    public Optional<Person> show(String name){
        return jdbcTemplate.query("select * from person where fullname=?",new Object[]{name}
                ,new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    public void update(int id, Person person){
        jdbcTemplate.update("UPDATE Person SET fullname=?,birthdate=? where person_id=?",person.getFullname(),person.getBirthdate(),id);
    }
    public void add(Person person){
        jdbcTemplate.update("INSERT INTO Person(fullname,birthdate) VALUES (?,?)",person.getFullname(),person.getBirthdate());
    }
    public boolean check(Person person){

        Person p = jdbcTemplate.query("Select * from Person where fullname=?",new Object[]{person.getFullname()},new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
        return p != null;
    }
    public void delete(int id){
        jdbcTemplate.update("DELETE From person where person_id=?",id);
    }
    public Optional<Person> getPersonByFullName(String name){
        return jdbcTemplate.query("select * from person where fullname=?",new Object[]{name},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    public List<Books> getBooksByPersonId(int id){
        return jdbcTemplate.query("select * from Book where person_id=?",new Object[]{id},
                new BeanPropertyRowMapper<>(Books.class));
    }
}
