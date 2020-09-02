/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.converter;

import at.adridi.freecostestimate.model.RatioDifficulty;
import at.adridi.freecostestimate.service.ServiceImpl;
import at.adridi.freecostestimate.service.ServiceInterface;
import java.util.NoSuchElementException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * Converter for RatioDifficulty Selector
 *
 * @author A.Dridi
 */
@FacesConverter("ratioDifficultyConverter")
public class RatioDifficultyConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String ratioDifficultyTitle) {
        if (ratioDifficultyTitle == null || ratioDifficultyTitle.trim().isEmpty()) {
            return null;
        } else {
            ServiceInterface<RatioDifficulty> ratioDifficultyService = new ServiceImpl<>(RatioDifficulty.class);
            try {
                return ratioDifficultyService.getAll(RatioDifficulty.class).stream().filter(ratioDifficulty -> ratioDifficulty.getTitle().equals(ratioDifficultyTitle)).findFirst().get();
            } catch (NoSuchElementException e) {
                return null;
            }
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object ratioDifficultyObject) {
        try {
            return ((RatioDifficulty) ratioDifficultyObject).getTitle();
        } catch (NullPointerException e) {
            return "";
        }
    }
}
