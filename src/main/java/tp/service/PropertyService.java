package tp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import tp.model.PropertiesList;
import tp.model.Property;
import tp.repository.PropertyRepository;

import java.util.Optional;

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

    /**
     * Récupére la liste des biens filtrés par le statut
     * @param status statut séléctionné (0: Dispo / 1 : En attente de validation / 2 : Non Dispo)
     * @return propertyList la liste des propriétés
     */
    public PropertiesList findPropertiesByStatus(int status){
        return new PropertiesList(this.propertyRepository.findByStatus(status));
    }

    /***
     * Modifie le statut du bien
     * @param propertyId Id du bien a modifier
     * @param status Nouveau statut
     */
    public void updatePropertyStatus(long propertyId, int status){
        Property prop = this.propertyRepository.findById(propertyId);
        prop.setStatus(status);
        this.propertyRepository.save(prop);
    }

    /**
     * Récupère la liste des biens qui ont comme propriétaire l'utilisateur avec l'ID correspondant
     * @param userId identifiant du propriétaire du bien
     * @return liste des biens de l'utilisateur
     */
    public PropertiesList findPropertiesByUser(long userId) {
        return new PropertiesList(this.propertyRepository.findByUser(userId));
    }

    /**
     * Récupère la liste des tous les biens disponibles à la location sauf ceux possédés par l'utilisateur courant
     * @return liste des biens disponibles
     */
    public PropertiesList findAllAvailableProperties(long currentUserId) {
        return new PropertiesList(this.propertyRepository.findAllAvailable(currentUserId));
    }

    public PropertiesList findPropertiesByTenant(long tenantId) {
        return new PropertiesList(this.propertyRepository.findByTenant(tenantId));
    }

    public boolean rentProperty(long propertyId, long userId) {
        Property prop = this.propertyRepository.findById(propertyId);
        if (prop.getStatus() == 1 || prop.getStatus() == 2) {
            return false;
        } else {
            prop.setStatus(1);
            prop.setTenantId(userId);
            this.propertyRepository.save(prop);
            return true;
        }
    }

    public void cancelRenting(long propertyId) {
        Property prop = this.propertyRepository.findById(propertyId);
        prop.setStatus(0);
        prop.setTenantId(-1);
        this.propertyRepository.save(prop);
    }
}
