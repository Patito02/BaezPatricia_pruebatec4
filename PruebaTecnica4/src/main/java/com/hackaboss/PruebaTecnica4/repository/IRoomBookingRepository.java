package com.hackaboss.PruebaTecnica4.repository;

import com.hackaboss.PruebaTecnica4.model.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoomBookingRepository extends JpaRepository<RoomBooking,Long> {

    @Query("select rb from RoomBooking rb where rb.status = true")
    List<RoomBooking> getRoomBookings();

    @Query("select rb from RoomBooking rb where rb.id = ?1 and rb.status = true")
    RoomBooking findRoomBookingById(Long id);

}
