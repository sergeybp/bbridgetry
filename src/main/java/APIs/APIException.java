package APIs;

public class APIException extends Exception {

    private int code;
    private String in;

    APIException(int code, String in){
        super();
        this.code = code;
        this.in = in;
    }

    @Override
    public String toString() {
        return "In: "+in+"; Code: "+code;
    }
}
