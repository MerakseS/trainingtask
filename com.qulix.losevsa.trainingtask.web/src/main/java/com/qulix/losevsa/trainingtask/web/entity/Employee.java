package com.qulix.losevsa.trainingtask.web.entity;

import java.io.Serializable;

/**
 * The type Employee.
 */
public class Employee implements Serializable {

    /**
     * Identifier of the employee
     */
    private long id;
    /**
     * First name of the employee
     */
    private String firstName;
    /**
     * Surname of the employee
     */
    private String surName;
    /**
     * Patronymic of the employee
     */
    private String patronymic;
    /**
     * Position in company of the employee
     */
    private String position;

    /**
     * Gets {@link Employee#id}.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets {@link Employee#id}.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets {@link Employee#firstName}.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets {@link Employee#firstName}.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets {@link Employee#surName}.
     *
     * @return the sur name
     */
    public String getSurName() {
        return surName;
    }

    /**
     * Sets {@link Employee#surName}.
     *
     * @param surName the sur name
     */
    public void setSurName(String surName) {
        this.surName = surName;
    }

    /**
     * Gets {@link Employee#patronymic}.
     *
     * @return the patronymic
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * Sets {@link Employee#patronymic}.
     *
     * @param patronymic the patronymic
     */
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    /**
     * Gets {@link Employee#position}.
     *
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets {@link Employee#position}.
     *
     * @param position the position
     */
    public void setPosition(String position) {
        this.position = position;
    }
}
