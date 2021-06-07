package com.pjm.painttest.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonUtils {

    public static <T> T getObject(String jsonString, Class<T> clz){
        try{
            return new Gson().fromJson(jsonString, clz);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> getListObject(String jsonString, Class<T> clz){
        try {
            List<T> t = new Gson().fromJson(jsonString, TypeToken.getParameterized(ArrayList.class, (Type) clz).getType());
            return t;
        }catch (Exception e){
            return null;
        }
    }
    public static <T> T getDataObject(String jsonString, Class<T> clz){
        try{
            JsonObject jsonObject =  new JsonParser().parse(jsonString).getAsJsonObject();
            JsonElement element = jsonObject.get("data");
            if(element != null){
                return new Gson().fromJson(element, clz);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getDataObject(String jsonString){
        try{
            JsonObject jsonObject =  new JsonParser().parse(jsonString).getAsJsonObject();
            JsonElement element = jsonObject.get("data");
            return element.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getDataResultString(String jsonString, String key){
        String result = "";
        try{
            JsonObject jsonObject =  new JsonParser().parse(jsonString).getAsJsonObject();
            result = jsonObject.get("data").getAsJsonObject().get(key).getAsString();
        }catch (Exception e){
            //e.printStackTrace();
        }
        return result;
    }

    public static int getDataResultInt(String jsonString, String key){
        int result = 0;
        try{
            JsonObject jsonObject =  new JsonParser().parse(jsonString).getAsJsonObject();
            result = jsonObject.get("data").getAsJsonObject().get(key).getAsInt();
        }catch (Exception e){
            //e.printStackTrace();
        }
        return result;
    }

    public static <T> String toJsonString(T t) {
        return new Gson().toJson(t);
    }
}
