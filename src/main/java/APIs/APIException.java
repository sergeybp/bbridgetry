package APIs;

public class APIException extends Exception {

    public int code;

    public APIException(int code){
        super();
        this.code = code;
    }

    @Override
    public String toString() {
        return ""+code;
    }
}
