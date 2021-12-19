package udemy.one2one_uni;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import udemy.one2one_uni.entity.Detail;
import udemy.one2one_uni.entity.Employee;

public class Test {

    public static void main(String[] args) {
        Session session = null;
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Detail.class)
                .buildSessionFactory();
        try {
            Employee employee;
            Detail detail;

            // insert:
            session = factory.getCurrentSession();
            session.beginTransaction();

            employee = new Employee("Юрий", "Гаврилов", "Midfielder", 450);
            detail = new Detail("Москва", "333-1212", "gavrila@gmail.com");
            employee.setEmpDetail(detail);
            session.save(employee);

            employee = new Employee("Сергей", "Родионов", "IT", 380);
            detail = new Detail("Москва", "433-7311", "pod@mail.ru");
            employee.setEmpDetail(detail);
            session.save(employee);

            session.getTransaction().commit();
            System.out.println("insert Done!");
            /*
            Hibernate: insert into details_o2o_uni (city, email, phone_number) values (?, ?, ?)
            Hibernate: insert into employees_o2o_uni (department, details_id, name, salary, surname) values (?, ?, ?, ?, ?)
            Hibernate: insert into details_o2o_uni (city, email, phone_number) values (?, ?, ?)
            Hibernate: insert into employees_o2o_uni (department, details_id, name, salary, surname) values (?, ?, ?, ?, ?)
            insert Done

            SELECT * FROM spring.employees_o2o_uni;
            1	Юрий	Гаврилов	Midfielder	450	1
            2	Сергей	Родионов	IT	        380	2

            SELECT * FROM spring.details_o2o_uni;
            1	Москва	333-1212	gavrila@gmail.com
            2	Москва	433-7311	pod@mail.ru
            */

            // update:
            session = factory.getCurrentSession();
            session.beginTransaction();

            session.get(Employee.class,1).getEmpDetail().setEmail("mumu@rambler.ru");
            session.get(Employee.class,2).setSalary(1100);
            session.get(Employee.class,2).getEmpDetail().setPhoneNumber("910-876-8722");

            session.getTransaction().commit();
            System.out.println("update Done!");
            /*
            Hibernate: select employee0_.id as id1_1_0_, employee0_.department as departme2_1_0_, employee0_.details_id as details_6_1_0_, employee0_.name as name3_1_0_, employee0_.salary as salary4_1_0_, employee0_.surname as surname5_1_0_, detail1_.id as id1_0_1_, detail1_.city as city2_0_1_, detail1_.email as email3_0_1_, detail1_.phone_number as phone_nu4_0_1_ from employees_o2o_uni employee0_ left outer join details_o2o_uni detail1_ on employee0_.details_id=detail1_.id where employee0_.id=?
            Hibernate: select employee0_.id as id1_1_0_, employee0_.department as departme2_1_0_, employee0_.details_id as details_6_1_0_, employee0_.name as name3_1_0_, employee0_.salary as salary4_1_0_, employee0_.surname as surname5_1_0_, detail1_.id as id1_0_1_, detail1_.city as city2_0_1_, detail1_.email as email3_0_1_, detail1_.phone_number as phone_nu4_0_1_ from employees_o2o_uni employee0_ left outer join details_o2o_uni detail1_ on employee0_.details_id=detail1_.id where employee0_.id=?
            Hibernate: update details_o2o_uni set city=?, email=?, phone_number=? where id=?
            Hibernate: update employees_o2o_uni set department=?, details_id=?, name=?, salary=?, surname=? where id=?
            Hibernate: update details_o2o_uni set city=?, email=?, phone_number=? where id=?
            update Done

            SELECT * FROM spring.employees_o2o_uni;
            1	Юрий	Гаврилов	Midfielder	450	    1
            2	Сергей	Родионов	IT	        1100	2   <-- 380 < 1100

            SELECT * FROM spring.details_o2o_uni;
            1	Москва	333-1212	    mumu@rambler.ru     <-- gavrila@gmail.com < mumu@rambler.ru
            2	Москва	910-876-8722	pod@mail.ru         <-- 433-7311 < 910-876-8722
            */

            // delete:
            session = factory.getCurrentSession();
            session.beginTransaction();

            employee = session.get(Employee.class,1);
            System.out.println(employee);
            session.delete(employee);

            employee = session.get(Employee.class,2);
            detail = employee.getEmpDetail();
            employee.setEmpDetail(null);        // убираем связь
            session.delete(detail);

            session.getTransaction().commit();
            System.out.println("delete Done");
            /*
            Hibernate: select employee0_.id as id1_1_0_, employee0_.department as departme2_1_0_, employee0_.details_id as details_6_1_0_, employee0_.name as name3_1_0_, employee0_.salary as salary4_1_0_, employee0_.surname as surname5_1_0_, detail1_.id as id1_0_1_, detail1_.city as city2_0_1_, detail1_.email as email3_0_1_, detail1_.phone_number as phone_nu4_0_1_ from employees_o2o_uni employee0_ left outer join details_o2o_uni detail1_ on employee0_.details_id=detail1_.id where employee0_.id=?
            Employee #1 { name='Юрий', surname='Гаврилов', department='Midfielder', salary=450 }
            Hibernate: delete from employees_o2o_uni where id=?
            Hibernate: delete from details_o2o_uni where id=?

            SELECT * FROM spring.employees_o2o_uni;
	        2	Сергей	Родионов	IT	1100    <null>

	        SELECT * FROM spring.details_o2o_uni;
	        <null>
            */
        }
        finally {
            session.close();
            factory.close();
        }
    }
}
