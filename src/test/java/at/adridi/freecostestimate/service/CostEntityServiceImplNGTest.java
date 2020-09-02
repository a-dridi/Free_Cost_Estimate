/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.service;

import at.adridi.freecostestimate.model.CostCategory;
import at.adridi.freecostestimate.model.CostEntity;
import at.adridi.freecostestimate.model.CostEstimate;
import at.adridi.freecostestimate.model.RatioDifficulty;
import at.adridi.freecostestimate.util.DefaultCostsValues;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
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
 * Cost Entity DAO Implementation. Run this test class seperate only, but not
 * together with other test classes.
 *
 * @author A.Dridi
 */
public class CostEntityServiceImplNGTest {

    private ServiceInterface<CostEntity> costEntityService;
    private ServiceInterface<CostEstimate> costEstimateService;
    private ServiceInterface<CostCategory> costCategoryService;
    private ServiceInterface<RatioDifficulty> ratioDifficultyService;

    private Class<CostEntity> classObject = CostEntity.class;

    public CostEntityServiceImplNGTest() {
    }

    @DataProvider
    public Object[][] getTenCostEntityItems() {
        CostEstimate newCostEstimate = new CostEstimate(53404, "name@email.tld");
        Object[][] costEntityItems = new Object[][]{
            {DefaultCostsValues.getCostCategories().get(0), DefaultCostsValues.getRatioDifficulty().get(1), DefaultCostsValues.getRatioDifficulty().get(1).getRatio() * DefaultCostsValues.getCostCategories().get(0).getCentPrice(), newCostEstimate, 1},
            {DefaultCostsValues.getCostCategories().get(1), DefaultCostsValues.getRatioDifficulty().get(2), DefaultCostsValues.getRatioDifficulty().get(2).getRatio() * DefaultCostsValues.getCostCategories().get(2).getCentPrice(), newCostEstimate, 2},
            {DefaultCostsValues.getCostCategories().get(3), DefaultCostsValues.getRatioDifficulty().get(0), DefaultCostsValues.getRatioDifficulty().get(0).getRatio() * DefaultCostsValues.getCostCategories().get(3).getCentPrice(), newCostEstimate, 3},
            {DefaultCostsValues.getCostCategories().get(4), DefaultCostsValues.getRatioDifficulty().get(1), DefaultCostsValues.getRatioDifficulty().get(1).getRatio() * DefaultCostsValues.getCostCategories().get(4).getCentPrice(), newCostEstimate, 4},
            {DefaultCostsValues.getCostCategories().get(2), DefaultCostsValues.getRatioDifficulty().get(0), DefaultCostsValues.getRatioDifficulty().get(0).getRatio() * DefaultCostsValues.getCostCategories().get(2).getCentPrice(), newCostEstimate, 5},
            {DefaultCostsValues.getCostCategories().get(3), DefaultCostsValues.getRatioDifficulty().get(1), DefaultCostsValues.getRatioDifficulty().get(1).getRatio() * DefaultCostsValues.getCostCategories().get(3).getCentPrice(), newCostEstimate, 6},
            {DefaultCostsValues.getCostCategories().get(2), DefaultCostsValues.getRatioDifficulty().get(0), DefaultCostsValues.getRatioDifficulty().get(0).getRatio() * DefaultCostsValues.getCostCategories().get(2).getCentPrice(), newCostEstimate, 7},
            {DefaultCostsValues.getCostCategories().get(1), DefaultCostsValues.getRatioDifficulty().get(1), DefaultCostsValues.getRatioDifficulty().get(1).getRatio() * DefaultCostsValues.getCostCategories().get(1).getCentPrice(), newCostEstimate, 8},
            {DefaultCostsValues.getCostCategories().get(0), DefaultCostsValues.getRatioDifficulty().get(1), DefaultCostsValues.getRatioDifficulty().get(1).getRatio() * DefaultCostsValues.getCostCategories().get(0).getCentPrice(), newCostEstimate, 9},
            {DefaultCostsValues.getCostCategories().get(3), DefaultCostsValues.getRatioDifficulty().get(0), DefaultCostsValues.getRatioDifficulty().get(0).getRatio() * DefaultCostsValues.getCostCategories().get(3).getCentPrice(), newCostEstimate, 10}
        };
        return costEntityItems;
    }

