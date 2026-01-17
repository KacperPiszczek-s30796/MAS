package org.example.Entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    @GeneratedValue
    public int ID;
    public String name, password, middleName, surname, nationality;
    public LocalDate dateOfBirth;
    public Person(){
        password = "123";
    }
    public Person(String name,String middleName, String surname, String nationality, LocalDate dateOfBirth){
        this.name = name;
        this.middleName = middleName;
        this.surname = surname;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        password = "123";
    }
    @Override
    public String toString() {
        return "Person{" +
                "id=" + ID +
                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                ", surname='" + surname + '\'' +
                ", nationality='" + nationality + '\'' +
                ", age=" + age() +
                '}';
    }
    int age(){
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

}
