package com.twoimpulse.challenge.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter

@Entity(name = "person")
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue
    @Column(name = "p_id")
    private long personId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    //@JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @Column(name = "registration_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime registrationDate;

    @Transient
    private long accountAgeInMonths;

    public Person(){}

    public Person(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
        this.registrationDate = LocalDateTime.now();
    }

    public Person(String name, String email, String password, LocalDateTime registrationDate){
        this.name = name;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
    }

    public String toString() {
        return "Name: '" + this.name + "', email: '" + this.email + "', password: '" + this.password + "'";
    }

    public long getAccountAgeInMonths(){
        return ChronoUnit.MONTHS.between(registrationDate, LocalDateTime.now());
    }

    public void setAccountAgeInMonths(Long accountAgeInMonths){
        this.accountAgeInMonths = accountAgeInMonths;
    }
}
