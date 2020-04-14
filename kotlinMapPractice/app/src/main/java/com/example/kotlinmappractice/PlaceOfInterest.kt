package com.example.kotlinmappractice

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception


class PlaceOfInterest(inputObject:JSONObject) {
        var placeID: String? = inputObject.getString("place_id")
        var locationObject: JSONObject =
            inputObject.getJSONObject("geometry").getJSONObject("location")
        var position: LatLng? = LatLng(
            locationObject.getDouble("lat"),
            locationObject.getDouble("lng")
        )
        var name: String? = inputObject.getString("name")
        //var rating: Double = 0.0
        var openingHours: String = inputObject.optJSONObject("opening_hours")?.optString("periods")?: "no opening times provided"

        var rating: Double? = inputObject.optDouble("rating", 0.0)
    }
