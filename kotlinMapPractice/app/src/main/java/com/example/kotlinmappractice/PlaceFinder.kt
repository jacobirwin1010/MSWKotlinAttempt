package com.example.kotlinmappractice

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class PlaceFinder : AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg params: String?): String {
        var stringURL:String? = params[0]
        var result:String = ""

        try{
            var myUrl: URL = URL(stringURL)
            var connection: HttpURLConnection = myUrl.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.readTimeout=15000
            connection.connectTimeout=15000

            connection.connect()

            var streamReader:InputStreamReader = InputStreamReader(connection.inputStream)
            var reader:BufferedReader = BufferedReader(streamReader)
            var stringBuilder:StringBuilder = StringBuilder()

            do{
                val nextLine = reader.readLine() ?: break //elvis operator breaks if readLine is null
                stringBuilder.append(nextLine)
            }while (nextLine != null)

            reader.close()
            streamReader.close()

            result = stringBuilder.toString()

        } catch(e:Exception)
        {
            Log.e("error at PlaceFinder.kt", e.message)
        }

        return result
    }

    override fun onPostExecute(result: String?) {

        super.onPostExecute(result)
    }
}