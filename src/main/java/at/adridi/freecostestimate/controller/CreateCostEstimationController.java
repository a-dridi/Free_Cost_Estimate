/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.controller;

import at.adridi.freecostestimate.model.CostCategory;
import at.adridi.freecostestimate.model.CostEntity;
import at.adridi.freecostestimate.model.CostEstimate;
import at.adridi.freecostestimate.model.RatioDifficulty;
import at.adridi.freecostestimate.runnables.SendCostEstimateEmail;
import at.adridi.freecostestimate.service.EmailService;
import at.adridi.freecostestimate.service.ServiceImpl;
import at.adridi.freecostestimate.service.ServiceInterface;
import at.adridi.freecostestimate.util.DefaultCostsValues;
import at.adridi.freecostestimate.util.EmailSettings;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 * User can create cost estimate and sent it to his e-mail address
 *
 * @author A.Dridi
 */
@Named(value = "createCostEstimationController")
@ViewScoped
public class CreateCostEstimationController implements Serializable {

    private ServiceInterface<CostCategory> costCategoryService = new ServiceImpl<>(CostCategory.class);
    private ServiceInterface<RatioDifficulty> ratioDiffcultyService = new ServiceImpl<>(RatioDifficulty.class);
    private ServiceInterface<CostEntity> costEntityService = new ServiceImpl<>(CostEntity.class);
    private ServiceInterface<CostEstimate> costEstimateService = new ServiceImpl<>(CostEstimate.class);

    private List<CostEntity> costEntitiesOfNewCostEstimate = new ArrayList<>();
    private CostEstimate newCostEstimate = new CostEstimate();
    private List<CostCategory> defaultCostCategories = DefaultCostsValues.getCostCategories();
    private List<RatioDifficulty> defaultRatioDifficulties = DefaultCostsValues.getRatioDifficulty();
    private boolean calulcationDone = false;
    private boolean costEstimateCreated = false;
    private String userEmail;

    /**
     * Creates a new instance of CreateCostEstimationController
     */
    public CreateCostEstimationController() {
    }

    /**
     * Add default values to the database, if they were not added.
     */
    @PostConstruct
    public void init() {
        if (getSavedCostCategory() == null) {
            List<CostCategory> costCategories = DefaultCostsValues.getCostCategories();
            for (CostCategory costCategory : costCategories) {
                this.costCategoryService.add(costCategory);
            }
        }

        if (getRatioDifficultyCategory() == null) {
            List<RatioDifficulty> ratioDifficulties = DefaultCostsValues.getRatioDifficulty();
            for (RatioDifficulty ratioDifficulty : ratioDifficulties) {
                this.ratioDiffcultyService.add(ratioDifficulty);
            }
        }
        this.addCostEntity();
    }

    /**
     * Returns the CostCategory items that are saved in the database, when the
     * size of the default CostCategory items (from the util class) is not
     * smaller as the size of saved CostCategory items in the database. If that
     * is not the case, then the return is null.
     *
     * @return
     */
    public List<CostCategory> getSavedCostCategory() {
        List<CostCategory> savedCostCategories = this.costCategoryService.getAll(CostCategory.class);
        if (savedCostCategories != null && (savedCostCategories.size() >= this.defaultCostCategories.size())) {
            return savedCostCategories;
        } else {
            return null;
        }
    }

    /**
     * Returns the RatioDifficulty items that are saved in the database, when
     * the size of the default RatioDifficulty items (from the util class) is
     * not smaller as the size of saved RatioDifficulty items in the database.
     * If that is not the case, then the return is null.
     *
     * @return
     */
    public List<RatioDifficulty> getRatioDifficultyCategory() {
        List<RatioDifficulty> savedRatioDiffculties = this.ratioDiffcultyService.getAll(RatioDifficulty.class);
        if (savedRatioDiffculties != null && (savedRatioDiffculties.size() >= this.defaultRatioDifficulties.size())) {
            return savedRatioDiffculties;
        } else {
            return null;
        }
    }

    /**
     * OnChange for "RatioDifficulty". Adjusts the price of a cost entity to the
     * selected ratio difficulty.
     *
     * @param selectedRatioDifficulty
     * @param indexOfCostEntityList
     */
    public void setRatioDifficulty(RatioDifficulty selectedRatioDifficulty, int indexOfCostEntityList) {
        this.costEntitiesOfNewCostEstimate.get(indexOfCostEntityList).setSelectedRatio(selectedRatioDifficulty);
        Integer defaultCentPrice = this.costEntitiesOfNewCostEstimate.get(indexOfCostEntityList).getSelectedCostCategory().getCentPrice();
        this.costEntitiesOfNewCostEstimate.get(indexOfCostEntityList).setCentPrice(defaultCentPrice * selectedRatioDifficulty.getRatio());
    }

    /**
     * Action. Add new form line that has fields for a cost entity.
     */
    public void addCostEntity() {
        this.costEntitiesOfNewCostEstimate.add(new CostEntity());
    }

    /**
     * Action. Calculates the cost estimate and displays it for the user below
     * the form.
     */
    public void calculateCostEstimate() {
        int costEntitiesCentSum = 0;
        for (CostEntity costEntity : this.costEntitiesOfNewCostEstimate) {
            costEntitiesCentSum += costEntity.getCentPrice();
        }
        this.newCostEstimate.setSumCent(costEntitiesCentSum);
        this.calulcationDone = true;
    }

