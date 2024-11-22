package api.dashboard.insurance.system.model.enums;

public enum ExceptionEnum {
    DATA_INTEGRITY_VIOLATION("99", "Data integrity violation occurred"),
    NOT_FOUND("01", "Not Found"),
    SER_UNEXPECTED_ERROR("99", "Unexpected Error"),
    BAD_REQUEST("01","Bad Request Validation");
    ExceptionEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public final String code;
    public final String description;
}
