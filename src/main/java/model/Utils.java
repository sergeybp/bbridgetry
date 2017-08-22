package model;

import com.google.gson.JsonObject;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sergeybp on 20.07.17.
 */
public class Utils {

    static ConcurrentHashMap<String, JsonObject> workingMap = new ConcurrentHashMap<>();
    public static JsonObject EMPTY = new JsonObject();
    static {
        EMPTY.addProperty("EMPTY", "YES");
    }




    String generateNewId() {
        Random random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }


}
