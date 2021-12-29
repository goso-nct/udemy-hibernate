package udemy.one2many_uni;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import udemy.one2many_uni.entity.Department;
import udemy.one2many_uni.entity.Employee;

public class Test {

    static SessionFactory factory;
    static Department dep = null;
    static Employee emp = null;

    public static void main(String[] args) {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Department.class)
                .buildSessionFactory();
        try {
//            insert();
//            update();
            delete();
        }
        finally {
            factory.close();
        }
    }

    // --------------------------------------------------------------------------------------------
    static void insert() {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();

            // d1
            dep = new Department("IT", 100, 500);
            System.out.println("dep IT");

            emp = new Employee("Сергей", "Ионов", 350);
            dep.addEmployee(emp);
            System.out.println("IT e1");

            emp = new Employee("Александр", "Смирнов", 300);
            dep.addEmployee(emp);
            System.out.println("IT e2");

            emp = new Employee("Марина", "Петрова", 250);
            dep.addEmployee(emp);
            System.out.println("IT e3");

            session.save(dep);
            System.out.println("session.save(dep)");

            // d2
            dep = new Department("Sale", 200, 400);
            System.out.println("dep Sale");

            emp = new Employee("Юлия", "Авакова", 280);
            dep.addEmployee(emp);
            System.out.println("Sale e1");

            emp = new Employee("Борис", "Леонов", 280);
            dep.addEmployee(emp);
            System.out.println("Sale e2");

            session.save(dep);
            System.out.println("session.save(dep)");

            session.getTransaction().commit();
            System.out.println("session.getTransaction().commit()");
            System.out.println("insert Done!");
            /*
            dep IT
            IT e1
            IT e2
            IT e3
            Hibernate: insert into departments_o2m (max_salary, min_salary, name) values (?, ?, ?)
            Hibernate: insert into employees_o2m (department_id, name, salary, surname) values (?, ?, ?, ?)
            Hibernate: insert into employees_o2m (department_id, name, salary, surname) values (?, ?, ?, ?)
            Hibernate: insert into employees_o2m (department_id, name, salary, surname) values (?, ?, ?, ?)
            session.save(dep)
            dep Sale
            Sale e1
            Sale e2
            Hibernate: insert into departments_o2m (max_salary, min_salary, name) values (?, ?, ?)
            Hibernate: insert into employees_o2m (department_id, name, salary, surname) values (?, ?, ?, ?)
            Hibernate: insert into employees_o2m (department_id, name, salary, surname) values (?, ?, ?, ?)
            session.save(dep)
            Hibernate: update employees_o2m set department_id=? where id=?
            Hibernate: update employees_o2m set department_id=? where id=?
            Hibernate: update employees_o2m set department_id=? where id=?
            Hibernate: update employees_o2m set department_id=? where id=?
            Hibernate: update employees_o2m set department_id=? where id=?
            session.getTransaction().commit()
            insert Done!
            */

            session = factory.getCurrentSession();
            session.beginTransaction();

            System.out.println(session.get(Department.class, 1));
            System.out.println(session.get(Department.class, 2));

            session.getTransaction().commit();
            System.out.println("insert check Done!");
            /*
            Hibernate: select department0_.id as id1_0_0_, department0_.max_salary as max_sala2_0_0_, department0_.min_salary as min_sala3_0_0_, department0_.name as name4_0_0_ from departments_o2m department0_ where department0_.id=?
            Hibernate: select employees0_.department_id as departme2_1_0_, employees0_.id as id1_1_0_, employees0_.id as id1_1_1_, employees0_.department_id as departme2_1_1_, employees0_.name as name3_1_1_, employees0_.salary as salary4_1_1_, employees0_.surname as surname5_1_1_ from employees_o2m employees0_ where employees0_.department_id=?
            Department #1 {name='IT', maxSalary=100, minSalary=500, employees=[Employee #1 {name='Сергей', surname='Ионов', salary=350, departmentId=1}, Employee #2 {name='Александр', surname='Смирнов', salary=300, departmentId=1}, Employee #3 {name='Марина', surname='Петрова', salary=250, departmentId=1}]}
            Hibernate: select department0_.id as id1_0_0_, department0_.max_salary as max_sala2_0_0_, department0_.min_salary as min_sala3_0_0_, department0_.name as name4_0_0_ from departments_o2m department0_ where department0_.id=?
            Hibernate: select employees0_.department_id as departme2_1_0_, employees0_.id as id1_1_0_, employees0_.id as id1_1_1_, employees0_.department_id as departme2_1_1_, employees0_.name as name3_1_1_, employees0_.salary as salary4_1_1_, employees0_.surname as surname5_1_1_ from employees_o2m employees0_ where employees0_.department_id=?
            Department #2 {name='Sale', maxSalary=200, minSalary=400, employees=[Employee #4 {name='Юлия', surname='Авакова', salary=280, departmentId=2}, Employee #5 {name='Борис', surname='Леонов', salary=280, departmentId=2}]}
            insert check Done!

            SELECT * FROM spring.departments_o2m;
            1	IT	100	500
            2	Sale	200	400

            SELECT * FROM spring.employees_o2m;
            1	Сергей      Ионов	350	1
            2	Александр	Смирнов	300	1
            3	Марина	    Петрова	250	1
            4	Юлия	    Авакова	280	2
            5	Борис	    Леонов	280	2
            */
        }
        finally {
            session.close();
        }
    }

    // --------------------------------------------------------------------------------------------
    static void update() {

        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();

            // переводим Марину Петрову в Sale
            emp = session.get(Employee.class, 3);
            dep = session.get(Department.class, 2);
            dep.addEmployee(emp);

            session.getTransaction().commit();
            System.out.println("update Done!");
            /*
            Hibernate: select employee0_.id as id1_1_0_, employee0_.department_id as departme2_1_0_, employee0_.name as name3_1_0_, employee0_.salary as salary4_1_0_, employee0_.surname as surname5_1_0_ from employees_o2m employee0_ where employee0_.id=?
            Hibernate: select department0_.id as id1_0_0_, department0_.max_salary as max_sala2_0_0_, department0_.min_salary as min_sala3_0_0_, department0_.name as name4_0_0_ from departments_o2m department0_ where department0_.id=?
            Hibernate: select employees0_.department_id as departme2_1_0_, employees0_.id as id1_1_0_, employees0_.id as id1_1_1_, employees0_.department_id as departme2_1_1_, employees0_.name as name3_1_1_, employees0_.salary as salary4_1_1_, employees0_.surname as surname5_1_1_ from employees_o2m employees0_ where employees0_.department_id=?
            Hibernate: update employees_o2m set department_id=? where id=?
            update Done!
            */

            session = factory.getCurrentSession();
            session.beginTransaction();

            emp = session.get(Employee.class, 3);
            System.out.println(emp);
            dep = session.get(Department.class, 2);
            System.out.println(dep);
            dep = session.get(Department.class, 1);
            System.out.println(dep);

            session.getTransaction().commit();
            System.out.println("update check Done!");
            /*
            Hibernate: select employee0_.id as id1_1_0_, employee0_.department_id as departme2_1_0_, employee0_.name as name3_1_0_, employee0_.salary as salary4_1_0_, employee0_.surname as surname5_1_0_ from employees_o2m employee0_ where employee0_.id=?
            Employee #3 {name='Марина', surname='Петрова', salary=250, departmentId=2}
            Hibernate: select department0_.id as id1_0_0_, department0_.max_salary as max_sala2_0_0_, department0_.min_salary as min_sala3_0_0_, department0_.name as name4_0_0_ from departments_o2m department0_ where department0_.id=?
            Hibernate: select employees0_.department_id as departme2_1_0_, employees0_.id as id1_1_0_, employees0_.id as id1_1_1_, employees0_.department_id as departme2_1_1_, employees0_.name as name3_1_1_, employees0_.salary as salary4_1_1_, employees0_.surname as surname5_1_1_ from employees_o2m employees0_ where employees0_.department_id=?
            Department #2 {name='Sale', maxSalary=200, minSalary=400, employees=[Employee #3 {name='Марина', surname='Петрова', salary=250, departmentId=2}]}
            Hibernate: select department0_.id as id1_0_0_, department0_.max_salary as max_sala2_0_0_, department0_.min_salary as min_sala3_0_0_, department0_.name as name4_0_0_ from departments_o2m department0_ where department0_.id=?
            Hibernate: select employees0_.department_id as departme2_1_0_, employees0_.id as id1_1_0_, employees0_.id as id1_1_1_, employees0_.department_id as departme2_1_1_, employees0_.name as name3_1_1_, employees0_.salary as salary4_1_1_, employees0_.surname as surname5_1_1_ from employees_o2m employees0_ where employees0_.department_id=?
            Department #1 {name='IT', maxSalary=100, minSalary=500, employees=[Employee #1 {name='Сергей', surname='Ионов', salary=350, departmentId=1}, Employee #2 {name='Александр', surname='Смирнов', salary=300, departmentId=1}]}
            update check Done!

            1	Сергей	    Ионов	350	1
            2	Александр	Смирнов	300	1
            3	Марина	    Петрова	250	2  <--
            4	Юлия	    Авакова	280	2
            5	Борис	    Леонов	280	2
            */
        }
        finally {
            session.close();
        }

    }

    // --------------------------------------------------------------------------------------------
    static void delete() {

        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();

            emp = session.get(Employee.class, 4);
            session.delete(emp);
            System.out.println("delete emp 4");

            session.getTransaction().commit();
            System.out.println("commit delete emp");
            /*
            Hibernate: select employee0_.id as id1_1_0_, employee0_.department_id as departme2_1_0_, employee0_.name as name3_1_0_, employee0_.salary as salary4_1_0_, employee0_.surname as surname5_1_0_ from employees_o2m employee0_ where employee0_.id=?
            delete emp 4
            Hibernate: delete from employees_o2m where id=?
            commit delete emp

            1	Сергей	    Ионов	350	1
            2	Александр	Смирнов	300	1
            3	Марина	    Петрова	250	2
            5	Борис	    Леонов	280	2
            */

            session = factory.getCurrentSession();
            session.beginTransaction();

            dep = session.get(Department.class, 2);
            session.delete(dep);
            System.out.println("delete dep 2");

            session.getTransaction().commit();
            System.out.println("commit delete dep");
            /*
            Hibernate: select department0_.id as id1_0_0_, department0_.max_salary as max_sala2_0_0_, department0_.min_salary as min_sala3_0_0_, department0_.name as name4_0_0_ from departments_o2m department0_ where department0_.id=?
            Hibernate: select employees0_.department_id as departme2_1_0_, employees0_.id as id1_1_0_, employees0_.id as id1_1_1_, employees0_.department_id as departme2_1_1_, employees0_.name as name3_1_1_, employees0_.salary as salary4_1_1_, employees0_.surname as surname5_1_1_ from employees_o2m employees0_ where employees0_.department_id=?
            delete dep 2
            Hibernate: update employees_o2m set department_id=null where department_id=?
            Hibernate: delete from employees_o2m where id=?
            Hibernate: delete from employees_o2m where id=?
            Hibernate: delete from departments_o2m where id=?
            commit delete dep

            SELECT * FROM spring.departments_o2m;
            1	IT	100	500

            SELECT * FROM spring.employees_o2m;
            1	Сергей	    Ионов	350	1
            2	Александр	Смирнов	300	1
            */
        }
        finally {
            session.close();
        }
    }

}
