package udemy.one2one_uni.entity;

import javax.persistence.*;

@Entity
@Table(name = "employees_o2o_uni")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 15)
    private String name;

    @Column(name = "surname", nullable = false, length = 25)
    private String surname;

    @Column(name = "department", length = 20)
    private String department;

    @Column(name = "salary")
    private Integer salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id") // FK на Detail.id
    private Detail empDetail;

    public Employee() {}

    public Employee(String name, String surname, String department, Integer salary) {
        this.name = name;
        this.surname = surname;
        this.department = department;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee #" + id + " {" +
                " name='" + name + "'" +
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }
    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Detail getEmpDetail() {
        return empDetail;
    }
    public void setEmpDetail(Detail detail) {
        this.empDetail = detail;
    }
}