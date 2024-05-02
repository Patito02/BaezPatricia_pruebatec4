package com.hackaboss.PruebaTecnica4.repository;

import com.hackaboss.PruebaTecnica4.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHotelRepository extends JpaRepository<Hotel,String> {
    @Query("select h from Hotel h where h.status = true")
    List<Hotel> getAllHotels();

    @Query("select h from Hotel h where h.hotelCode = ?1 and h.status = true")
    Hotel findHotelById(String hotelCode);

}
