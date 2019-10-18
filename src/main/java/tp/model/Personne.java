package tp.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@Component
@Scope(value= WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Personne {
    private int id;
    private String nom;
    @Autowired
    private ListeOperations listOpe;

    public Personne() {
    }

    public void ajouterOperation(Operation o) {
        this.listOpe.add(o);
    }

    public double dernierCalcul() {
        return this.listOpe.pop().getRes();
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public ListeOperations getListOpe() {
        return listOpe;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setListOpe(ListeOperations listOpe) {
        this.listOpe = listOpe;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", listOpe=" + listOpe +
                '}';
    }

    public List<Operation> getHistorique() {
        return this.listOpe.getLo();
    }
}
