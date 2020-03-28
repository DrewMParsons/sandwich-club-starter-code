package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonUtils {
    public static final String NAME = "name";
    public static final String MAIN_NAME = "mainName";
    public static final String AKA = "alsoKnownAs";
    public static final String ORIGIN = "placeOfOrigin";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String INGREDIENTS = "ingredients";



    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sandwichJSON = new JSONObject(json);
            JSONObject name = sandwichJSON.getJSONObject(NAME);
            String mainName = name.optString(MAIN_NAME);
            JSONArray aka = name.getJSONArray(AKA);
            ArrayList<String> akaList = createListFromJSON(aka);
            String placeOfOrigin = sandwichJSON.optString(ORIGIN);
            String description = sandwichJSON.optString(DESCRIPTION);
            String image = sandwichJSON.optString(IMAGE);
            JSONArray ing = sandwichJSON.getJSONArray(INGREDIENTS);
            ArrayList<String>ingredients = createListFromJSON(ing);
            return new Sandwich(mainName,akaList,placeOfOrigin,description,image,ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ArrayList<String> createListFromJSON(JSONArray aka) throws JSONException
    {
        ArrayList<String> list= new ArrayList<>();
        if(aka !=null){
            int len = aka.length();
            for (int i = 0; i < len; i++) {
                list.add(aka.get(i).toString());
            }
        }
        return list;
    }
}
