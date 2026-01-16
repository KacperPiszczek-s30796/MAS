package org.example;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    @GeneratedValue
    int ID;
    String name, middleName, surname, nationality;
    LocalDate dateOfBirth;
    public Person(){

    }
    public Person(String name,String middleName, String surname, String nationality, LocalDate dateOfBirth){
        this.name = name;
        this.middleName = middleName;
        this.surname = surname;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
    }
    @Override
    public String toString() {
        return "Person{" +
                "id=" + ID +
                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                ", surname='" + surname + '\'' +
                ", nationality='" + nationality + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
    void age(){}

}
