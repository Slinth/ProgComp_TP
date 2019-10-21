package tp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import tp.model.PropertiesList;
import tp.service.PropertyService;

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

    /*@GetMapping("/properties/filter")
    public ModelAndView listPropertiesFilter(
            @RequestParam(value="type",required=false,defaultValue = "NaN")String type,
            @RequestParam(value="price_max",required=false, defaultValue = "NaN")String price_max,
            @RequestParam(value="capacity_max",required=false,defaultValue = "NaN")String capacity_max
            ){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("properties");

        PropertiesList propertiesList;
        if(!type.equals("NaN")) propertyService.findAllProperties();


        modelAndView.addObject("pageTitle", "All properties");
        modelAndView.addObject("propertiesList", propertiesList);
        return modelAndView;
    }*/
    @GetMapping("/properties/sort")
    public ModelAndView listPropertiesSorted(
            @RequestParam(value="type",required = true)String sort_elt
    ){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("properties");
        PropertiesList propertiesList;
        if(sort_elt.equals("price"))
            propertiesList = propertyService.findAllPropertiesSortedByPrice();
        else if(sort_elt.equals("capacity"))
            propertiesList = propertyService.findAllPropertiesSortedByCapacity();
        else
            propertiesList = new PropertiesList();


        modelAndView.addObject("pageTitle", "All properties");
        modelAndView.addObject("propertiesList", propertiesList);
        return modelAndView;
    }
}
