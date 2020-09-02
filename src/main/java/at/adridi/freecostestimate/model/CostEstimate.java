/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * A cost estimate that is created by a user.
 *
 * @author A.Dridi
 */
@Entity
@SequenceGenerator(name = "costestimate_seq", sequenceName = "costestimate_id_seq", allocationSize = 1)
public class CostEstimate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "costestimate_seq")
    private Integer id;
    @OneToMany(fetch=FetchType.EAGER, mappedBy = "costEstimateOfCostEntity")
    private List<CostEntity> costEntityList = new ArrayList<>();
    private Integer sumCent;
    private String userEmail;

    /**
     * 
     * @param sumCent
     * @param userEmail 
     */
    public CostEstimate(Integer sumCent, String userEmail) {
        this.sumCent = sumCent;
        this.userEmail = userEmail;
    }

    public CostEstimate() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CostEntity> getCostEntityList() {
        return costEntityList;
    }

    public void setCostEntityList(List<CostEntity> costEntityList) {
        this.costEntityList = costEntityList;
    }

    public Integer getSumCent() {
        return sumCent;
    }

    public void setSumCent(Integer sumCent) {
        this.sumCent = sumCent;
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
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.costEntityList);
        hash = 23 * hash + Objects.hashCode(this.sumCent);
        hash = 23 * hash + Objects.hashCode(this.userEmail);
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
        final CostEstimate other = (CostEstimate) obj;
        if (!Objects.equals(this.userEmail, other.userEmail)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.costEntityList, other.costEntityList)) {
            return false;
        }
        if (!Objects.equals(this.sumCent, other.sumCent)) {
            return false;
        }
        return true;
    }
    
}
