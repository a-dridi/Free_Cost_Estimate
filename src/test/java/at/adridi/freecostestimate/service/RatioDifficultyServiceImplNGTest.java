/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.service;

import at.adridi.freecostestimate.model.CostCategory;
import at.adridi.freecostestimate.model.RatioDifficulty;
import at.adridi.freecostestimate.util.DefaultCostsValues;
import java.util.List;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.Target;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * Ratio Difficulty DAO Implementation
 *
 * @author A.Dridi
 */
public class RatioDifficultyServiceImplNGTest {

    private ServiceInterface<RatioDifficulty> ratioDifficultyService;
    private Class<RatioDifficulty> classObject = RatioDifficulty.class;

    public RatioDifficultyServiceImplNGTest() {
    }

    @DataProvider(name = "tenRatioDifficultyItems")
    public Object[][] getTenRatioDifficultyItems() {
        List<RatioDifficulty> defaultRatioDifficultyList = DefaultCostsValues.getRatioDifficulty();
        Object[][] ratioDifficultyItems = new Object[][]{{defaultRatioDifficultyList.get(0).getTitle(), defaultRatioDifficultyList.get(0).getRatio(), 1}, {defaultRatioDifficultyList.get(1).getTitle(), defaultRatioDifficultyList.get(1).getRatio(), 2}, {defaultRatioDifficultyList.get(2).getTitle(), defaultRatioDifficultyList.get(2).getRatio(), 3}, {defaultRatioDifficultyList.get(0).getTitle(), defaultRatioDifficultyList.get(0).getRatio(), 4}, {defaultRatioDifficultyList.get(1).getTitle(), defaultRatioDifficultyList.get(1).getRatio(), 5}, {defaultRatioDifficultyList.get(0).getTitle(), defaultRatioDifficultyList.get(0).getRatio(), 6}, {defaultRatioDifficultyList.get(1).getTitle(), defaultRatioDifficultyList.get(1).getRatio(), 7}, {defaultRatioDifficultyList.get(2).getTitle(), defaultRatioDifficultyList.get(2).getRatio(), 8}, {defaultRatioDifficultyList.get(0).getTitle(), defaultRatioDifficultyList.get(0).getRatio(), 9}, {defaultRatioDifficultyList.get(0).getTitle(), defaultRatioDifficultyList.get(0).getRatio(), 10}};
        return ratioDifficultyItems;
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
        this.ratioDifficultyService = new ServiceImpl<>(RatioDifficulty.class);
        //setupCleanDatabase();
    }

    @Test(priority = 1, description = "Add RatioDifficulty entry and check title only", groups = ("AddDataTest"))
    public void testAddRatioDifficulty() {
        System.out.println("* TEST METHOD: testAddRatioDifficulty");
        SoftAssert softAssert = new SoftAssert();
        RatioDifficulty expectedRatioDifficulty = new RatioDifficulty(DefaultCostsValues.getRatioDifficulty().get(0).getTitle(), DefaultCostsValues.getRatioDifficulty().get(0).getRatio());
        assertTrue(this.ratioDifficultyService.add(expectedRatioDifficulty), "Object must be saved in the database!");
        RatioDifficulty actualRatioDifficulty = this.ratioDifficultyService.getById(1, classObject);
        try {
            softAssert.assertEquals(actualRatioDifficulty.getTitle(), expectedRatioDifficulty.getTitle(), "Added title should have been saved in database.");
        } catch (NullPointerException e) {
            fail("Created object was not saved in database." + e);
            e.printStackTrace();
        }
        softAssert.assertAll();
    }

