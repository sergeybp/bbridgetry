package APIs;

public class APIException extends Exception {

    public int code;
    public String in;

    public APIException(int code, String in){
        super();
        this.code = code;
        this.in = in;
    }

    @Override
    public String toString() {
        return "In: "+in+"; Code: "+code;
    }
}
