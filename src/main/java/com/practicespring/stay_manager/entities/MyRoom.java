package com.practicespring.stay_manager.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class MyRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String roomNumber;
    private String buildingName;
    private String floor;
    private String bedNumber;
    private String roomType;
    private String status;
    private String date;
    @OneToOne
    @JoinColumn(name = "userId")
    private User user;  
}
