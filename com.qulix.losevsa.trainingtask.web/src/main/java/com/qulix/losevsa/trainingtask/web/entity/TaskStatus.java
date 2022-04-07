package com.qulix.losevsa.trainingtask.web.entity;

/**
 * The enum that represents status of the {@link Task}
 */
public enum TaskStatus {

    /**
     * Not started status.
     */
    NOT_STARTED("Не начата"),

    /**
     * In progress status.
     */
    IN_PROGRESS("В процессе"),

    /**
     * Completed status.
     */
    COMPLETED("Завершена"),

    /**
     * Postponed status.
     */
    POSTPONED("Отложена");

    private final String name;

    TaskStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
