package com.smartfarming.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "AgroDealer")
public class AgroDealer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Dealer_ID")
    private Long dealerId;
    
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Column(name = "Name", nullable = false)
    private String name;
    
    @NotBlank(message = "Shop location is required")
    @Size(max = 150, message = "Shop location must not exceed 150 characters")
    @Column(name = "Shop_Location", nullable = false)
    private String shopLocation;
    
    @NotBlank(message = "Phone number is required")
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    @Column(name = "Phone", nullable = false)
    private String phone;
    
    // Constructors
    public AgroDealer() {}
    
    public AgroDealer(String name, String shopLocation, String phone) {
        this.name = name;
        this.shopLocation = shopLocation;
        this.phone = phone;
    }
    
    // Getters and Setters
    public Long getDealerId() {
        return dealerId;
    }
    
    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getShopLocation() {
        return shopLocation;
    }
    
    public void setShopLocation(String shopLocation) {
        this.shopLocation = shopLocation;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
