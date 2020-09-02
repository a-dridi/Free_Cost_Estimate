/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * An entry of a cost estimate
 *
 * @author A.Dridi
 */
@Entity
@SequenceGenerator(name = "costentity_seq", sequenceName = "costentity_id_seq", allocationSize = 1)
public class CostEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "costentity_seq")
    private Integer id;
    @OneToOne(optional = false, cascade=CascadeType.ALL)
    @JoinColumn(name = "costcategory_costcategory_id")
    private CostCategory selectedCostCategory;
    @OneToOne(optional = false, cascade=CascadeType.ALL)
    @JoinColumn(name = "ratiodifficulty_radio_id")
    private RatioDifficulty selectedRatio;
    private Integer centPrice;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "costentity_costestimate_id")
    private CostEstimate costEstimateOfCostEntity;

    /**
     * 
     * @param selectedCostCategory
     * @param selectedRatio
     * @param centPrice
     * @param costEstimateOfCostEntity 
     */
    public CostEntity(CostCategory selectedCostCategory, RatioDifficulty selectedRatio, Integer centPrice, CostEstimate costEstimateOfCostEntity) {
        this.selectedCostCategory = selectedCostCategory;
        this.selectedRatio = selectedRatio;
        this.centPrice = centPrice;
        this.costEstimateOfCostEntity = costEstimateOfCostEntity;
    }

    public CostEntity() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CostCategory getSelectedCostCategory() {
        return selectedCostCategory;
    }

    public void setSelectedCostCategory(CostCategory selectedCostCategory) {
        this.selectedCostCategory = selectedCostCategory;
    }

    public RatioDifficulty getSelectedRatio() {
        return selectedRatio;
    }

    public void setSelectedRatio(RatioDifficulty selectedRatio) {
        this.selectedRatio = selectedRatio;
    }

    public Integer getCentPrice() {
        return centPrice;
    }

    public void setCentPrice(Integer centPrice) {
        this.centPrice = centPrice;
    }

    public CostEstimate getCostEstimateOfCostEntity() {
        return costEstimateOfCostEntity;
    }

    public void setCostEstimateOfCostEntity(CostEstimate costEstimateOfCostEntity) {
        this.costEstimateOfCostEntity = costEstimateOfCostEntity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.selectedCostCategory);
        hash = 37 * hash + Objects.hashCode(this.selectedRatio);
        hash = 37 * hash + Objects.hashCode(this.centPrice);
        hash = 37 * hash + Objects.hashCode(this.costEstimateOfCostEntity);
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
        final CostEntity other = (CostEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.selectedCostCategory, other.selectedCostCategory)) {
            return false;
        }
        if (!Objects.equals(this.selectedRatio, other.selectedRatio)) {
            return false;
        }
        if (!Objects.equals(this.centPrice, other.centPrice)) {
            return false;
        }
        if (!Objects.equals(this.costEstimateOfCostEntity, other.costEstimateOfCostEntity)) {
            return false;
        }
        return true;
    }

}
