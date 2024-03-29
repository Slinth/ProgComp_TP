package tp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import tp.model.PropertiesList;
import tp.model.Property;
import tp.model.User;
import tp.service.*;

@Controller
public class PropertyController {
    @Autowired
    PropertyService propertyService;

    @Autowired
    UserService userService;

    @Autowired
    ConversionAwsService conversionAwsService;

    @Autowired
    KafkaQueueService kafkaQueueService;

    @GetMapping("/properties")
    public ModelAndView listAllProperties() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("properties");

        User user = getCurrentUser();

        PropertiesList propertiesList = propertyService.findAllAvailableProperties(user.getId());

        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("propertiesList", propertiesList);
        return modelAndView;
    }

    @GetMapping("/properties/filter")
    public ModelAndView listPropertiesFilter(
            @RequestParam(value="name")String name,
            @RequestParam(value="value")String value
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

        modelAndView.addObject("currentUser", getCurrentUser());
        modelAndView.addObject("propertiesList", propertiesList);
        return modelAndView;
    }

    @GetMapping("/properties/book")
    public ModelAndView bookProperty(
            @RequestParam(value="propertyId")String propertyId
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("booking");

        User user = getCurrentUser();

        boolean changed = propertyService.rentProperty(Long.parseLong(propertyId), user.getId());

        Property property = propertyService.findPropertyById(Long.parseLong(propertyId));
        kafkaQueueService.addToQueue(property.getPrice().toString());

        modelAndView.addObject("property", property);
        modelAndView.addObject("validated", changed);
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
        propertyService.cancelRenting(Long.parseLong(propertyId));
        model.addAttribute("validationMessage", "La location a correctement été annulée !");
        return "validation-view";
    }

    @GetMapping("/kafka")
    public ModelAndView displayKafkaQueue() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("kafka-view");

        modelAndView.addObject("dataPairs", KafkaQueueService.getRetrievedDatas());
        return modelAndView;
    }

    @GetMapping("/awsLambda")
    public ModelAndView displayPropertiesWithConvertedPrices() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("awsLambda-view");

        // Id = -1 pour afficher tous les enregistrements
        PropertiesList propertiesList = propertyService.findAllAvailableProperties(-1);

        modelAndView.addObject("propertiesList", propertiesList);
        modelAndView.addObject("conversionService", conversionAwsService);
        return modelAndView;
    }

    public User getCurrentUser() {
        User currentUser = new User();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            currentUser = ((UserDetailsImpl) principal).getUserDetails();
        }

        return currentUser;
    }

}
