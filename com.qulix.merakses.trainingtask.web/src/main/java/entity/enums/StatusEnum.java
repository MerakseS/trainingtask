package entity.enums;

public enum StatusEnum {
    NOT_STARTED("Не начата"),
    IN_PROGRESS("В процессе"),
    COMPLETED("Завершена"),
    POSTPONED("Отложена");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
