package udemy.crud;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import udemy.crud.entity.Employee;

public class TestUpd_n2 {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();
        Session session = null;
        Employee emp;
        try {
            /*
            1	Виктор	    Зингер	    Keeper	500
            2	Виктор	    Шалимов	    Attack	400
            3	Владимир	Шадрин	    Attack	450
            4	Александр	Якушев	    Attack	500
            5	Юрий	    Ляпкин	    Defence	400
            6	Сергей	    Коротков	Defence	350
            */
            session = factory.getCurrentSession();
            session.beginTransaction();

            emp = session.get(Employee.class,1);
            emp.setSalary(emp.getSalary() + 100);
            emp = session.get(Employee.class,2);
            emp.setSalary(600);

            session.getTransaction().commit();
            /*
            1	Виктор	    Зингер	    Keeper	600 <--
            2	Виктор	    Шалимов	    Attack	600 <--
            3	Владимир	Шадрин	    Attack	450
            4	Александр	Якушев	    Attack	500
            5	Юрий	    Ляпкин	    Defence	400
            6	Сергей	    Коротков	Defence	350
            */
        }
        finally {
            session.close();
            factory.close();
        }
    }
}

