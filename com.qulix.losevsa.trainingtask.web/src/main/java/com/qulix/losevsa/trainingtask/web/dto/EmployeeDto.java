package com.qulix.losevsa.trainingtask.web.dto;

import com.qulix.losevsa.trainingtask.web.entity.Employee;

/**
 * The Data transfer object of the {@link Employee}.
 */
public class EmployeeDto {

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
}
