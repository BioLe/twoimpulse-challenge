package com.twoimpulse.challenge.library;

import com.twoimpulse.challenge.librarycard.LibraryCard;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "lib")
@Table(name = "lib")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "l_id")
    private long L_ID;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    public Library(){}

    public Library(long libraryId, String name, String address){
        this.L_ID = libraryId;
        this.name = name;
        this.address = address;
    }

    public Library(String name, String address){
        this.name = name;
        this.address = address;
    }

}
