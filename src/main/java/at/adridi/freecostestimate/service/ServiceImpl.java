/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.service;

import at.adridi.freecostestimate.dao.DAOImpl;
import at.adridi.freecostestimate.dao.DAOInterface;
import at.adridi.freecostestimate.model.CostCategory;
import at.adridi.freecostestimate.model.CostEstimate;
import at.adridi.freecostestimate.model.RatioDifficulty;
import java.util.List;

/**
 *
 * An implemntation of DAO service
 *
 * @author A.Dridi
 */
public class ServiceImpl<T> implements ServiceInterface<T> {

    private DAOInterface<T> dao;
    private Class<T> classObject;

    public ServiceImpl(Class<T> classObject) {
        this.classObject = classObject;
        this.dao = new DAOImpl();
    }

    @Override
    public List<T> getAll(Class<T> classObject) {
        return this.dao.getAll(classObject);
    }

    @Override
    public T getById(Integer id, Class<T> classObject) {
        return this.dao.getById(id, classObject);
    }

    @Override
    public Boolean add(T t) {
        return this.dao.add(t);
    }

    @Override
    public Boolean delete(T t) {
        return this.dao.delete(t);
    }

    @Override
    public Boolean update(T t) {
        return this.dao.update(t);
    }

    @Override
    public Boolean doSQLQuery(String sqlQuery) {
        return this.dao.doSQLQuery(sqlQuery);
    }

    @Override
    public Boolean insertCostEntity(CostEstimate costEstimateOfNewCostEntity, CostCategory selectedCostCategory, RatioDifficulty selectedRatio, Integer ratio) {
        return this.dao.insertCostEntity(costEstimateOfNewCostEntity, selectedCostCategory, selectedRatio, ratio);
    }

    @Override
    public Boolean deleteAll(Class<T> classObject) {
        return this.dao.deleteAll(classObject);
    }

}
