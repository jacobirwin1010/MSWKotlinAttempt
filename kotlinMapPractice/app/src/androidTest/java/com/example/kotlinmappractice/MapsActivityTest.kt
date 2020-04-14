package com.example.kotlinmappractice

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import org.json.JSONObject
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import java.lang.Exception
/Users/admin/AndroidStudioProjects/kotlinMapPractice/app

@RunWith(AndroidJUnit4::class)
internal class MapsActivityTest {

    var testPlaceOfInterestOutput = """{"geometry" :{"location":{"lat":50.41300469999999,"lng":-5.092063899999999},"viewport":{"northeast":{"lat":50.41444702989273,"lng":-5.090695270107278},"southwest":{"lat":50.41174737010728,"lng":-5.093394929892722}}},"icon":"https:\/\/maps.gstatic.com\/mapfiles\/place_api\/icons\/lodging-71.png","id":"testid","name":"Smarties Surf Lodge","photos":[{"height":720,"html_attributions":["<a href=\"https:\/\/maps.google.com\/maps\/contrib\/102536665466319527351\">fred dale<\/a>"],"photo_reference":"CmRaAAAAGAJbdKWpKn53VJajK75UZpoReqtcyrAWxrRTTODv_a6wGg1sO56fxhWe1g3q1Q4GemCGE27aNJYqrzBG55yCHQEVY4l_lmH_4LqZkCvClVlVb4Tvl7u_ylid7VEb-QPcEhCCIp6M4KAb0AZPqkXfbHDlGhTnTgFXgbEmDxCxrn6gJpWZ-XWjqg","width":960}],"place_id":"ChIJTcgA_c0Pa0gRmPvmHoSSbXw","plus_code":{"compound_code":"CW75+65 Newquay","global_code":"9C2PCW75+65"},"rating":5,"reference":"ChIJTcgA_c0Pa0gRmPvmHoSSbXw","scope":"GOOGLE","types":["lodging","point_of_interest","establishment"],"user_ratings_total":8,"vicinity":"84 Crantock St, Newquay"}"""


    @Rule @JvmField
    var mActivityRule: ActivityTestRule<MapsActivity> = ActivityTestRule(MapsActivity::class.java)


    @Test
    fun getLocalPlaces(){
        val userPosition: LatLng = LatLng(50.41563 as Double,-5.07521 as Double)
        val radiusMeters: Int = 1000
        val nameSearchString: String = "surf"
            //val mockActivity = MapsActivity()
            var getLocalPlacesOutput = mActivityRule.activity.getLocalPlaces(userPosition,radiusMeters,nameSearchString);

        assertTrue(getLocalPlacesOutput.size>1)
        //probably not the best way to test it, this relies on Newquay having more than 1 search result
    }

    @Test
    fun writeMarkersToMap(){
        val testPoint = PlaceOfInterest(JSONObject(testPlaceOfInterestOutput))
        var output: ArrayList<Marker>?
        val testPointInArray = ArrayList<PlaceOfInterest>()
        testPointInArray.add(testPoint)

           mActivityRule.activity.runOnUiThread {
               try{
                   Log.i("writeMarkersToMapInfo", "running on UI thread")
                   output = mActivityRule.activity.writeMarkersToMap(testPointInArray)
                   assertEquals(output?.size, 1)
               } finally {
                   mActivityRule.activity.mMap.clear()
               }
           }


    }



//@Test
//fun refreshMap(){
//    refreshMap()
//}

}