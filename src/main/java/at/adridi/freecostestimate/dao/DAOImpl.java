/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.dao;

import at.adridi.freecostestimate.model.CostCategory;
import at.adridi.freecostestimate.model.CostEntity;
import at.adridi.freecostestimate.model.CostEstimate;
import at.adridi.freecostestimate.model.RatioDifficulty;
import at.adridi.freecostestimate.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author A.Dridi
 */
public class DAOImpl<T> implements Serializable, AutoCloseable, DAOInterface<T> {

    @Override
    public void close() throws Exception {
        HibernateUtil.close();
    }

    @Override
    public List<T> getAll(Class<T> classObject) {
        if (classObject == null) {
            return null;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("FROM " + classObject.getName());
            return query.list();
        } catch (HibernateException e) {
            System.out.println("Error in DAO getAll() - " + classObject.getName() + ": " + e);
            return null;
        } finally {
            session.close();
        }

    }

    @Override
    public T getById(Integer id, Class<T> classObject) {
        if (id == null || classObject == null) {
            return null;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("FROM " + classObject.getName() + " WHERE id=:idValue");
            query.setParameter("idValue", id);
            List<T> costCategory = query.list();
            if (costCategory != null && costCategory.size() > 0) {
                return costCategory.get(0);
            } else {
                return null;
            }
        } catch (HibernateException e) {
            System.out.println("Error in DAO getById() - " + classObject.getName() + ": " + e);
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public Boolean add(T t) {
        if (t == null) {
            return false;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(t);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            System.out.println("Error in DAO add(): " + e);
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Boolean delete(T t) {
        if (t == null) {
            return false;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.delete(t);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            System.out.println("Error in DAO delete(): " + e);
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Boolean update(T t) {
        if (t == null) {
            return false;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.update(t);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            System.out.println("Error in DAO update(): " + e);
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Boolean doSQLQuery(String sqlQuery) {
        if (sqlQuery == null) {
            return false;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createSQLQuery(sqlQuery);
            query.executeUpdate();
            return true;
        } catch (HibernateException e) {
            System.out.println("Error in DAO query():" + e);
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    /**
     * Method that adds a CostEntity. Used in CreateFreeCostEstimateController.
     * The (CostEstimate) argument "costEstimateOfNewCostEntity" must be a
     * CostEstimate that was created and is loaded from the database.
     *
     * @param costEstimateOfNewCostEntity Must be a created object from the
     * database
     * @param selectedCostCategory
     * @param selectedRatio
     * @param ratio
     * @return
     */
    @Override
    public Boolean insertCostEntity(CostEstimate costEstimateOfNewCostEntity, CostCategory selectedCostCategory, RatioDifficulty selectedRatio, Integer ratio) {
        if (costEstimateOfNewCostEntity == null || selectedCostCategory == null || selectedRatio == null || ratio == null) {
            return false;
        }

        CostEntity newCostEntity = new CostEntity();
        newCostEntity.setSelectedCostCategory(selectedCostCategory);
        newCostEntity.setSelectedRatio(selectedRatio);
        newCostEntity.setCentPrice(selectedCostCategory.getCentPrice());
        newCostEntity.setCostEstimateOfCostEntity(costEstimateOfNewCostEntity);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(newCostEntity);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            System.out.println("Error in insertCostEntity(): " + e);
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Boolean deleteAll(Class<T> classObject) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query query = session.createQuery("DELETE FROM " + classObject.getName());
            query.executeUpdate();
            return true;
        } catch (HibernateException e) {
            System.out.println("Error in DAO deleteAll() - " + classObject.getName() + ": " + e);
            return false;
        } finally {
            session.close();
        }
    }

}
