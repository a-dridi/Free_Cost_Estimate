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
 * Group of ratio values mapped to service difficulties. Ratios increase the price of a cost category according to the RatioDifficulty (difficulty of service). 
 *
 * @author A.Dridi
 */
@Entity
@SequenceGenerator(name = "ratiodifficulty_seq", sequenceName = "ratiodifficulty_id_seq", allocationSize = 1)
public class RatioDifficulty implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ratiodifficulty_seq")
    private Integer id;
    private String title;
    private Integer ratio;

    /**
     * 
     * @param title
     * @param ratio 
     */
    public RatioDifficulty(String title, Integer ratio) {
        this.title = title;
        this.ratio = ratio;
    }

    public RatioDifficulty() {

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

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.title);
        hash = 37 * hash + Objects.hashCode(this.ratio);
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
        final RatioDifficulty other = (RatioDifficulty) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.ratio, other.ratio)) {
            return false;
        }
        return true;
    }

}
