package tp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import tp.model.PropertiesList;
import tp.model.Property;
import tp.service.PropertyService;
import tp.service.UserService;

@Controller
public class PropertyController {
    @Autowired
    PropertyService propertyService;

    @Autowired
    UserService userService;

    @GetMapping("/properties")
    public ModelAndView listAllProperties() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("properties");

        PropertiesList propertiesList = propertyService.findAllAvailableProperties();

        modelAndView.addObject("pageTitle", "Tous les biens");
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

    @GetMapping("/properties/book")
    public ModelAndView bookProperty(
            @RequestParam(value="propertyId",required=true)String propertyId
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("booking");

        boolean changed = propertyService.updatePropertyStatus(Long.parseLong(propertyId),1);
        Property p = propertyService.findPropertyById(Long.parseLong(propertyId));
        modelAndView.addObject("pageTitle", "Book property");
        modelAndView.addObject("property", p);
        modelAndView.addObject("changed",changed);
        return modelAndView;
    }

    @GetMapping("properties/book/validation")
    public ModelAndView bookingValidation(
            @RequestParam(value="userId",required=true)String userId
    ){


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("booking-validation");

//        PropertiesList propertiesList = propertyService.findPropertiesByStatus(1);
        PropertiesList propertiesList = propertyService.findPropertiesByUser(Long.parseLong(userId));

        modelAndView.addObject("pageTitle", "All properties");
        modelAndView.addObject("propertiesList", propertiesList);
        return modelAndView;
    }

    @GetMapping("/validateRenting")
    public String validateRenting(@RequestParam(value = "propertyId", required = true) String propertyId, Model model) {
        propertyService.updatePropertyStatus(Long.parseLong(propertyId), 2);
        model.addAttribute("validationMessage", "La location a correctement été validée !");
        model.addAttribute("property", propertyService.findPropertyById(Long.parseLong(propertyId)));
        return "validation-view";
    }

    @GetMapping("/invalidateRenting")
    public String invalidateRenting(@RequestParam(value = "propertyId", required = true) String propertyId, Model model) {
        propertyService.updatePropertyStatus(Long.parseLong(propertyId), 0);
        model.addAttribute("validationMessage", "La location a correctement été refusée !");
        return "validation-view";
    }

    @GetMapping("/cancelRenting")
    public String cancelRenting(@RequestParam(value = "propertyId", required = true) String propertyId, Model model) {
        propertyService.updatePropertyStatus(Long.parseLong(propertyId), 0);
        model.addAttribute("validationMessage", "La location a correctement été annulée !");
        return "validation-view";
    }

}
