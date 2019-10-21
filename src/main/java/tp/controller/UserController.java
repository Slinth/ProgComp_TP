package tp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import tp.model.User;
import tp.service.UserDetailsServiceImpl;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    UserDetailsServiceImpl userService;

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

    @PostMapping("/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        UserDetails userExists = (UserDetails) userService.loadUserByUsername(user.getUsername());
        if (userExists != null) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            System.out.println("# ERREUR : + " + bindingResult);
            modelAndView.addObject("systemMessage", "There is already a user registered with the username provided");
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("systemMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.addObject("logged", true);
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

}
