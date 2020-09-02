/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.service;

import at.adridi.freecostestimate.dao.DAOInterface;
import at.adridi.freecostestimate.model.CostCategory;
import at.adridi.freecostestimate.model.CostEstimate;
import at.adridi.freecostestimate.model.RatioDifficulty;
import java.util.List;

/**
 *
 * Generic service interface for accessing DAO
 *
 * @author A.Dridi
 */
public interface ServiceInterface<T> extends DAOInterface<T> {

    public List<T> getAll(Class<T> classObject);

    public T getById(Integer id, Class<T> classObject);

    public Boolean add(T t);

    public Boolean delete(T t);

    public Boolean update(T t);

    public Boolean doSQLQuery(String sqlQuery);

    public Boolean insertCostEntity(CostEstimate costEstimateOfNewCostEntity, CostCategory selectedCostCategory, RatioDifficulty selectedRatio, Integer ratio);

    public Boolean deleteAll(Class<T> classObject);

}