    @BeforeClass
    public void setupCostCategoryServiceImplNGTest() {
        System.out.println(classObject.getName() + " DAO START");
        this.costEntityService = new ServiceImpl<>(CostEntity.class);
        this.costEstimateService = new ServiceImpl<>(CostEstimate.class);
       //setupCleanDatabase();
    }

    public void setupCleanDatabase() {
        Configuration databaseConfiguration = new Configuration();
        databaseConfiguration.configure("/hibernate.cfg.xml");

        SchemaExport databaseSchemaExport = new SchemaExport(databaseConfiguration);
        databaseSchemaExport.setFormat(true);
        databaseSchemaExport.setDelimiter(";");
        databaseSchemaExport.create(Target.EXPORT);
    }

    @Test(priority = 2, description = "Update the CostEntity entry that was created", groups = ("UpdateDataTest"))
    public void testUpdateOneCostEntity() {
        System.out.println("* TEST METHOD: testUpdateOneCostEntity");
        SoftAssert softAssert = new SoftAssert();

        //Add object that will be updated
        CostEntity expectedAddUpdateCostEntity = new CostEntity(DefaultCostsValues.getCostCategories().get(0), DefaultCostsValues.getRatioDifficulty().get(0), 2000, new CostEstimate(64564, "email@email.tld"));
        assertTrue(this.costEntityService.add(expectedAddUpdateCostEntity), "Object must be saved in the database!");
        CostEntity actualNewCostEntity = this.costEntityService.getById(1, classObject);
        try {
            softAssert.assertEquals(actualNewCostEntity.getCentPrice(), expectedAddUpdateCostEntity.getCentPrice(), "Added cent price should be saved in database.");
        } catch (NullPointerException e) {
            fail("Prerequisite for Update Test failed. Created object was not saved in database." + e);
            e.printStackTrace();
        }

        //Update object that was added
        CostEntity expectedCostEntity = new CostEntity(DefaultCostsValues.getCostCategories().get(1), DefaultCostsValues.getRatioDifficulty().get(1), 8000, new CostEstimate(64564, "email2@email.tld"));
        actualNewCostEntity.setCostEstimateOfCostEntity(new CostEstimate(64564, "email2@email.tld"));
        actualNewCostEntity.setSelectedCostCategory(DefaultCostsValues.getCostCategories().get(1));
        actualNewCostEntity.setSelectedRatio(DefaultCostsValues.getRatioDifficulty().get(1));
        actualNewCostEntity.setCentPrice(8000);
        assertTrue(this.costEntityService.update(actualNewCostEntity), "Object must be updated in the database!");
        CostEntity actualCostEntity = this.costEntityService.getById(1, classObject);
        try {
            softAssert.assertEquals(actualCostEntity.getCostEstimateOfCostEntity().getUserEmail(), expectedCostEntity.getCostEstimateOfCostEntity().getUserEmail(), "Added CostEstimate should have been updated in database.");
            softAssert.assertEquals(actualCostEntity.getSelectedCostCategory().getTitle(), expectedCostEntity.getSelectedCostCategory().getTitle(), "Added CostCategory should have been updated in database.");
            softAssert.assertEquals(actualCostEntity.getSelectedRatio().getTitle(), expectedCostEntity.getSelectedRatio().getTitle(), "Added RatioDifficulty should have been updated in database.");
            softAssert.assertEquals(actualCostEntity.getCentPrice(), expectedCostEntity.getCentPrice(), "Added CentPrice should have been updated in database.");
            softAssert.assertEquals(actualCostEntity.getId(), Integer.valueOf(1), "PK must be 1.");
        } catch (NullPointerException e) {
            fail("Created object was not saved in database. " + e);
            e.printStackTrace();
        }
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Add one CostEntity entry and check if its added correctly (correct attribute values and PK is 1)", groups = {"AddDataTest"})
    public void testAddOneAndCheckCostEntity() {
        deleteCostEntityTableData();
        System.out.println("* TEST METHOD: testAddOneAndCheckCostEntity");
        SoftAssert softAssert = new SoftAssert();
        CostEstimate expectedCostEstimate = new CostEstimate(53404, "name@email.tld");
        CostEntity expectedCostEntity = new CostEntity(DefaultCostsValues.getCostCategories().get(0), DefaultCostsValues.getRatioDifficulty().get(0), 2000, expectedCostEstimate);
        List<CostEntity> newCostEntities = new ArrayList<>();
        newCostEntities.add(expectedCostEntity);
        expectedCostEstimate.setCostEntityList(newCostEntities);
        assertTrue(this.costEntityService.add(expectedCostEntity), "Object must be updated in the database!");
        System.out.println("ADDONE SIZE: " + this.costEntityService.getAll(classObject).size());
        CostEntity actualCostEntity = this.costEntityService.getById(1, classObject);
        try {
            softAssert.assertEquals(actualCostEntity.getCentPrice(), expectedCostEntity.getCentPrice(), "Added cent price should have been saved in database.");
            softAssert.assertEquals(actualCostEntity.getCostEstimateOfCostEntity().getId(), expectedCostEntity.getCostEstimateOfCostEntity().getId(), "Added CostEstimate reference should have been saved in database.");
            softAssert.assertEquals(actualCostEntity.getSelectedCostCategory().getId(), expectedCostEntity.getSelectedCostCategory().getId(), "Added CostEntity reference should have been saved in database.");
            softAssert.assertEquals(actualCostEntity.getSelectedRatio().getId(), expectedCostEntity.getSelectedRatio().getId(), "Added RatioDifficulty reference should have been saved in database.");
            softAssert.assertEquals(actualCostEntity.getId(), Integer.valueOf(1), "PK must be 1.");

        } catch (NullPointerException e) {
            fail("Created object was not saved in database. " + e);
            e.printStackTrace();
        }
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = {"testAddOneAndCheckCostEntity"}, priority = 3, description = "Add 10 CostEntity entries and check if its added correctly (correct attribute values and PK is incremented till 10)", groups = {"AddDataTest"}, dataProvider = "getTenCostEntityItems")
    public void testAddTenAndCheckCostEntity(CostCategory costCategoryItem, RatioDifficulty radioDifficultyItem, Integer centPrice, CostEstimate costEstimateOfCostEntityItem, Integer index) {
        System.out.println("* TEST METHOD: testAddTenAndCheckCostEntity");
        SoftAssert softAssert = new SoftAssert();
        CostEstimate expectedCostEstimate = costEstimateOfCostEntityItem;
        CostEntity expectedCostEntity = new CostEntity(costCategoryItem, radioDifficultyItem, centPrice, costEstimateOfCostEntityItem);
        List<CostEntity> newCostEntities = new ArrayList<>();
        newCostEntities.add(expectedCostEntity);
        expectedCostEstimate.setCostEntityList(newCostEntities);
        assertTrue(this.costEntityService.add(expectedCostEntity), "Object must be updated in the database!");

        CostEntity actualCostEntity = this.costEntityService.getById(index, classObject);
        try {
            softAssert.assertEquals(actualCostEntity.getCentPrice(), expectedCostEntity.getCentPrice(), "Added cent price should have been saved in database.");
            softAssert.assertEquals(actualCostEntity.getCostEstimateOfCostEntity().getId(), expectedCostEntity.getCostEstimateOfCostEntity().getId(), "Added CostEstimate reference should have been saved in database.");
            softAssert.assertEquals(actualCostEntity.getSelectedCostCategory().getId(), expectedCostEntity.getSelectedCostCategory().getId(), "Added CostEntity reference should have been saved in database.");
            softAssert.assertEquals(actualCostEntity.getSelectedRatio().getId(), expectedCostEntity.getSelectedRatio().getId(), "Added RatioDifficulty reference should have been saved in database.");
            softAssert.assertEquals(actualCostEntity.getId(), index, "PK must be " + index + " .");

        } catch (NullPointerException e) {
            fail("Created object was not saved in database. " + e);
            e.printStackTrace();
        }
        softAssert.assertAll();

        if (index == 10) {
            deleteCostEntityTableData();
        }
    }

    @Test(priority = 4, description = "Insert one CostEntity (with passed values) and check if its added correctly (correct attribute values and PK is 1)")
    public void testInsertOneAndCheckCostEntity() {
        System.out.println("* TEST METHOD: testInsertOneAndCheckCostEntity");
        SoftAssert softAssert = new SoftAssert();
        CostEstimate expectedCostEstimate = new CostEstimate(53404, "name@email.tld");
        CostEntity expectedCostEntity = new CostEntity(DefaultCostsValues.getCostCategories().get(0), DefaultCostsValues.getRatioDifficulty().get(0), DefaultCostsValues.getCostCategories().get(0).getCentPrice(), expectedCostEstimate);
        List<CostEntity> newCostEntities = new ArrayList<>();
        newCostEntities.add(expectedCostEntity);
        expectedCostEstimate.setCostEntityList(newCostEntities);
        softAssert.assertTrue(this.costEntityService.insertCostEntity(expectedCostEstimate, DefaultCostsValues.getCostCategories().get(0), DefaultCostsValues.getRatioDifficulty().get(0), DefaultCostsValues.getRatioDifficulty().get(0).getRatio()), "Object must be saved in the database!");

        CostEntity actualCostEntity = this.costEntityService.getById(1, classObject);
        try {
            softAssert.assertEquals(actualCostEntity.getCentPrice(), expectedCostEntity.getCentPrice(), "Added cent price should have been saved in database.");
            softAssert.assertEquals(actualCostEntity.getCostEstimateOfCostEntity().getId(), expectedCostEntity.getCostEstimateOfCostEntity().getId(), "Added CostEstimate reference should have been saved in database.");
            softAssert.assertEquals(actualCostEntity.getSelectedCostCategory().getTitle(), expectedCostEntity.getSelectedCostCategory().getTitle(), "Added CostCategory reference should have been saved in database.");
            softAssert.assertEquals(actualCostEntity.getSelectedRatio().getRatio(), expectedCostEntity.getSelectedRatio().getRatio(), "Added RatioDifficulty reference should have been saved in database.");
            softAssert.assertEquals(actualCostEntity.getId(), Integer.valueOf(1), "PK must be 1.");

        } catch (NullPointerException e) {
            fail("Created object was not saved in database. " + e);
            e.printStackTrace();
        }
        softAssert.assertAll();
    }

    @Test(priority = 5, description = "Insert 10 CostEntity (with passed values)  and check if its added correctly (correct attribute values and PK is incremented till 10)", dataProvider = "getTenCostEntityItems")
    public void testInsertTenAndCheckCostEntity(CostCategory costCategoryItem, RatioDifficulty radioDifficultyItem, Integer centPrice, CostEstimate costEstimateOfCostEntityItem, Integer index) {
        System.out.println("* TEST METHOD: testInsertTenAndCheckCostEntity");
        SoftAssert softAssert = new SoftAssert();
        CostEstimate expectedCostEstimate = costEstimateOfCostEntityItem;
        CostEntity expectedCostEntity = new CostEntity(costCategoryItem, radioDifficultyItem, costCategoryItem.getCentPrice(), costEstimateOfCostEntityItem);
        List<CostEntity> newCostEntities = new ArrayList<>();
        newCostEntities.add(expectedCostEntity);
        expectedCostEstimate.setCostEntityList(newCostEntities);
        softAssert.assertTrue(this.costEntityService.insertCostEntity(expectedCostEstimate, costCategoryItem, radioDifficultyItem, DefaultCostsValues.getRatioDifficulty().get(0).getRatio()), "Object must be saved in the database!");

        CostEntity actualCostEntity = this.costEntityService.getById(index, classObject);
        try {
            softAssert.assertEquals(actualCostEntity.getCentPrice(), expectedCostEntity.getCentPrice(), "Added cent price should have been saved in database.");
            softAssert.assertEquals(actualCostEntity.getCostEstimateOfCostEntity().getId(), expectedCostEntity.getCostEstimateOfCostEntity().getId(), "Added CostEstimate reference should have been saved in database.");
            softAssert.assertEquals(actualCostEntity.getSelectedCostCategory().getTitle(), expectedCostEntity.getSelectedCostCategory().getTitle(), "Added CostCategory reference should have been saved in database.");
            softAssert.assertEquals(actualCostEntity.getSelectedRatio().getRatio(), expectedCostEntity.getSelectedRatio().getRatio(), "Added RatioDifficulty reference should have been saved in database.");
            softAssert.assertEquals(actualCostEntity.getId(), index, "PK must be " + index + " .");
        } catch (NullPointerException e) {
            fail("Created object was not saved in database. " + e);
            e.printStackTrace();
        }
        softAssert.assertAll();

        if (index == 10) {
            deleteCostEntityTableData();
        }
    }

    @Test(priority = 6, description = "Delete one created CostEntity entry", groups = ("DeleteDataTest"))
    public void testDeleteOneCostCategory() {
        System.out.println("* TEST METHOD: testDeleteOneCostCategory");
        SoftAssert softAssert = new SoftAssert();
        CostEstimate expectedCostEstimate = new CostEstimate(53404, "name@email.tld");
        CostEntity expectedCostEntity = new CostEntity(DefaultCostsValues.getCostCategories().get(0), DefaultCostsValues.getRatioDifficulty().get(0), 2000, expectedCostEstimate);
        List<CostEntity> newCostEntities = new ArrayList<>();
        newCostEntities.add(expectedCostEntity);
        expectedCostEstimate.setCostEntityList(newCostEntities);
        this.costEntityService.add(expectedCostEntity);

        CostEntity actualCostEntity = this.costEntityService.getById(1, classObject);
        try {
            softAssert.assertEquals(actualCostEntity.getCentPrice(), expectedCostEntity.getCentPrice(), "Prerequisite for this test case faile. Added title should have been saved in database.");
        } catch (NullPointerException e) {
            fail("Prerequisite for this test case failed. Object that will be deleted afterwards was not saved in the database." + e);
        }

        try {
            softAssert.assertTrue(this.costEntityService.delete(actualCostEntity), "Created object  must be deleted");
            softAssert.assertTrue(this.costEntityService.getAll(classObject) == null, "Table CostCategory should be empty.");
        } catch (NullPointerException e) {
            fail("Checking CostCategory table content failed. Database cannot be accessed." + e);
            e.printStackTrace();
        }
    }

    public void deleteCostEntityTableData() {
        System.out.println("Delete CostEntity Table Data");
        this.costEntityService.deleteAll(classObject);
        this.costEntityService.doSQLQuery("ALTER SEQUENCE costentity_id_seq RESTART WITH 1");
    }

    public void deleteAllTableData() {
        System.out.println("Delete CostEstimate Table Data");
        this.costEstimateService.deleteAll(CostEstimate.class);
        this.costEstimateService.doSQLQuery("ALTER SEQUENCE costestimate_id_seq RESTART WITH 1");
        System.out.println("Delete CostCategory Table Data");
        this.costCategoryService.deleteAll(CostCategory.class);
        this.costCategoryService.doSQLQuery("ALTER SEQUENCE costcategory_id_seq RESTART WITH 1");
        System.out.println("Delete RatioDifficulty Table Data");
        this.ratioDifficultyService.deleteAll(RatioDifficulty.class);
        this.ratioDifficultyService.doSQLQuery("ALTER SEQUENCE ratiodifficulty_id_seq RESTART WITH 1");
        System.out.println("Delete CostEntity Table Data");
        this.costEntityService.deleteAll(classObject);
        this.costEntityService.doSQLQuery("ALTER SEQUENCE costentity_id_seq RESTART WITH 1");
    }

    @AfterMethod()
    public void clearUpTableData(ITestResult result) {
        if (!result.getName().equals("testAddTenAndCheckCostEntity") && !result.getName().equals("testAddCostEntity") && !result.getName().equals("testInsertTenAndCheckCostEntity")) {
            deleteCostEntityTableData();
            // deleteAllTableData();
        }
    }

    @AfterClass()
    public void tearDownCostEntityServiceImplNGTest() {
        System.out.println(classObject.getName() + " DAO END");
    }

}
