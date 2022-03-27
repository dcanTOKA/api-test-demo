package Api.helper.Constants;

public enum Elements {
    OOBEYA_USERNAME("xpath","*//input[@formcontrolname='username']"),
    OOBEYA_PASSWORD("css","div input[formcontrolname='password']"),
    OOBEYA_SIGN_IN_BUTTON("xpath","*//button//span[text()=' Sign In ']"),
    OOBEYA_DASHBOARD_TITLE("css","div>h2[class='content-header__title']");


    private String key,value;

    Elements(String key , String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
