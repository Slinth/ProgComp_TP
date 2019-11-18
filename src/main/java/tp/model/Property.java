package tp.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
public class Property {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String type;
    private Double price;
    private String address;
    private int capacity;
    private int status;// 0 : dispo - 1 : en att - 2 : occupé
    private long userId;
    private long tenantId;

    public Property() {}

    public Property(String type, Double price, String address, int capacity, int status, long userId) {
        this.type = type;
        this.price = price;
        this.address = address;
        this.capacity = capacity;
        this.status = status;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", address='" + address + '\'' +
                ", capacity=" + capacity +
                ", status=" + status +
                ", userId=" + userId +
                '}';
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }
}
