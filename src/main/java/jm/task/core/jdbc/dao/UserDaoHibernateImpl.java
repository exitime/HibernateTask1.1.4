package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    private void SQLQuery(String SQL) {
        Transaction t = null;
        try (Session s = Util.getSession()) {
            t = s.beginTransaction();
            s.createSQLQuery(SQL)
                    .executeUpdate();
            t.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (t != null) {
                t.rollback();
            }
        }
    }

    @Override
    public void createUsersTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS users " +
                "(id bigint primary key auto_increment, name varchar(255), lastname varchar(255), age tinyint)";
        SQLQuery(SQL);
    }


    @Override
    public void dropUsersTable() {
        String SQL = "DROP TABLE IF EXISTS users";
        SQLQuery(SQL);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction t = null;
        try (Session s = Util.getSession()) {
            t = s.beginTransaction();
            s.save(new User(name, lastName, age));
            t.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (t != null) {
                t.rollback();
            }
        }
        System.out.println("Пользователь с именем " + name + " добавлен");
    }

    @Override
    public void removeUserById(long id) {
        Transaction t = null;
        try (Session s = Util.getSession()) {
            t = s.beginTransaction();
            s.createQuery("DELETE FROM User WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            t.commit();
        } catch (PersistenceException e) {
            e.printStackTrace();
            if (t != null) {
                t.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction t = null;
        List<User> users = new ArrayList<>();
        try (Session s = sessionFactory.openSession()) {
            t = s.beginTransaction();
            users = s.createQuery("FROM User").getResultList();
            t.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (t != null) {
                t.rollback();
            }
        }
        return users;
    }



    @Override
    public void cleanUsersTable() {
        String SQL = "DELETE FROM users";
        SQLQuery(SQL);
    }
}
