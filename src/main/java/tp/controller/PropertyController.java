package tp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import tp.model.PropertiesList;
import tp.model.Property;
import tp.service.PropertyService;

import java.util.ArrayList;
import java.util.List;

import static sun.security.krb5.internal.crypto.Nonce.value;

@Controller
public class PropertyController {
    @Autowired
    PropertyService propertyService;

    @GetMapping("/properties")
    public ModelAndView listAllProperties() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("properties");

        PropertiesList propertiesList = propertyService.findAllProperties();

        modelAndView.addObject("pageTitle", "All properties");
        modelAndView.addObject("propertiesList", propertiesList);
        return modelAndView;
    }

    @GetMapping("/properties/filter")
    public ModelAndView listPropertiesFilter(
            @RequestParam(value="name",required=true)String name,
            @RequestParam(value="value",required=true)String value
            ){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("properties");
        PropertiesList propertiesList;
        switch (name){
            case "type" :
                propertiesList = propertyService.findPropertiesByType(value);
                break;
            case "price" :
                propertiesList = propertyService.findPropertiesByMaxPrice(Double.parseDouble(value));
                break;
            case "capacity":
                propertiesList = propertyService.findPropertiesByCapacity(Integer.parseInt(value));
                break;
            case "status":
                if(value.equals("disponible")) value = "0";
                else if(value.equals("attente")) value = "1";
                else if(value.equals("occupe"))value = "2";
                propertiesList = propertyService.findPropertiesByStatus(Integer.parseInt(value));
                break;
            default:
                propertiesList = new PropertiesList();
                break;
        }


        modelAndView.addObject("pageTitle", "Filtred properties");
        modelAndView.addObject("propertiesList", propertiesList);
        return modelAndView;
    }
}
