/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.service;

import at.adridi.freecostestimate.model.CostCategory;
import static org.testng.Assert.*;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * Testing basic DAO method implementations with null values
 *
 * @author A.Dridi
 */
public class DAOImplNullNGTest {

    private ServiceInterface<CostCategory> costCategoryService;
    private Class<CostCategory> classObject = CostCategory.class;
    //Object used for update cost category. 
    private CostCategory existingCostCategory;

    public DAOImplNullNGTest() {
    }

    @BeforeClass
    public void setupCostCategoryServiceImplNGTest() {
        System.out.println(classObject.getName() + " DAO START");
        this.costCategoryService = new ServiceImpl<>(CostCategory.class);
    }

    @Test(description = "Null test on Add method", groups = ("NullDataTest"))
    public void testNullAddCostCategory() {
        System.out.println("* TEST METHOD: testNullAddCostCategory");
        try {
            assertFalse(this.costCategoryService.add(null), "Method should return false.");
        } catch (NullPointerException e) {
            fail("Method should return false and cannot cause any exception. " + e);
            e.printStackTrace();
        }
    }

    @Test(description = "Null test on Delete method", groups = ("NullDataTest"))
    public void testNullDeleteCostCategory() {
        System.out.println("* TEST METHOD: testNullDeleteCostCategory");
        try {
            assertFalse(this.costCategoryService.delete(null), "Method should return false.");
        } catch (NullPointerException e) {
            fail("Method should return false and cannot cause any exception. " + e);
            e.printStackTrace();
        }
    }

    @Test(description = "Null test on Update method", groups = ("NullDataTest"))
    public void testNullUpdateCostCategory() {
        System.out.println("* TEST METHOD: testNullUpdateCostCategory");
        try {
            assertFalse(this.costCategoryService.delete(null), "Method should return false.");
        } catch (NullPointerException e) {
            fail("Method should return false and cannot cause any exception. " + e);
            e.printStackTrace();
        }
    }

    @Test(description = "Null test on Update method", groups = ("NullDataTest"))
    public void testNullGetAllCostCategory() {
        System.out.println("* TEST METHOD: testNullGetAllCostCategory");
        try {
            this.costCategoryService.getAll(null);
        } catch (NullPointerException e) {
            fail("Method should return null and cannot cause any exception. " + e);
            e.printStackTrace();
        }
    }

    @Test(description = "Null test on Insert CostCategory method", groups = ("NullDataTest"))
    public void testNullInsertCostCategory() {
        System.out.println("* TEST METHOD: testNullInsertCostCategory");
        try {
            assertFalse(this.costCategoryService.insertCostEntity(null, null, null, null), "Method should return false.");
        } catch (NullPointerException e) {
            fail("Method should return false and cannot cause any exception. " + e);
            e.printStackTrace();
        }
    }

    @AfterClass()
    public void tearDownCostCategoryServiceImplNGTest() {
        System.out.println(classObject.getName() + " DAO END");
    }

}
