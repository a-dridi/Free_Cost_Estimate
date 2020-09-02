/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.service;

import at.adridi.freecostestimate.model.CostCategory;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.metamodel.MetadataBuilder;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.Target;
import static org.testng.Assert.*;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * Cost Category DAO Implementation
 *
 * @author A.Dridi
 */
public class CostCategoryServiceImplNGTest {

    private ServiceInterface<CostCategory> costCategoryService;
    private Class<CostCategory> classObject = CostCategory.class;

    public CostCategoryServiceImplNGTest() {
    }

    @DataProvider(name = "tenCostCategoryItems")
    public Object[][] getTenCostCategoryItems() {
        Object[][] costCategoryItems = new Object[][]{{"Tire Change", 2000, 1}, {"Paint Color", 6000, 2}, {"Repair Engine", 8000, 3}, {"Repair Windshield", 7000, 4}, {"Change Entertainment System", 4600, 5}, {"Repair Audio Boxes", 8000, 6}, {"Change Seating", 7600, 7}, {"Change Oil", 9000, 8}, {"Repair Door", 8000, 9}, {"Change Lights", 4000, 10}};
        return costCategoryItems;
    }

    public void setupCleanDatabase() {
        Configuration databaseConfiguration = new Configuration();
        databaseConfiguration.configure("/hibernate.cfg.xml");

        SchemaExport databaseSchemaExport = new SchemaExport(databaseConfiguration);
        databaseSchemaExport.setFormat(true);
        databaseSchemaExport.setDelimiter(";");
        databaseSchemaExport.create(Target.EXPORT);
    }

    @BeforeClass
    public void setupCostCategoryServiceImplNGTest() {
        System.out.println(classObject.getName() + " DAO START");
        this.costCategoryService = new ServiceImpl<>(CostCategory.class);
        setupCleanDatabase();
    }

    @Test(priority = 1, description = "Update the CostCategory entry that was created", groups = ("UpdateDataTest"))
    public void testUpdateOneCostCategory() {
        System.out.println("* TEST METHOD: testUpdateOneCostCategory");
        SoftAssert softAssert = new SoftAssert();

        //Add object that will be updated
        CostCategory expectedAddUpdateCostCategory = new CostCategory("Repair window", 4000);
        assertTrue(this.costCategoryService.add(expectedAddUpdateCostCategory), "Object must be saved in the database!");
        CostCategory actualNewCostCategory = this.costCategoryService.getById(1, classObject);
        try {
            softAssert.assertEquals(actualNewCostCategory.getTitle(), expectedAddUpdateCostCategory.getTitle(), "Added title should have been saved in database.");
        } catch (NullPointerException e) {
            fail("Prerequisite for Update Test failed. Created object was not saved in database." + e);
            e.printStackTrace();
        }

        //Update object that was added
        actualNewCostCategory.setTitle("Change Car Audio System");
        actualNewCostCategory.setCentPrice(8000);
        assertTrue(this.costCategoryService.update(actualNewCostCategory), "Object must be updated in the database!");
        CostCategory actualUpdatedCostCategory = this.costCategoryService.getById(1, classObject);
        CostCategory expectedUpdatedCostCategory = new CostCategory("Change Car Audio System", 8000);
        try {
            softAssert.assertEquals(actualUpdatedCostCategory.getTitle(), expectedUpdatedCostCategory.getTitle(), "Added title should have been updated in database.");
            softAssert.assertEquals(actualUpdatedCostCategory.getCentPrice(), expectedUpdatedCostCategory.getCentPrice(), "Added CentPrice should have been updated in database.");
            softAssert.assertEquals(actualUpdatedCostCategory.getId(), Integer.valueOf(1), "PK must be 1.");
        } catch (NullPointerException e) {
            fail("Created object was not saved in database. " + e);
            e.printStackTrace();
        }
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Add one CostCategory entry and check if its added correctly (correct attribute values and PK is 1)", groups = {"AddDataTest"})
    public void testAddOneAndCheckCostCategory() {
        System.out.println("* TEST METHOD: testAddOneAndCheckCostCategory");
        SoftAssert softAssert = new SoftAssert();
        CostCategory expectedCostCategory = new CostCategory("Oil Change", 10000);
        assertTrue(this.costCategoryService.add(expectedCostCategory), "Object must be saved in the database!");
        CostCategory actualNewCostCategory = this.costCategoryService.getById(1, classObject);
        try {
            //Actual, Expected
            softAssert.assertEquals(actualNewCostCategory.getTitle(), expectedCostCategory.getTitle(), "Added title should have been saved in database.");
            softAssert.assertEquals(actualNewCostCategory.getId(), Integer.valueOf(1), "PK must be 1.");
        } catch (NullPointerException e) {
            fail("Created object was not saved in database. " + e);
            e.printStackTrace();
        }
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Delete one created CostCategory entry", groups = ("DeleteDataTest"))
    public void testDeleteOneCostCategory() {
        System.out.println("* TEST METHOD: testDeleteOneCostCategory");
        SoftAssert softAssert = new SoftAssert();
        CostCategory expectedCostCategory = new CostCategory("Change windshield", 10000);
        assertTrue(this.costCategoryService.add(expectedCostCategory), "Object must be saved in the database!");
        CostCategory actualCostCategory = this.costCategoryService.getById(1, classObject);
        try {
            softAssert.assertEquals(actualCostCategory.getTitle(), expectedCostCategory.getTitle(), "Prerequisite for this test case faile. Added title should have been saved in database.");
        } catch (NullPointerException e) {
            fail("Prerequisite for this test case failed. Object that will be deleted afterwards was not saved in the database." + e);
        }

        try {
            softAssert.assertTrue(this.costCategoryService.delete(actualCostCategory), "Created object  must be deleted");
            softAssert.assertTrue(this.costCategoryService.getAll(classObject) == null, "Table CostCategory should be empty.");
        } catch (NullPointerException e) {
            fail("Checking CostCategory table content failed. Database cannot be accessed." + e);
            e.printStackTrace();
        }
    }

    public void deleteCostCategoryTableData() {
        System.out.println("Delete CostCategory Table Data");
        this.costCategoryService.deleteAll(classObject);
        this.costCategoryService.doSQLQuery("ALTER SEQUENCE costcategory_id_seq RESTART WITH 1");
    }

    @AfterMethod()
    public void clearUpTableData(ITestResult result) {
        if (!result.getName().equals("testAddTenAndCheckCostCategory")) {
            deleteCostCategoryTableData();
        }
    }

    @AfterClass()
    public void tearDownCostCategoryServiceImplNGTest() {
        System.out.println(classObject.getName() + " DAO END");
    }

}
