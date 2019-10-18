package tp.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Property findPropertyById(long id) {
        return this.propertyRepository.findById(id);
    }

    public Property findPropertyByAddress(String address) {
        return this.propertyRepository.findByAddress(address);
    }

    public PropertiesList findPropertiesByCapacity(int capacity) {
        return new PropertiesList(this.propertyRepository.findByCapacity(capacity));
    }

    public PropertiesList findPropertiesByMaxPrice(Double price) {
        return new PropertiesList(this.propertyRepository.findByMaxPrice(price));
    }

    public PropertiesList findAllPropertiesSortedByPrice(Double price) {
        return new PropertiesList(this.propertyRepository.findAllSorted(JpaSort.unsafe("price")));
    }

    public PropertiesList findAllPropertiesSortedByCapacity(int capacity) {
        return new PropertiesList(this.propertyRepository.findAllSorted(JpaSort.unsafe("capacity")));
    }

    public void saveProperty(Property property) {
        this.propertyRepository.save(property);
    }
}
