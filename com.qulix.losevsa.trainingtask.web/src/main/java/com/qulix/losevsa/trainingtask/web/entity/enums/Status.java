package com.qulix.losevsa.trainingtask.web.entity.enums;

/**
 * The enum that represents status of the {@link com.qulix.losevsa.trainingtask.web.entity.Task}
 */
public enum Status {
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

    private final String status;

    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
