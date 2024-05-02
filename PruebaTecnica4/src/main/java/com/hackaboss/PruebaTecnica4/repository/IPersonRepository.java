package com.hackaboss.PruebaTecnica4.repository;

import com.hackaboss.PruebaTecnica4.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPersonRepository extends JpaRepository<Person,String> {

    @Query("select p from Person p where p.status = true")
    List<Person> getAllPersons();

    @Query("select p from Person p where p.dni = ?1 and p.status = true")
    Person findPersonById(String dni);

}
