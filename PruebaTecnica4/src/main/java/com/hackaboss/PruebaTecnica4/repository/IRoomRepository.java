package com.hackaboss.PruebaTecnica4.repository;

import com.hackaboss.PruebaTecnica4.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoomRepository extends JpaRepository<Room,Long> {

    @Query("select r from Room r where r.status = true")
    List<Room> getAllRooms();

    @Query("select r from Room r where r.id = ?1 and r.status = true")
    Room findRoomById(Long id);

}
