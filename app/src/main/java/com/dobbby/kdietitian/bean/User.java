package com.dobbby.kdietitian.bean;

import java.util.Date;

/**
 * Created by Dobbby on 2017/6/22.
 * <p>
 * When signing up, clients should provide variables below:
 * {@link User#username}
 * {@link User#plainPassword}
 * {@link User#telephoneNumber}
 * {@link User#role}
 */
public class User {
    private int id;
    private String username;
    private String salt;
    private String plainPassword;
    private String encryptedPassword;
    private String telephoneNumber;
    private String nickname;
    private Role role;
    private Gender gender;
    private Date birthday;
    private Date creationDate;
    private Date modificationDate;

    public User() {
        role = Role.normal;
        gender = Gender.unknown;
    }

    public enum Role {
        normal, dietitian, dealer,
    }

    public enum Gender {
        male, female, unknown,
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
}
