package com.qulix.losevsa.trainingtask.web.entity.enums;

/**
 * The enum that represents status of the {@link com.qulix.losevsa.trainingtask.web.entity.Task}
 */
public enum Status {
    /**
     * Not started status.
     */
    NOT_STARTED("\u041d\u0435 \u043d\u0430\u0447\u0430\u0442\u0430"),
    /**
     * In progress status.
     */
    IN_PROGRESS("\u0412 \u043F\u0440\u043E\u0446\u0435\u0441\u0441\u0435"),
    /**
     * Completed status.
     */
    COMPLETED("\u0417\u0430\u0432\u0435\u0440\u0448\u0435\u043D\u0430"),
    /**
     * Postponed status.
     */
    POSTPONED("\u041E\u0442\u043B\u043E\u0436\u0435\u043D\u0430");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
