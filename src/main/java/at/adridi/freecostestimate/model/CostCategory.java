/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 *
 * The services that a user can select
 *
 * @author A.Dridi
 */
@Entity
@SequenceGenerator(name = "costcategory_seq", sequenceName = "costcategory_id_seq", allocationSize = 1)
public class CostCategory implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="costcategory_seq")
    private Integer id;
    private String title;
    private Integer centPrice;

    /**
     * 
     * @param title
     * @param centPrice 
     */
    public CostCategory(String title, Integer centPrice) {
        this.title = title;
        this.centPrice = centPrice;
    }

    public CostCategory() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCentPrice() {
        return centPrice;
    }

    public void setCentPrice(Integer centPrice) {
        this.centPrice = centPrice;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.title);
        hash = 29 * hash + Objects.hashCode(this.centPrice);
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
        final CostCategory other = (CostCategory) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.centPrice, other.centPrice)) {
            return false;
        }
        return true;
    }

}
