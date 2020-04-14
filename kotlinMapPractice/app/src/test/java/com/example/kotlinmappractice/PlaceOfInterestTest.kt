package com.example.kotlinmappractice

import android.util.Log
import org.json.JSONObject
import org.json.JSONException
import org.json.JSONTokener
import org.json.JSONStringer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.Exception
import kotlin.math.log

internal class PlaceOfInterestTest {

    @Test
    fun `placeOfInterest`() {
        var testInputString = """{"geometry":{"location":{"lat":50.41300469999999,"lng":-5.092063899999999},"viewport":{"northeast":{"lat":50.41444702989273,"lng":-5.090695270107278},"southwest":{"lat":50.41174737010728,"lng":-5.093394929892722}}},"icon":"https:\/\/maps.gstatic.com\/mapfiles\/place_api\/icons\/lodging-71.png","id":"e39880a46cf7bec9ba159ea27552cf218cbb66d3","name":"Smarties Surf Lodge","photos":[{"height":720,"html_attributions":["<a href=\"https:\/\/maps.google.com\/maps\/contrib\/102536665466319527351\">fred dale<\/a>"],"photo_reference":"CmRaAAAAGAJbdKWpKn53VJajK75UZpoReqtcyrAWxrRTTODv_a6wGg1sO56fxhWe1g3q1Q4GemCGE27aNJYqrzBG55yCHQEVY4l_lmH_4LqZkCvClVlVb4Tvl7u_ylid7VEb-QPcEhCCIp6M4KAb0AZPqkXfbHDlGhTnTgFXgbEmDxCxrn6gJpWZ-XWjqg","width":960}],"place_id":"ChIJTcgA_c0Pa0gRmPvmHoSSbXw","plus_code":{"compound_code":"CW75+65 Newquay","global_code":"9C2PCW75+65"},"rating":5,"reference":"ChIJTcgA_c0Pa0gRmPvmHoSSbXw","scope":"GOOGLE","types":["lodging","point_of_interest","establishment"],"user_ratings_total":8,"vicinity":"84 Crantock St, Newquay"}"""
        var test:PlaceOfInterest

        var testInputJSON = JSONObject(testInputString)

        test = PlaceOfInterest(testInputJSON)

        Assertions.assertEquals(test.placeID,"ChIJTcgA_c0Pa0gRmPvmHoSSbXw")
        Assertions.assertEquals(test.position?.latitude, 50.41300469999999)
        Assertions.assertEquals(test.position?.longitude, -5.092063899999999)
        Assertions.assertEquals(test.name,  "Smarties Surf Lodge")
        Assertions.assertEquals(test.openingHours,"no opening times provided")
    }
}