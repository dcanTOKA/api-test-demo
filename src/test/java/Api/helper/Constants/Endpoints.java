package Api.helper.Constants;

public enum Endpoints {

    GET_USER("get","/user/{username}"),
    CREATE_USER("post","/user"),
    CREATE_USERS_WITH_ARRAY("post","/user/createWithArray"),
    UPDATE_USER("put", "/user/{username}"),
    DELETE_USER("delete","/user/{username}"),
    LOGIN("get","/user/login"),
    LOGOUT("get","/user/logout");


    private String method, endpoint;

    Endpoints(String method, String endpoint){
        this.method = method;
        this.endpoint = endpoint;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
