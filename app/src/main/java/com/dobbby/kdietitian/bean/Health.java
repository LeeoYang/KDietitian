package com.dobbby.kdietitian.bean;

/**
 * Created by Dobbby on 2017/6/26.
 */
public class Health {
    private String id;
    private String username;
    private float height;
    private float weight;

    public String getId() {
        return id;
    }

    public Health setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Health setUsername(String username) {
        this.username = username;
        return this;
    }

    public float getHeight() {
        return height;
    }

    public Health setHeight(float height) {
        this.height = height;
        return this;
    }

    public float getWeight() {
        return weight;
    }

    public Health setWeight(float weight) {
        this.weight = weight;
        return this;
    }
}
