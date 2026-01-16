package org.example.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue
    int ID;
    public String name;
    public int floorNumber;
    @OneToOne
    public Doctor HeadOfDepartment;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    List<Doctor> doctors;
    void AssignNewHead(){}
}
