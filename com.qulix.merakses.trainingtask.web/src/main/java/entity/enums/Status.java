package entity.enums;

public enum Status {
    NOT_STARTED("\u041d\u0435 \u043d\u0430\u0447\u0430\u0442\u0430"),
    IN_PROGRESS("\u0412 \u043F\u0440\u043E\u0446\u0435\u0441\u0441\u0435"),
    COMPLETED("\u0417\u0430\u0432\u0435\u0440\u0448\u0435\u043D\u0430"),
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
