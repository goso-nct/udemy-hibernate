package udemy.crud;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import udemy.crud.entity.Employee;

public class TestDel_n3 {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();
        Session session = null;
        Employee emp;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            /*
            1	Виктор	    Зингер	    Keeper	600
            2	Виктор	    Шалимов	    Attack	600
            3	Владимир	Шадрин	    Attack	450
            4	Александр	Якушев	    Attack	500
            5	Юрий	    Ляпкин	    Defence	400
            6	Сергей	    Коротков	Defence	350
            */

            emp = session.get(Employee.class,6);
            session.delete(emp);
            session.createQuery("delete Employee where firstName = 'Виктор'").executeUpdate();

            session.getTransaction().commit();
            /*
            3	Владимир	Шадрин	Attack	450
            4	Александр	Якушев	Attack	500
            5	Юрий	    Ляпкин	Defence	400
            */
        }
        finally {
            session.close();
            factory.close();
        }
    }
}