    /**
     * Action. When the cost estimate was calculated, then this method will save
     * the cost estimate in the database and sent it to the user's email.
     * address.
     */
    public void createAndSendCostEstimate() {
        if (this.userEmail == null || this.userEmail.trim().equals("") || !this.userEmail.contains("@")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter a correct e-mail address!", ""));
            return;
        }
        this.costEstimateCreated = true;

        //Create cost estimate email content
        Date currentDate = new Date();
        SimpleDateFormat dateHourMinutesFormat = new SimpleDateFormat("dd.MM.yyyy hh:mmm");
        StringBuilder emailText = new StringBuilder("Hello \nThis is the cost estimate that you generated on ");
        emailText.append(dateHourMinutesFormat.format(currentDate));
        emailText.append(": \n\n");

        for (CostEntity costEntity : this.costEntitiesOfNewCostEstimate) {
            this.costEntityService.add(costEntity);
            emailText.append(costEntity.getSelectedCostCategory().getTitle());
            emailText.append(" - ");
            emailText.append(costEntity.getCentPrice() / 100);
            emailText.append(" USD \n");
        }
        emailText.append("-------------\n");
        emailText.append("Cost Estimate Sum: ");
        emailText.append(this.newCostEstimate.getSumCent() / 100);
        emailText.append("USD \n\n");
        emailText.append("If you have any questions, then feel free to contact us. \n\n");
        emailText.append(EmailSettings.EMAIL_FOOTER);

        this.newCostEstimate.setCostEntityList(this.costEntitiesOfNewCostEstimate);
        this.newCostEstimate.setUserEmail(this.userEmail);
        this.costEstimateService.add(newCostEstimate);
        sendCostEstimate(this.userEmail, emailText.toString());
    }

    /**
     * Send cost estimate as an e-mail (in the e-mail message) to the passed
     * e-mail address.
     *
     * @param emailAddress
     * @param emailText The content of the email. Greetings and cost estimate.
     * @param costEntityItems
     * @param costEstimateOfCostEntityItems
     * @return
     */
    public boolean sendCostEstimate(String emailAddress, String emailText) {
        if (emailAddress == null || emailAddress.trim().equals("") || !emailAddress.contains("@") || emailText == null || emailText.trim().equals("")) {
            return false;
        }

        Runnable sendEmailJob = new SendCostEstimateEmail(emailAddress, emailText);
        EmailService.executeEmailJob.submit(sendEmailJob);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cost Estimate was sent!", "Email was sent to the e-mail address: " + emailAddress));
        return true;
    }

    public List<CostEntity> getCostEntitiesOfNewCostEstimate() {
        return costEntitiesOfNewCostEstimate;
    }

    public void setCostEntitiesOfNewCostEstimate(List<CostEntity> costEntitiesOfNewCostEstimate) {
        this.costEntitiesOfNewCostEstimate = costEntitiesOfNewCostEstimate;
    }

    public CostEstimate getNewCostEstimate() {
        return newCostEstimate;
    }

    public void setNewCostEstimate(CostEstimate newCostEstimate) {
        this.newCostEstimate = newCostEstimate;
    }

    public List<CostCategory> getDefaultCostCategories() {
        return defaultCostCategories;
    }

    public void setDefaultCostCategories(List<CostCategory> defaultCostCategories) {
        this.defaultCostCategories = defaultCostCategories;
    }

    public List<RatioDifficulty> getDefaultRatioDifficulties() {
        return defaultRatioDifficulties;
    }

    public void setDefaultRatioDifficulties(List<RatioDifficulty> defaultRatioDifficulties) {
        this.defaultRatioDifficulties = defaultRatioDifficulties;
    }

    public boolean isCalulcationDone() {
        return calulcationDone;
    }

    public void setCalulcationDone(boolean calulcationDone) {
        this.calulcationDone = calulcationDone;
    }

    public boolean isCostEstimateCreated() {
        return costEstimateCreated;
    }

    public void setCostEstimateCreated(boolean costEstimateCreated) {
        this.costEstimateCreated = costEstimateCreated;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.costCategoryService);
        hash = 79 * hash + Objects.hashCode(this.ratioDiffcultyService);
        hash = 79 * hash + Objects.hashCode(this.costEntityService);
        hash = 79 * hash + Objects.hashCode(this.costEstimateService);
        hash = 79 * hash + Objects.hashCode(this.costEntitiesOfNewCostEstimate);
        hash = 79 * hash + Objects.hashCode(this.newCostEstimate);
        hash = 79 * hash + Objects.hashCode(this.defaultCostCategories);
        hash = 79 * hash + Objects.hashCode(this.defaultRatioDifficulties);
        hash = 79 * hash + (this.calulcationDone ? 1 : 0);
        hash = 79 * hash + (this.costEstimateCreated ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.userEmail);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CreateCostEstimationController other = (CreateCostEstimationController) obj;
        if (this.calulcationDone != other.calulcationDone) {
            return false;
        }
        if (this.costEstimateCreated != other.costEstimateCreated) {
            return false;
        }
        if (!Objects.equals(this.userEmail, other.userEmail)) {
            return false;
        }
        if (!Objects.equals(this.costCategoryService, other.costCategoryService)) {
            return false;
        }
        if (!Objects.equals(this.ratioDiffcultyService, other.ratioDiffcultyService)) {
            return false;
        }
        if (!Objects.equals(this.costEntityService, other.costEntityService)) {
            return false;
        }
        if (!Objects.equals(this.costEstimateService, other.costEstimateService)) {
            return false;
        }
        if (!Objects.equals(this.costEntitiesOfNewCostEstimate, other.costEntitiesOfNewCostEstimate)) {
            return false;
        }
        if (!Objects.equals(this.newCostEstimate, other.newCostEstimate)) {
            return false;
        }
        if (!Objects.equals(this.defaultCostCategories, other.defaultCostCategories)) {
            return false;
        }
        if (!Objects.equals(this.defaultRatioDifficulties, other.defaultRatioDifficulties)) {
            return false;
        }
        return true;
    }

    
    
}
