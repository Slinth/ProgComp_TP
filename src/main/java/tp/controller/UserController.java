package tp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import tp.model.*;
import tp.service.PropertyService;
import tp.service.UserDetailsImpl;
import tp.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PropertyService propertyService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/profil")
    public ModelAndView getProfil() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profil");

        PropertiesList propertiesList = new PropertiesList();

        User user = getCurrentUser();
        if (user.getType().equals("loueur")) {
            propertiesList = propertyService.findPropertiesByUser(user.getId());
        } else if (user.getType().equals("locataire")) {
            log.info("DANS LE IF " + user.getType() + user.getId());
            propertiesList = propertyService.findPropertiesByTenant(user.getId());
        }

        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("pageTitle", "Profil " + user.getType());
        modelAndView.addObject("properties", propertiesList.getPropertyList());
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

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        UserDto user = new UserDto();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public RedirectView register(@Valid UserDto user, Model model, RedirectAttributes attributes) {
        //User userExists = userService.findUserByEmail(user.getUsername());
        log.info(user.getUsername());
        log.info(user.getPassword());
        log.info(user.getEmail());
        log.info(user.getType());

        User userToCreate = new User();
        userToCreate.setUsername(user.getUsername());
        userToCreate.setEmail(user.getEmail());
        userToCreate.setPassword(user.getPassword());

        if (user.getType().equals("locataire")) {
            userToCreate.addAuthority(new Authority(AuthorityType.ROLE_LOCATAIRE));
        } else if (user.getType().equals("loueur")) {
            userToCreate.addAuthority(new Authority(AuthorityType.ROLE_LOUEUR));
        }

        userService.saveNewUser(userToCreate);
        attributes.addFlashAttribute("flashAttribute", "User has been registered successfully");
        attributes.addFlashAttribute("success", true);
        return new RedirectView("login");
    }

    @GetMapping("/login")
    public ModelAndView redirection (ModelMap model, @ModelAttribute("flashAttribute") Object flashAttribute, @ModelAttribute("success") Object successAttribute) {
        model.addAttribute("systemMessage", flashAttribute);
        model.addAttribute("success", successAttribute);
        return new ModelAndView("login", model);
    }
}
