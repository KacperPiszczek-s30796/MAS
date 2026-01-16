package org.example;

import javax.persistence.*;
import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue
    int ID;
    String name;
    int floorNumber;
    @OneToOne
    Doctor HeadOfDepartment;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    List<Doctor> doctors;
    void AssignNewHead(){}
}