    @Test(priority = 1, description = "Update the RatioDifficulty entry that was created", groups = ("UpdateDataTest"))
    public void testUpdateOneRatioDifficulty() {
        System.out.println("* TEST METHOD: testUpdateOneRatioDifficulty");
        SoftAssert softAssert = new SoftAssert();

        //Add object that will be updated
        RatioDifficulty expectedNewRatioDifficulty = new RatioDifficulty(DefaultCostsValues.getRatioDifficulty().get(0).getTitle(), DefaultCostsValues.getRatioDifficulty().get(0).getRatio());
        assertTrue(this.ratioDifficultyService.add(expectedNewRatioDifficulty), "Object must be saved in the database!");
        RatioDifficulty actualNewRatioDifficulty = this.ratioDifficultyService.getById(1, classObject);
        try {
            softAssert.assertEquals(actualNewRatioDifficulty.getTitle(), expectedNewRatioDifficulty.getTitle(), "Added title should have been saved in database.");
        } catch (NullPointerException e) {
            fail("Prerequisite for Update Test failed. Created object was not saved in database." + e);
            e.printStackTrace();
        }

        //Update object that was added
        actualNewRatioDifficulty.setTitle(DefaultCostsValues.getRatioDifficulty().get(1).getTitle());
        actualNewRatioDifficulty.setRatio(DefaultCostsValues.getRatioDifficulty().get(1).getRatio());
        RatioDifficulty expectedUpdatedRatioDifficulty = new RatioDifficulty(DefaultCostsValues.getRatioDifficulty().get(1).getTitle(), DefaultCostsValues.getRatioDifficulty().get(1).getRatio());
        assertTrue(this.ratioDifficultyService.update(actualNewRatioDifficulty), "Object must be updated in the database!");
        RatioDifficulty actualUpdatedRatioDifficulty = this.ratioDifficultyService.getById(1, classObject);
        try {
            softAssert.assertEquals(actualUpdatedRatioDifficulty.getTitle(), expectedUpdatedRatioDifficulty.getTitle(), "Added title should have been updated in database.");
            softAssert.assertEquals(actualUpdatedRatioDifficulty.getRatio(), expectedUpdatedRatioDifficulty.getRatio(), "Added Ratio should have been updated in database.");
            softAssert.assertEquals(actualUpdatedRatioDifficulty.getId(), Integer.valueOf(1), "PK must be 1.");
        } catch (NullPointerException e) {
            fail("Created object was not saved in database. " + e);
            e.printStackTrace();
        }
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Add one RatioDifficulty entry and check if its added correctly (correct attribute values and PK is 1)", groups = {"AddDataTest"})
    public void testAddOneAndCheckRatioDifficulty() {
        System.out.println("* TEST METHOD: testAddOneAndCheckRatioDifficulty");
        SoftAssert softAssert = new SoftAssert();
        RatioDifficulty expectedRatioDifficulty = new RatioDifficulty(DefaultCostsValues.getRatioDifficulty().get(0).getTitle(), DefaultCostsValues.getRatioDifficulty().get(0).getRatio());
        assertTrue(this.ratioDifficultyService.add(expectedRatioDifficulty), "Object must be saved in the database!");
        RatioDifficulty actualRatioDifficulty = this.ratioDifficultyService.getById(1, classObject);
        try {
            softAssert.assertEquals(actualRatioDifficulty.getTitle(), expectedRatioDifficulty.getTitle(), "Added Title should have been saved in database.");
            softAssert.assertEquals(actualRatioDifficulty.getRatio(), expectedRatioDifficulty.getRatio(), "Added Ratio should have been saved in database.");
            softAssert.assertEquals(actualRatioDifficulty.getId(), Integer.valueOf(1), "PK must be 1.");
        } catch (NullPointerException e) {
            fail("Created object was not saved in database. " + e);
            e.printStackTrace();
        }
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Add 10 RatioDifficulty entries and check if its added correctly (correct attribute values and PK is incremented till 10)", groups = {"AddDataTest"}, dataProvider = "getTenRatioDifficultyItems")
    public void testAddTenAndCheckRatioDifficulty(String ratioDifficultyTitle, Integer ratioDifficultyRatio, Integer index) {
        System.out.println("* TEST METHOD: testAddTenAndCheckRatioDifficulty");
        SoftAssert softAssert = new SoftAssert();
        RatioDifficulty expectedRatioDifficultyItem = new RatioDifficulty(ratioDifficultyTitle, ratioDifficultyRatio);
        assertTrue(this.ratioDifficultyService.add(expectedRatioDifficultyItem), "Object must be saved in the database!");
        RatioDifficulty actualRatioDifficultyItem = this.ratioDifficultyService.getById(index, classObject);
        try {
            softAssert.assertEquals(actualRatioDifficultyItem.getTitle(), expectedRatioDifficultyItem.getTitle(), "Added Title should have been saved in database.");
            softAssert.assertEquals(actualRatioDifficultyItem.getRatio(), expectedRatioDifficultyItem.getRatio(), "Added Ratio should have been saved in database.");
            softAssert.assertEquals(actualRatioDifficultyItem.getId(), index, "PK must be " + index + ".");
        } catch (NullPointerException e) {
            fail("Created object was not saved in database. " + e);
            e.printStackTrace();
        }
        softAssert.assertAll();

        if (index == 10) {
            deleteRatioDifficultyTableData();
        }
    }

    @Test(priority = 3, description = "Delete one created RatioDifficulty entry", groups = ("DeleteDataTest"))
    public void testDeleteOneRatioDifficulty() {
        System.out.println("* TEST METHOD: testDeleteOneRatioDifficulty");
        SoftAssert softAssert = new SoftAssert();
        RatioDifficulty expectedRatioDifficulty = new RatioDifficulty(DefaultCostsValues.getRatioDifficulty().get(0).getTitle(), DefaultCostsValues.getRatioDifficulty().get(0).getRatio());
        assertTrue(this.ratioDifficultyService.add(expectedRatioDifficulty), "Object must be saved in the database!");
        RatioDifficulty actualRatioDifficulty = this.ratioDifficultyService.getById(1, classObject);
        try {
            softAssert.assertEquals(actualRatioDifficulty.getTitle(), expectedRatioDifficulty.getTitle(), "Prerequisite for this test case faile. Added title should have been saved in database.");
        } catch (NullPointerException e) {
            fail("Prerequisite for this test case failed. Object that will be deleted afterwards was not saved in the database." + e);
        }

        try {
            softAssert.assertTrue(this.ratioDifficultyService.delete(actualRatioDifficulty), "Created object must be deleted");
            softAssert.assertTrue(this.ratioDifficultyService.getAll(classObject) == null, "Table RatioDifficulty should be empty.");
        } catch (NullPointerException e) {
            fail("Checking RatioDifficulty table content failed. Database cannot be accessed." + e);
            e.printStackTrace();
        }
    }

    public void deleteRatioDifficultyTableData() {
        System.out.println("Delete RatioDifficulty Table Data");
        this.ratioDifficultyService.deleteAll(classObject);
        this.ratioDifficultyService.doSQLQuery("ALTER SEQUENCE ratiodifficulty_id_seq RESTART WITH 1");
    }

    public void deleteAllTableData() {
        this.ratioDifficultyService.doSQLQuery("DELETE FROM CostCategory");
        this.ratioDifficultyService.doSQLQuery("DELETE FROM CostEntity");
        this.ratioDifficultyService.doSQLQuery("DELETE FROM CostEstimate");
        this.ratioDifficultyService.doSQLQuery("DELETE FROM RatioDifficulty");
        this.ratioDifficultyService.doSQLQuery("ALTER SEQUENCE costcategory_id_seq RESTART WITH 1");
        this.ratioDifficultyService.doSQLQuery("ALTER SEQUENCE costentity_id_seq RESTART WITH 1");
        this.ratioDifficultyService.doSQLQuery("ALTER SEQUENCE costestimate_id_seq RESTART WITH 1");
        this.ratioDifficultyService.doSQLQuery("ALTER SEQUENCE ratiodifficulty_id_seq RESTART WITH 1");
    }

    @AfterMethod()
    public void clearUpTableData(ITestResult result) {
        if (!result.getName().equals("testAddTenAndCheckRatioDifficulty") && !result.getName().equals("testAddRatioDifficulty")) {
            deleteRatioDifficultyTableData();
           // deleteAllTableData();
        }
    }

    @AfterClass()
    public void tearDownCostCategoryServiceImplNGTest() {
        System.out.println(classObject.getName() + " DAO END");
    }
}
