package com.hackaboss.PruebaTecnica4.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hotel {

    @Id
    private String hotelCode;
    private String name;
    private String place;
    private boolean status;

    @OneToMany(mappedBy = "hotel")
    @JsonManagedReference
    private List<Room> listRooms = new ArrayList<>();




}
