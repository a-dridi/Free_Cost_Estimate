/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.service;

import at.adridi.freecostestimate.model.CostCategory;
import at.adridi.freecostestimate.model.CostEntity;
import at.adridi.freecostestimate.model.CostEstimate;
import at.adridi.freecostestimate.util.DefaultCostsValues;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.cfg.Configuration;
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
 * Cost Estimate DAO Implementation. Run this test class seperate only, but not
 * together with other test classes.
 *
 * @author A.Dridi
 */
public class CostEstimateServiceImplNGTest {

    private ServiceInterface<CostEstimate> costEstimateService;
    private Class<CostEstimate> classObject = CostEstimate.class;

    public CostEstimateServiceImplNGTest() {
    }

    @DataProvider(name = "tenCostEstimateItems")
    public Object[][] getTenCostEstimateItems() {
        Object[][] costEstimateItems = new Object[10][4];
        for (int i = 1; i <= 10; i++) {
            CostEstimate costEstimate = new CostEstimate(1000 * i, "email" + i + "@email.tld");
            CostEntity costEntity1 = new CostEntity(DefaultCostsValues.getCostCategories().get(0), DefaultCostsValues.getRatioDifficulty().get(0), DefaultCostsValues.getCostCategories().get(0).getCentPrice() * DefaultCostsValues.getRatioDifficulty().get(0).getRatio(), costEstimate);
            CostEntity costEntity2 = new CostEntity(DefaultCostsValues.getCostCategories().get(1), DefaultCostsValues.getRatioDifficulty().get(1), DefaultCostsValues.getCostCategories().get(1).getCentPrice() * DefaultCostsValues.getRatioDifficulty().get(1).getRatio(), costEstimate);
            CostEntity costEntity3 = new CostEntity(DefaultCostsValues.getCostCategories().get(2), DefaultCostsValues.getRatioDifficulty().get(1), DefaultCostsValues.getCostCategories().get(2).getCentPrice() * DefaultCostsValues.getRatioDifficulty().get(1).getRatio(), costEstimate);
            List<CostEntity> costEntities = new ArrayList<>();
            costEntities.add(costEntity1);
            costEntities.add(costEntity2);
            costEntities.add(costEntity3);
            costEstimateItems[i][0] = costEntities;
            costEstimateItems[i][1] = 1000 * i;
            costEstimateItems[i][2] = "email" + i + "@email.tld";
            costEstimateItems[i][3] = i;
        }
        return costEstimateItems;
    }

    public Object[] getOneCostEstimateItem() {
        Object[] costEstimateItems = new Object[3];
        CostEstimate costEstimate = new CostEstimate(2000, "email@email.tld");
        CostEntity costEntity1 = new CostEntity(DefaultCostsValues.getCostCategories().get(0), DefaultCostsValues.getRatioDifficulty().get(0), DefaultCostsValues.getCostCategories().get(0).getCentPrice() * DefaultCostsValues.getRatioDifficulty().get(0).getRatio(), costEstimate);
        CostEntity costEntity2 = new CostEntity(DefaultCostsValues.getCostCategories().get(1), DefaultCostsValues.getRatioDifficulty().get(1), DefaultCostsValues.getCostCategories().get(1).getCentPrice() * DefaultCostsValues.getRatioDifficulty().get(1).getRatio(), costEstimate);
        CostEntity costEntity3 = new CostEntity(DefaultCostsValues.getCostCategories().get(2), DefaultCostsValues.getRatioDifficulty().get(1), DefaultCostsValues.getCostCategories().get(2).getCentPrice() * DefaultCostsValues.getRatioDifficulty().get(1).getRatio(), costEstimate);
        List<CostEntity> costEntities = new ArrayList<>();
        costEntities.add(costEntity1);
        costEntities.add(costEntity2);
        costEntities.add(costEntity3);
        costEstimateItems[0] = costEntities;
        costEstimateItems[1] = 2000;
        costEstimateItems[2] = "email@email.tld";
        return costEstimateItems;
    }

    public Object[] getOneCostEstimateItemVersion2() {
        Object[] costEstimateItems = new Object[3];
        CostEstimate costEstimate = new CostEstimate(5000, "email2@email.tld");
        CostEntity costEntity1 = new CostEntity(DefaultCostsValues.getCostCategories().get(1), DefaultCostsValues.getRatioDifficulty().get(1), DefaultCostsValues.getCostCategories().get(1).getCentPrice() * DefaultCostsValues.getRatioDifficulty().get(1).getRatio(), costEstimate);
        CostEntity costEntity2 = new CostEntity(DefaultCostsValues.getCostCategories().get(3), DefaultCostsValues.getRatioDifficulty().get(2), DefaultCostsValues.getCostCategories().get(3).getCentPrice() * DefaultCostsValues.getRatioDifficulty().get(2).getRatio(), costEstimate);
        CostEntity costEntity3 = new CostEntity(DefaultCostsValues.getCostCategories().get(2), DefaultCostsValues.getRatioDifficulty().get(1), DefaultCostsValues.getCostCategories().get(2).getCentPrice() * DefaultCostsValues.getRatioDifficulty().get(1).getRatio(), costEstimate);
        List<CostEntity> costEntities = new ArrayList<>();
        costEntities.add(costEntity1);
        costEntities.add(costEntity2);
        costEntities.add(costEntity3);
        costEstimateItems[0] = costEntities;
        costEstimateItems[1] = 5000;
        costEstimateItems[2] = "email2@email.tld";
        return costEstimateItems;
    }

