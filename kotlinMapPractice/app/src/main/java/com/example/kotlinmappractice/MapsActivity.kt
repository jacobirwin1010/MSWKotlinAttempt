package com.example.kotlinmappractice

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.SphericalUtil
import org.json.JSONObject
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var mMap: GoogleMap

    private  var searchTerm:kotlin.String = "surf" //initialised to surf, can be changed
    private val searchType = "lodging"
    private var cameraPosition: LatLng? = null
    private var currentReturnedPlaces: ArrayList<PlaceOfInterest>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        val userLocation = LatLng(50.41563, -5.07521)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13.5f))

        val returnedMapPoints = getLocalPlaces(userLocation, 1500, "surf")
        mMap.clear()
        writeMarkersToMap(returnedMapPoints)
    }

//    fun WriteMarkersToMap(var ArrayList<PlaceOfInterest>)
    fun getLocalPlaces(userPosition: LatLng, radiusMeters: Int, nameSearchString: String): ArrayList<PlaceOfInterest> {
    cameraPosition = userPosition
    val placesSearchStr =
        "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                "json" +
                "?location=" + userPosition.latitude + "," + userPosition.longitude +
                "&fields=place_id,geometry,name,opening_hours,rating" +
                "&radius=" + radiusMeters +
                "&type=" + searchType +
                "&keyword=lodge," + nameSearchString +
                "&key=" + getString(R.string.api_key)
    var jsonResult = JSONObject()
    var stringResult: String? = null
    val listResults = ArrayList<PlaceOfInterest>()
    try {
        stringResult = PlaceFinder().execute(placesSearchStr).get()
        if (stringResult != null) {
            jsonResult = JSONObject(stringResult)
        }
    } catch (e: Exception) {
        Log.e("MapsActivity", e.message)
    }
    if (jsonResult.length() > 0) {
        try {
            val results = jsonResult.getJSONArray("results")
            for (i in 0 until results.length()) {
                val test = results.getJSONObject(i).toString()
                listResults.add(PlaceOfInterest(results.getJSONObject(i)))
            }
        } catch (e: Exception) {
            Log.e("MapsActivity", e.message)
        }
    }
    currentReturnedPlaces = listResults
    return listResults
}

    fun writeMarkersToMap(placesOfInterest: ArrayList<PlaceOfInterest>): ArrayList<Marker>? {
        var listOfAddedMarkers = ArrayList<Marker>()

        if (placesOfInterest.size > 0) {
            val iterator: Iterator<PlaceOfInterest> = placesOfInterest.iterator()
            while (iterator.hasNext()) {
                val current = iterator.next()
                val markerToWrite = MarkerOptions()
                    .position(current.position!!)
                    .title(current.name)
                    .snippet("Rating: " + current.rating.toString() + "|" + current.openingHours)
                listOfAddedMarkers.add(mMap.addMarker(markerToWrite))
            }
        }
        return listOfAddedMarkers
    }

    fun refreshMap(view: View?): ArrayList<PlaceOfInterest> {
    val mapCenter: LatLng = mMap.getCameraPosition().target
    val mapBounds: LatLngBounds = mMap.getProjection().getVisibleRegion().latLngBounds
    val northeast = mapBounds.northeast
    val southwest = mapBounds.southwest

    val radius = Math.round(SphericalUtil.computeDistanceBetween(northeast, southwest) / 2).toInt()
    val returnedMapPoints: ArrayList<PlaceOfInterest> = getLocalPlaces(mapCenter, radius, searchTerm)
    mMap.clear()
    writeMarkersToMap(returnedMapPoints)
    return returnedMapPoints
}
}