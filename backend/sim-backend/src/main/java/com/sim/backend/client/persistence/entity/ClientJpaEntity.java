package com.sim.backend.client.persistence.entity;

import com.sim.backend.client.entity.ClientType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "clients",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_clients_document_number",
                        columnNames = "document_number"
                )
        }
)
public class ClientJpaEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ClientType type;

    @Column(
            name = "document_number",
            nullable = false,
            length = 11
    )
    private String documentNumber;

    @Column(length = 150)
    private String name;

    @Column(name = "business_name", length = 200)
    private String businessName;

    @Column(length = 250)
    private String address;

    @Column(length = 100)
    private String department;

    @Column(length = 100)
    private String province;

    @Column(length = 100)
    private String district;

    @Column(length = 150)
    private String email;

    @Column(length = 30)
    private String phone;

    @Column(nullable = false)
    private boolean active;

    protected ClientJpaEntity(){
        //Constructor requerido por JPA
    }

    public ClientJpaEntity(
            UUID id,
            ClientType type,
            String documentNumber,
            String name,
            String businessName,
            String address,
            String department,
            String province,
            String district,
            String email,
            String phone,
            boolean active
    ) {
        this.id = id;
        this.type = type;
        this.documentNumber = documentNumber;
        this.name = name;
        this.businessName = businessName;
        this.address = address;
        this.department = department;
        this.province = province;
        this.district = district;
        this.email = email;
        this.phone = phone;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public ClientType getType() {
        return type;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getName() {
        return name;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getAddress() {
        return address;
    }

    public String getDepartment() {
        return department;
    }

    public String getProvince() {
        return province;
    }

    public String getDistrict() {
        return district;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isActive() {
        return active;
    }
}





