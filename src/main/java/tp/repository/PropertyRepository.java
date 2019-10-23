package tp.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tp.model.Property;

import java.util.List;

public interface PropertyRepository extends CrudRepository<Property, Long> {

    Property findById(long id);
    Property findByAddress(String address);

    List<Property> findByCapacity(int capacity);
    List<Property> findAll();
    List<Property> findByStatus(int status);


    @Query("SELECT p FROM Property p")
    List<Property> findAllSorted(Sort sort);

    @Query("SELECT p FROM Property p WHERE p.price < ?1")
    List<Property> findByMaxPrice(Double price);

    @Query("SELECT p FROM Property p WHERE p.type = ?1")
    List<Property> findByType(String type);
}
