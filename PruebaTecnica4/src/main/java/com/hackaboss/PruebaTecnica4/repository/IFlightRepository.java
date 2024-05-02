package com.hackaboss.PruebaTecnica4.repository;

import com.hackaboss.PruebaTecnica4.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFlightRepository extends JpaRepository<Flight,Long> {
    @Query("select f from Flight f where f.status = true")
    List<Flight> getAllFlights();

    @Query("select f from Flight f where f.id = ?1 and f.status = true")
    Flight findFlightById(Long id);


}
