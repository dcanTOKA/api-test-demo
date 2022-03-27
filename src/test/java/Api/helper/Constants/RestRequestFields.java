package Api.helper.Constants;

public enum RestRequestFields {

    CONTENT_TYPE("application/json");

    private String value;

    RestRequestFields(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
