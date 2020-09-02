/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.util;

import at.adridi.freecostestimate.model.CostCategory;
import at.adridi.freecostestimate.model.RatioDifficulty;
import java.util.ArrayList;
import java.util.List;

/**
 * CHANGE THIS TO YOUR NEEDS. 
 * All cost categories and ratios that are available for the user. But also the difficulties that multiply your price of your service. 
 *
 * @author A.Dridi
 */
public class DefaultCostsValues {

    public static List<CostCategory> getCostCategories() {
        List<CostCategory> costCategories = new ArrayList<>();
        costCategories.add(new CostCategory("Oil Change", 20000));
        costCategories.add(new CostCategory("Tire Change", 30000));
        costCategories.add(new CostCategory("Engine Repair", 50000));
        costCategories.add(new CostCategory("Car Painting", 40000));
        costCategories.add(new CostCategory("Windshield Change", 70000));
        return costCategories;
    }

    public static List<RatioDifficulty> getRatioDifficulty() {
        List<RatioDifficulty> ratioDifficulties = new ArrayList<>();
        ratioDifficulties.add(new RatioDifficulty("Small Size Car", 1));
        ratioDifficulties.add(new RatioDifficulty("Mid-size Car", 2));
        ratioDifficulties.add(new RatioDifficulty("Large Size Car", 5));
        return ratioDifficulties;
    }

}
