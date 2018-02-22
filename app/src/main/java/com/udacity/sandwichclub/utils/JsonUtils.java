package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    private static final String TAG = JsonUtils.class.getSimpleName();


    public static Sandwich parseSandwichJson(String json) {

        // If the JSON string is empty or null, then return early.

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        Log.d(TAG, "el string json no está vacio o es nulo: " + json);

        Sandwich sandwich = new Sandwich();


        try {

            // Create a JSONObject from json String
            JSONObject JsonSandwich = new JSONObject(json);

            JSONObject objectName = JsonSandwich.getJSONObject(NAME);

            // Extract the value for the key called "mainName"
            String mMainName = objectName.optString(MAIN_NAME);

            // Extract the value for the key called "alsoKnownAs"
            JSONArray mAlsoKnownAs = objectName.getJSONArray(ALSO_KNOWN_AS);
            List<String> alsoKnownAs = convertJsonArrayAsListString(mAlsoKnownAs);

            // Extract the value for the key called "placeOfOrigin"
            String mPlaceOfOrigin = JsonSandwich.optString(PLACE_OF_ORIGIN);
            Log.d(TAG, "Place of Origin is: " + mPlaceOfOrigin);

            // Extract the value for the key called "description"
            String mDescription = JsonSandwich.optString(DESCRIPTION);

            // Extract the value for the key called "image"
            String mImage = JsonSandwich.optString(IMAGE);

            // Extract the value for the key called "ingredients"
            JSONArray mIngredients = JsonSandwich.getJSONArray(INGREDIENTS);
            List<String> ingredients = convertJsonArrayAsListString(mIngredients);

            Log.d(TAG, "List String ingredients = " + ingredients);

            sandwich = new Sandwich(mMainName, alsoKnownAs, mPlaceOfOrigin, mDescription, mImage, ingredients);

        } catch (JSONException e) {
            Log.e("JsonUtils", "Problem parsing the Sandwich JSON results", e);

        }

        Log.d(TAG, "El contenido de sandwich es: " + sandwich.toString());
        return sandwich;
    }

    private static List<String> convertJsonArrayAsListString(JSONArray jsonArray) throws JSONException {

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            stringList.add(jsonArray.getString(i));
        }

        return stringList;

    }
}
