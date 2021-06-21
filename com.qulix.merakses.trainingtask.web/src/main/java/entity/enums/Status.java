package entity.enums;

public enum Status {
    NOT_STARTED("�� ������"),
    IN_PROGRESS("� ��������"),
    COMPLETED("���������"),
    POSTPONED("��������");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