    public void setupCleanDatabase() {
        Configuration databaseConfiguration = new Configuration();
        databaseConfiguration.configure("/hibernate.cfg.xml");

        SchemaExport databaseSchemaExport = new SchemaExport(databaseConfiguration);
        databaseSchemaExport.setFormat(true);
        databaseSchemaExport.setDelimiter(";");
        databaseSchemaExport.create(Target.SCRIPT);
    }

    @BeforeClass
    public void setupCostEstimateServiceImplNGTest() {
        System.out.println(classObject.getName() + " DAO START");
        this.costEstimateService = new ServiceImpl<>(CostEstimate.class);
        setupCleanDatabase();
    }

    @Test(priority = 1, description = "Update the CostCategory entry that was created", groups = ("UpdateDataTest"))
    public void testUpdateOneCostEstimate() {
        System.out.println("* TEST METHOD: testUpdateOneCostEstimate");
        SoftAssert softAssert = new SoftAssert();

        //Add object that will be updated
        Object[] costEstimateTestItem = getOneCostEstimateItem();
        CostEstimate expectedNewCostEstimate = new CostEstimate((Integer) costEstimateTestItem[1], (String) costEstimateTestItem[2]);
        expectedNewCostEstimate.setCostEntityList((List<CostEntity>) costEstimateTestItem[0]);
        assertTrue(this.costEstimateService.add(expectedNewCostEstimate), "Object must be saved in the database!");
        CostEstimate actualNewCostEstimate = this.costEstimateService.getById(1, classObject);
        try {
            softAssert.assertEquals(actualNewCostEstimate.getUserEmail(), expectedNewCostEstimate.getUserEmail(), "Prerequisite for Update Test failed. Added UserEmail should have been saved in database.");
        } catch (NullPointerException e) {
            fail("Prerequisite for Update Test failed. Created object was not saved in database." + e);
            e.printStackTrace();
        }

        //Update object that was added
        Object[] costEstimateTestItemUpdated = getOneCostEstimateItemVersion2();
        actualNewCostEstimate.setSumCent((Integer) costEstimateTestItemUpdated[1]);
        actualNewCostEstimate.setUserEmail((String) costEstimateTestItemUpdated[2]);
        assertTrue(this.costEstimateService.update(actualNewCostEstimate), "Object must be updated in the database!");
        CostEstimate actualUpdatedCostEstimate = this.costEstimateService.getById(1, classObject);
        CostEstimate expecteUpdatedCostEstimate = expectedNewCostEstimate;
        expecteUpdatedCostEstimate.setSumCent((Integer) costEstimateTestItemUpdated[1]);
        expecteUpdatedCostEstimate.setUserEmail((String) costEstimateTestItemUpdated[2]);
        try {
            softAssert.assertEquals(actualUpdatedCostEstimate.getSumCent(), expecteUpdatedCostEstimate.getSumCent(), "Added SumSent should have been updated in database.");
            softAssert.assertEquals(actualUpdatedCostEstimate.getUserEmail(), expecteUpdatedCostEstimate.getUserEmail(), "Added UserEmail should have been updated in database.");
            softAssert.assertEquals(actualUpdatedCostEstimate.getId(), Integer.valueOf(1), "PK must be 1.");
        } catch (NullPointerException e) {
            fail("Created object was not saved in database. " + e);
            e.printStackTrace();
        }
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Add one CostEstimate entry and check if its added correctly (correct attribute values and PK is 1)", groups = {"AddDataTest"})
    public void testAddOneAndCheckCostEstimate() {
        System.out.println("* TEST METHOD: testAddOneAndCheckCostEstimate");
        SoftAssert softAssert = new SoftAssert();
        Object[] costEstimateTestItem = getOneCostEstimateItem();
        CostEstimate expectedCostEstimate = new CostEstimate((Integer) costEstimateTestItem[1], (String) costEstimateTestItem[2]);
        expectedCostEstimate.setCostEntityList((List<CostEntity>) costEstimateTestItem[0]);
        assertTrue(this.costEstimateService.add(expectedCostEstimate), "Object must be saved in the database!");
        CostEstimate actualCostEstimate = this.costEstimateService.getById(1, classObject);
        try {
            softAssert.assertEquals(actualCostEstimate.getUserEmail(), expectedCostEstimate.getUserEmail(), "Added UserEmail should have been saved  in database.");
            softAssert.assertEquals(actualCostEstimate.getId(), Integer.valueOf(1), "PK must be 1.");
        } catch (NullPointerException e) {
            fail("Created object was not saved in database. " + e);
            e.printStackTrace();
        }
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = {"testAddOneAndCheckCostEstimate"}, priority = 3, description = "Add 10 CostEstimate entries and check if its added correctly (correct attribute values and PK is incremented till 10)", groups = {"AddDataTest"}, dataProvider = "getTenCostEstimateItems")
    public void testAddTenAndCheckCostEstimate(List<CostEntity> costEntitiesOfCostEstimate, Integer sumCent, String userEmail, Integer index) {
        System.out.println("* TEST METHOD: testAddTenAndCheckCostEstimate");
        SoftAssert softAssert = new SoftAssert();
        CostEstimate expectedCostEstimateItem = new CostEstimate(sumCent, userEmail);
        expectedCostEstimateItem.setCostEntityList(costEntitiesOfCostEstimate);
        assertTrue(this.costEstimateService.add(expectedCostEstimateItem), "Object must be saved in the database!");

        CostEstimate actualCostEstimate = this.costEstimateService.getById(index, classObject);
        try {
            softAssert.assertEquals(actualCostEstimate.getSumCent(), expectedCostEstimateItem.getSumCent(), "Added SumSent should have been saved  in database.");
            softAssert.assertEquals(actualCostEstimate.getUserEmail(), expectedCostEstimateItem.getUserEmail(), "Added UserEmail should have been saved  in database.");
            softAssert.assertEquals(actualCostEstimate.getId(), index, "PK must be " + index + ".");
        } catch (NullPointerException e) {
            fail("Created object was not saved in database. " + e);
            e.printStackTrace();
        }
        softAssert.assertAll();

        if (index == 10) {
            deleteCostEstimateTableData();
        }
    }

    @Test(priority = 3, description = "Delete one created CostEstimate entry", groups = ("DeleteDataTest"))
    public void testDeleteOneCostEstimate() {
        System.out.println("* TEST METHOD: testDeleteOneCostEstimate");
        SoftAssert softAssert = new SoftAssert();

        Object[] costEstimateTestItem = getOneCostEstimateItemVersion2();
        CostEstimate expectedCostEstimate = new CostEstimate((Integer) costEstimateTestItem[1], (String) costEstimateTestItem[2]);
        expectedCostEstimate.setCostEntityList((List<CostEntity>) costEstimateTestItem[0]);
        assertTrue(this.costEstimateService.add(expectedCostEstimate), "Object must be saved in the database!");

        CostEstimate actualCostEstimate = this.costEstimateService.getById(1, classObject);
        try {
            softAssert.assertEquals(actualCostEstimate.getSumCent(), expectedCostEstimate.getSumCent(), "Prerequisite for this test case faile. Added SumCent should have been saved in database.");
        } catch (NullPointerException e) {
            fail("Prerequisite for this test case failed. Object that will be deleted afterwards was not saved in the database." + e);
        }

        try {
            softAssert.assertTrue(this.costEstimateService.delete(actualCostEstimate), "Created object must have been deleted");
            softAssert.assertTrue(this.costEstimateService.getAll(classObject) == null, "Table CostEstimate should be empty.");
        } catch (NullPointerException e) {
            fail("Checking CostEstimate table content failed. Database cannot be accessed." + e);
            e.printStackTrace();
        }
    }

    public void deleteCostEstimateTableData() {
        System.out.println("Delete CostEstimate Table Data");
        this.costEstimateService.deleteAll(classObject);
        this.costEstimateService.doSQLQuery("ALTER SEQUENCE costestimate_id_seq RESTART WITH 1");
    }

    public void deleteAllTableData() {
        this.costEstimateService.doSQLQuery("DELETE FROM CostCategory");
        this.costEstimateService.doSQLQuery("DELETE FROM CostEntity");
        this.costEstimateService.doSQLQuery("DELETE FROM CostEstimate");
        this.costEstimateService.doSQLQuery("DELETE FROM RatioDifficulty");
        this.costEstimateService.doSQLQuery("ALTER SEQUENCE costcategory_id_seq RESTART WITH 1");
        this.costEstimateService.doSQLQuery("ALTER SEQUENCE costentity_id_seq RESTART WITH 1");
        this.costEstimateService.doSQLQuery("ALTER SEQUENCE costestimate_id_seq RESTART WITH 1");
        this.costEstimateService.doSQLQuery("ALTER SEQUENCE ratiodifficulty_id_seq RESTART WITH 1");
    }

    @AfterMethod()
    public void clearUpTableData(ITestResult result) {
        if (!result.getName().equals("testAddTenAndCheckCostEstimate")) {
            deleteCostEstimateTableData();
            // deleteAllTableData();
        }
    }

    @AfterClass()
    public void tearDownCostCategoryServiceImplNGTest() {
        System.out.println(classObject.getName() + " DAO END");
    }

}
