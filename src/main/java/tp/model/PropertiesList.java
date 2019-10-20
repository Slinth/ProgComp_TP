package tp.model;

import java.util.ArrayList;
import java.util.List;

public class PropertiesList {
    private List<Property> propertyList = new ArrayList<>();

    public PropertiesList() {}

    public PropertiesList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }

    public List<Property> getPropertyList() {
        return this.propertyList;
    }

    @Override
    public String toString() {
        return "PropertiesList{" +
                "propertyList=" + propertyList +
                '}';
    }

    public boolean isEmpty() {
        return this.propertyList.isEmpty();
    }
}
