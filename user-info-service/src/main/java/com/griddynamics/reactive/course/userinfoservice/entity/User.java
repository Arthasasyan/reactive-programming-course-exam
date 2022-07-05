package com.griddynamics.reactive.course.userinfoservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("users")
public class User {
    private String _id;
    private String name;
    private String phone;

    public User(String _id, String name, String phone) {
        this._id = _id;
        this.name = name;
        this.phone = phone;
    }

    @Id
    @Field("_id")
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    @Field("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Field("phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
