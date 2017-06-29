package com.dobbby.kdietitian.bean;

/**
 * Created by Dobbby on 2017/6/26.
 */
public class DietitianAuthQuery {
    private String id;
    private String name;
    private String email;
    private String hospital;
    private String office;
    private String title;

    public String getId() {
        return id;
    }

    public DietitianAuthQuery setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DietitianAuthQuery setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public DietitianAuthQuery setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getHospital() {
        return hospital;
    }

    public DietitianAuthQuery setHospital(String hospital) {
        this.hospital = hospital;
        return this;
    }

    public String getOffice() {
        return office;
    }

    public DietitianAuthQuery setOffice(String office) {
        this.office = office;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public DietitianAuthQuery setTitle(String title) {
        this.title = title;
        return this;
    }
}
