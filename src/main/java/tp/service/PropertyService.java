package tp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import tp.model.PropertiesList;
import tp.model.Property;
import tp.repository.PropertyRepository;

@Service("propertyService")
public class PropertyService {

    private PropertyRepository propertyRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /**
     * Récupère le bien d'id passé en paramètre depuis la BD
     * @param id identifiant du bien
     * @return le bien avec l'id correspondant
     */
    public Property findPropertyById(long id) {
        return this.propertyRepository.findById(id);
    }

    /**
     * Récupère le bien d'adresse passée en paramètre depuis la BD
     * @param address adresse du bien
     * @return le bien avec l'adresse correpondante
     */
    public Property findPropertyByAddress(String address) {
        return this.propertyRepository.findByAddress(address);
    }

    /**
     * Récupère une liste de biens de capacité égale au paramètre
     * @param capacity capacité du/des bien(s)
     * @return la liste de biens de capacité correspondante
     */
    public PropertiesList findPropertiesByCapacity(int capacity) {
        return new PropertiesList(this.propertyRepository.findByCapacity(capacity));
    }

    /**
     * Récupère une liste de biens de prix maximum égal au paramètre
     * @param price prix maximum du bien recherché
     * @return la liste de biens de prix max correspondant
     */
    public PropertiesList findPropertiesByMaxPrice(Double price) {
        return new PropertiesList(this.propertyRepository.findByMaxPrice(price));
    }

    /**
     * Récupère tous les biens triés par prix croissants
     * @return la liste de biens triée
     */
    public PropertiesList findAllProperties() {
        return new PropertiesList(this.propertyRepository.findAll());
    }

    /**
     * Récupère tous les biens triés par prix croissants
     * @return la liste de biens triée
     */
    public PropertiesList findAllPropertiesSortedByPriceASC() {
        return new PropertiesList(this.propertyRepository.findAllSorted(JpaSort.unsafe(Sort.Direction.ASC,"price")));
    }
    /**
     * Récupère tous les biens triés par prix décroissants
     * @return la liste de biens triée
     */
    public PropertiesList findAllPropertiesSortedByPriceDESC() {
        return new PropertiesList(this.propertyRepository.findAllSorted(JpaSort.unsafe(Sort.Direction.DESC,"price")));
    }
    /**
     * Récupère tous les biens triés par capacité croissantes
     * @return la liste de biens triée
     */
    public PropertiesList findAllPropertiesSortedByCapacityASC() {
        return new PropertiesList(this.propertyRepository.findAllSorted(JpaSort.unsafe(Sort.Direction.ASC,"capacity")));
    }
    /**
     * Récupère tous les biens triés par capacité croissantes
     * @return la liste de biens triée
     */
    public PropertiesList findAllPropertiesSortedByCapacityDESC() {
        return new PropertiesList(this.propertyRepository.findAllSorted(JpaSort.unsafe(Sort.Direction.DESC,"capacity")));
    }
    /**
     * Enregistre une propriété dans la BD
     * @param property la propriété à enregistrer
     */
    public void saveProperty(Property property) {
        this.propertyRepository.save(property);
    }

    /**
     * Récupéra la liste des biens en filtrés par le type
     * @param type type sélectionné
     * @return propertyList la liste des propriétés.
     */
    public PropertiesList findPropertiesByType(String type){
        return new PropertiesList(this.propertyRepository.findByType(type));
    }
}
