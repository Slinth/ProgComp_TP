package tp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import tp.model.PropertiesList;
import tp.service.PropertyService;

@Controller
public class PropertyController {
    @Autowired
    PropertyService propertyService;

    @GetMapping("/properties")
    public ModelAndView listAllProperties() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("properties");

        PropertiesList propertiesList = propertyService.findAllProperties();
        modelAndView.addObject("propertiesList", propertiesList);
        return modelAndView;
    }
}
