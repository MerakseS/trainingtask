package com.qulix.losevsa.trainingtask.web.entity;

import java.io.Serializable;
import static java.lang.String.format;

/**
 * The type Employee.
 */
public class Employee implements Serializable {

    /**
     * Identifier of the employee.
     */
    private long id;

    /**
     * First name of the employee.
     */
    private String firstName;

    /**
     * Surname of the employee.
     */
    private String surname;

    /**
     * Patronymic of the employee.
     */
    private String patronymic;

    /**
     * Position in company of the employee.
     */
    private String position;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return format("%s %s %s %s", position, firstName, surname, patronymic);
    }
}
