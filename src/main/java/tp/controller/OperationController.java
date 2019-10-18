package tp.controller;

import tp.model.Operation;
import tp.model.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class OperationController {
    @Autowired
    Personne p;

    @GetMapping("/operations")
    public String operations(Model model) {
        return getModel(model);
    }

    @RequestMapping(value = "/calculer", method = RequestMethod.POST)
    public String submit(@ModelAttribute Operation ope, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "error";
        }

        ope.resoudre();

        p.ajouterOperation(ope);

        return getModel(model);
    }

    public String getModel(Model model) {
        if(p.getHistorique().isEmpty()) {
            Operation o1 = new Operation(53.5, 48.2, "+");
            Operation o2 = new Operation(4, 8, "*");

            p.ajouterOperation(o1);
            p.ajouterOperation(o2);
        }
        model.addAttribute("nom", p.getNom());
        model.addAttribute("dernierCalcul", p.dernierCalcul());
        model.addAttribute("historique", p.getHistorique());
        model.addAttribute("operation", new Operation());

        return "operations";
    }

}