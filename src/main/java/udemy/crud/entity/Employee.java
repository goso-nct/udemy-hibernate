package udemy.crud.entity;

import javax.persistence.*;

@Entity
@Table(name = "employees_crud")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 15)
    private String firstName;

    @Column(name = "surname", length = 25)
    private String surname;

    @Column(name = "department", length = 20)
    private String department;

    @Column(name = "salary")
    private Integer salary;

    public Employee() {}

    public Employee(String name, String surname, String department, Integer salary) {
        this.firstName = name;
        this.surname = surname;
        this.department = department;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee #" + id + " {" +
                " name='" + firstName + "'" +
                ", surname='" + surname + "'" +
                ", department='" + department + "'" +
                ", salary=" + salary +
                " }";
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getSalary() {
        return salary;
    }
    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}