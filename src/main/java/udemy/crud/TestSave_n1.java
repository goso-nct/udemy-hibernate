package udemy.crud;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import udemy.crud.entity.Employee;

public class TestSave_n1 {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();
        Session session = null;
        Employee employee = null;

        try {
            session = factory.getCurrentSession();
            session.beginTransaction();

            employee = new Employee("Виктор", "Зингер", "Keeper",500);
            session.save(employee);
            employee = new Employee("Виктор", "Шалимов", "Attack",400);
            session.save(employee);
            employee = new Employee("Владимир", "Шадрин", "Attack",450);
            session.save(employee);
            employee = new Employee("Александр", "Якушев", "Attack",500);
            session.save(employee);
            employee = new Employee("Юрий", "Ляпкин", "Defence",400);
            session.save(employee);
            employee = new Employee("Сергей", "Коротков", "Defence",350);
            session.save(employee);

            session.getTransaction().commit();
            /*
            1	Виктор	Зингер	Keeper	500
            2	Виктор	Шалимов	Attack	400
            3	Владимир	Шадрин	Attack	450
            4	Александр	Якушев	Attack	500
            5	Юрий	Ляпкин	Defence	400
            6	Сергей	Коротков	Defence	350
             */
        }
        finally {
            session.close();
            factory.close();
        }
    }
}
