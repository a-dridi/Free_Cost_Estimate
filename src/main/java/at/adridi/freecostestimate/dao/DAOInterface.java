/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.dao;

import at.adridi.freecostestimate.model.CostCategory;
import at.adridi.freecostestimate.model.CostEstimate;
import at.adridi.freecostestimate.model.RatioDifficulty;
import java.util.List;

/**
 *
 * DAO Interface for all DAO classes (of models). Has also methods that belong
 * only to certain DAO class.
 *
 * @author A.Dridi
 */
public interface DAOInterface<T> {

    public List<T> getAll(Class<T> classObject);

    public T getById(Integer id, Class<T> classObject);

    public Boolean add(T t);

    public Boolean delete(T t);

    public Boolean update(T t);

    public Boolean deleteAll(Class<T> classObject);

    public Boolean doSQLQuery(String sqlQuery);

    public Boolean insertCostEntity(CostEstimate costEstimateOfNewCostEntity, CostCategory selectedCostCategory, RatioDifficulty selectedRatio, Integer ratio);

}
