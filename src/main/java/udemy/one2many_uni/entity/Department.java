package udemy.one2many_uni.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments_o2m")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 15)
    private String name;

    @Column(name = "max_salary")
    private Integer maxSalary;

    @Column(name = "min_salary")
    private Integer minSalary;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private List<Employee> employees;

    public Department() {}

    public Department(String name, Integer maxSalary, Integer minSalary) {
        this.name = name;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
    }

    public void addEmployee(Employee emp) {
        if (employees == null) employees = new ArrayList<>();
        employees.add(emp);
    }

    @Override
    public String toString() {
        return "Department #" + id + " {" +
                "name='" + name + "'" +
                ", maxSalary=" + maxSalary +
                ", minSalary=" + minSalary +
                ", employees=" + employees +
                "}";
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMinSalary() {
        return minSalary;
    }
    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }
    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}