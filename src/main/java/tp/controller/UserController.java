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
import tp.model.Authority;
import tp.model.AuthorityType;
import tp.model.User;
import tp.model.UserDto;
import tp.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @RequestMapping(value={"/locataire", "/loueur"}, method = RequestMethod.GET)
    public ModelAndView getCurrentUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("loueur");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.toString();
        String type = "";

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
            type = this.getType((UserDetails) principal);
        }

        modelAndView.addObject("pageTitle", "Profil " + type);
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    private String getType(UserDetails user) {
        String type = "";
        if(user.getAuthorities().iterator().next().getAuthority().equals("ROLE_LOCATAIRE")) {
            type = "locataire";
        } else if(user.getAuthorities().iterator().next().getAuthority().equals("ROLE_LOUEUR")) {
            type = "loueur";
        }
        return type;
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


    /*
    @RequestMapping(value = "/enreg", method = RequestMethod.POST)
    public String doRegistration(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String type,
            Model model) {

        log.info(username);
        log.info(email);
        log.info(password);
        log.info(type);

        //UserDetails userExists = (UserDetails) userDetailsService.loadUserByUsername(username);
        //log.info("[*] User loaded : " + userExists.toString());

        User userToCreate = new User();

        if (type.equals("locataire")) {
            userToCreate.addAuthority(new Authority(AuthorityType.ROLE_LOCATAIRE));
        } else if (type.equals("loueur")) {
            userToCreate.addAuthority(new Authority(AuthorityType.ROLE_LOUEUR));
        }
        userToCreate.setUsername(username);
        userToCreate.setPassword(password);
        userToCreate.setEmail(email);

        userService.saveNewUser(userToCreate);

        model.addAttribute("message", "créé");
        model.addAttribute("test", "youpi");

        return "registration";
    }
     */
}
