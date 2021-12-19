package udemy.one2one_bi;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import udemy.one2one_bi.entity.Detail;
import udemy.one2one_bi.entity.Employee;

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
            session = factory.getCurrentSession();

            // insert:
            /*
            session.beginTransaction();

            employee = new Employee("Юрий", "Гаврилов", "Midfielder", 450);
            detail = new Detail("Москва", "333-1212", "gavrila@gmail.com");
            employee.setEmpDetail(detail);
            detail.setEmployee(employee);       // чтобы при сохранении detail сохранился и employee
            session.save(detail);               // сохраняем detail, и employee тоже запишется

            employee = new Employee("Сергей", "Родионов", "forward", 380);
            detail = new Detail("Москва", "433-7311", "pod@mail.ru");
            employee.setEmpDetail(detail);
            detail.setEmployee(employee);
            session.save(detail);

            session.getTransaction().commit();
            System.out.println("insert Done");
            */
            /*
            Hibernate: insert into details_o2o (city, email, phone_number) values (?, ?, ?)
            Hibernate: insert into employees_o2o (department, detail_id, name, salary, surname) values (?, ?, ?, ?, ?)
            Hibernate: insert into details_o2o (city, email, phone_number) values (?, ?, ?)
            Hibernate: insert into employees_o2o (department, detail_id, name, salary, surname) values (?, ?, ?, ?, ?)
            insert Done

            SELECT * FROM spring.employees_o2o_uni;
            1	Юрий	Гаврилов	Midfielder	450	1
            2	Сергей	Родионов	forward	    380	2

            SELECT * FROM spring.details_o2o_uni;
            1	Москва	333-1212	gavrila@gmail.com
            2	Москва	433-7311	pod@mail.ru
            */

            // update: такой-же

            // delete:
            session = factory.getCurrentSession();
            session.beginTransaction();

            detail = session.get(Detail.class, 1);
            System.out.println(detail);
            System.out.println(detail.getEmployee());
            session.delete(detail);

            session.getTransaction().commit();
            System.out.println("delete Done");
            /*
            Hibernate: select detail0_.id as id1_0_0_, detail0_.city as city2_0_0_, detail0_.email as email3_0_0_, detail0_.phone_number as phone_nu4_0_0_, employee1_.id as id1_1_1_, employee1_.department as departme2_1_1_, employee1_.detail_id as detail_i6_1_1_, employee1_.name as name3_1_1_, employee1_.salary as salary4_1_1_, employee1_.surname as surname5_1_1_ from details_o2o detail0_ left outer join employees_o2o employee1_ on detail0_.id=employee1_.detail_id where detail0_.id=?
            Detail #1 { city='Москва', phoneNumber='333-1212', email='mumu@rambler.ru' }
            Employee #1 { name='Юрий', surname='Гаврилов', department='Midfielder', salary=450 }
            Hibernate: delete from employees_o2o where id=?
            Hibernate: delete from details_o2o where id=?
            delete Done

            SELECT * FROM spring.employees_o2o_uni;
	        2	Сергей	Родионов	forward	1100	2

	        SELECT * FROM spring.details_o2o_uni;
	        2	Москва	910-876-8722	pod@mail.ru
            */
        }
        finally {
            session.close();
            factory.close();
        }
    }
}
