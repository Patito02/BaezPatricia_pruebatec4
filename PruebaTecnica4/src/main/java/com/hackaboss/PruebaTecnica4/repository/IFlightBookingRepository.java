package com.hackaboss.PruebaTecnica4.repository;

import com.hackaboss.PruebaTecnica4.model.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFlightBookingRepository extends JpaRepository<FlightBooking, Long> {
    @Query("select fb from FlightBooking fb where fb.status = true")
    List<FlightBooking> getFlightBookings();
    @Query("select fb from FlightBooking fb where fb.id = ?1 and fb.status = true")
    FlightBooking findFlightBookingById(Long id);
}
