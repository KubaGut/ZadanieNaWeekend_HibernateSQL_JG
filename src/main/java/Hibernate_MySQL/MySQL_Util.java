package Hibernate_MySQL;


import Entity.Employee;
import Entity.Name;
import org.dom4j.io.ElementModifier;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQL_Util {

    private static final SessionFactory ourSessionFactory;
    private List<Integer> bossesList = new ArrayList<Integer>();

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void saveObjectToDataBase(){
        Session session = getSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();

                for (int i = 1; i < 100001; i++) {
                    Employee employee = new Employee(i, Name.giveName(), Name.giveLastName(), i / 2);
                    session.save(employee);
                }

            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void countSubordinates(int id) throws SQLException, ClassNotFoundException {
        if ((id * 2) <= 100001){
            this.bossesList.add(id* 2);
            this.bossesList.add(id*2 +1);
            countSubordinates(id * 2);
            countSubordinates(id * 2 + 1);
        }
    }

    public void showSubordinates() throws SQLException, ClassNotFoundException {
        Session session = getSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();

            for (Integer p: bossesList){
                List<Employee> subordinate = session.createQuery("FROM Employee E WHERE E.id = " + p ).list();
                System.out.println(subordinate.get(0).getId() + subordinate.get(0).getName() + subordinate.get(0).getLastName());
            }

            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }

    }

}
