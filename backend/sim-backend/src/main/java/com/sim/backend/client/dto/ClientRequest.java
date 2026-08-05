package com.sim.backend.client.dto;

import com.sim.backend.client.entity.ClientType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ClientRequest {

    @NotNull(message = "El tipo de cliente es obligatorio")
    private ClientType type;

    @NotBlank(message = "El numero de documento es obligatorio")
    @Size(max = 20, message = "El documento no puede superar los 20 caracteres")
    private String documentNumber;

    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
    private String name;

    @Size(max = 200)
    private String businessName;

    @Size(max = 200)
    private String address;

    @Size(max = 100)
    private String department;

    @Size(max = 100)
    private String province;

    @Size(max = 100)
    private String district;

    @Email(message = "Correo electronico invalido")
    @Size(max = 150)
    private String email;

    @Size(max = 30)
    private String phone;

    public ClientRequest(){}

    public ClientType getType(){

        return type;
    }
    public void setType(ClientType type){
        this.type = type;
    }
    public String getDocumentNumber(){
        return documentNumber;
    }
    public void setDocumentNumber(String documentNumber){
        this.documentNumber = documentNumber;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
