package com.sim.backend.client.entity;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class Client {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final UUID id;
    private ClientType type;
    private String documentNumber;
    private String name;
    private String businessName;
    private String address;
    private String department;
    private String province;
    private String district;
    private String email;
    private String phone;
    private boolean active;

    private Client(
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
        this.id = Objects.requireNonNull(
                id,
                "El identificador del cliente es obligatorio"
        );

        this.type = Objects.requireNonNull(
                type,
                "El tipo de cliente es obligatorio"
        );

        this.documentNumber = requireText(
                documentNumber,
                "El número de documento es obligatorio"
        );

        this.name = normalize(name);
        this.businessName = normalize(businessName);
        this.address = normalize(address);
        this.department = normalize(department);
        this.province = normalize(province);
        this.district = normalize(district);
        this.email = normalize(email);
        this.phone = normalize(phone);
        this.active = active;

        validate();
    }

    public static Client create(
            ClientType type,
            String documentNumber,
            String name,
            String businessName,
            String address,
            String department,
            String province,
            String district,
            String email,
            String phone
    ) {
        return new Client(
                UUID.randomUUID(),
                type,
                documentNumber,
                name,
                businessName,
                address,
                department,
                province,
                district,
                email,
                phone,
                true
        );
    }

    public static Client restore(
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
        return new Client(
                id,
                type,
                documentNumber,
                name,
                businessName,
                address,
                department,
                province,
                district,
                email,
                phone,
                active
        );
    }

    public void updateIdentityData(
            ClientType type,
            String documentNumber,
            String name,
            String businessName
    ) {
        this.type = Objects.requireNonNull(
                type,
                "El tipo de cliente es obligatorio"
        );

        this.documentNumber = requireText(
                documentNumber,
                "El número de documento es obligatorio"
        );

        this.name = normalize(name);
        this.businessName = normalize(businessName);

        validateDocument();
        validateName();
    }

    public void updateContactInfo(
            String address,
            String department,
            String province,
            String district,
            String email,
            String phone
    ) {
        this.address = normalize(address);
        this.department = normalize(department);
        this.province = normalize(province);
        this.district = normalize(district);
        this.email = normalize(email);
        this.phone = normalize(phone);

        validateEmail();
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    private void validate() {
        validateDocument();
        validateName();
        validateEmail();
    }

    private void validateDocument() {
        if (!documentNumber.matches("\\d+")) {
            throw new IllegalArgumentException(
                    "El número de documento solo debe contener números"
            );
        }

        if (type == ClientType.PERSONA
                && documentNumber.length() != 8) {
            throw new IllegalArgumentException(
                    "El DNI debe tener 8 dígitos"
            );
        }

        if (type == ClientType.EMPRESA
                && documentNumber.length() != 11) {
            throw new IllegalArgumentException(
                    "El RUC debe tener 11 dígitos"
            );
        }
    }

    private void validateName() {
        if (type == ClientType.PERSONA && name.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del cliente es obligatorio"
            );
        }

        if (type == ClientType.EMPRESA && businessName.isBlank()) {
            throw new IllegalArgumentException(
                    "La razón social es obligatoria"
            );
        }
    }

    private void validateEmail() {
        if (!email.isBlank()
                && !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException(
                    "El correo electrónico no tiene un formato válido"
            );
        }
    }

    private static String requireText(
            String value,
            String message
    ) {
        String normalizedValue = normalize(value);

        if (normalizedValue.isBlank()) {
            throw new IllegalArgumentException(message);
        }

        return normalizedValue;
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim();
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