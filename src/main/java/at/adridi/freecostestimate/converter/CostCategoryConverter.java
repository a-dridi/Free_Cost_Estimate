/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.converter;

import at.adridi.freecostestimate.model.CostCategory;
import at.adridi.freecostestimate.service.ServiceImpl;
import at.adridi.freecostestimate.service.ServiceInterface;
import java.util.NoSuchElementException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * Converter for CostCategory Selector
 *
 * @author A.Dridi
 */
@FacesConverter("costCategoryConverter")
public class CostCategoryConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String costCategoryTitle) {
        if (costCategoryTitle == null || costCategoryTitle.trim().isEmpty()) {
            return null;
        } else {
            ServiceInterface<CostCategory> costCategoryService = new ServiceImpl<>(CostCategory.class);
            try {
                return costCategoryService.getAll(CostCategory.class).stream().filter(costCategory -> costCategory.getTitle().equals(costCategoryTitle)).findFirst().get();
            } catch (NoSuchElementException e) {
                return null;
            }
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object costCategoryObject) {
        try {
            return ((CostCategory) costCategoryObject).getTitle();
        } catch (NullPointerException e) {
            return "";
        }
    }

}
