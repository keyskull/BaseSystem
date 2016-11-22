package me.keyskull.util

import java.net.{HttpURLConnection, URL}
import javax.net.ssl.HttpsURLConnection

import org.json.{JSONObject, JSONTokener}

/**
  * Created by Jane on 2016/11/21.
  */
trait Location {

  case class LocationInformation(accuracy: Double, longitude: Double, latitude: Double)

  private val url = new URL("https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyAYteeSoy0p7LwBeBlpPVbzCiHDU8BwRMg")
  private val httpsConnection = url.openConnection().asInstanceOf[HttpsURLConnection]
  httpsConnection.setRequestMethod("POST")
  httpsConnection.setConnectTimeout(5000)
  httpsConnection.setConnectTimeout(10000)
  httpsConnection.setDoOutput(true)
  private val out = httpsConnection.getOutputStream
  out.write("".toCharArray map (s => s.toByte))
  out.flush()
  out.close()
  private val jsonObject = new JSONObject(new JSONTokener(httpsConnection.getInputStream))
  println(jsonObject.toString)

  def Init: LocationInformation

  def getLocation(): LocationInformation = {
    val info = try {
      if (httpsConnection.getResponseCode == HttpURLConnection.HTTP_OK) {
        LocationInformation(jsonObject.getDouble("accuracy"),
          jsonObject.getJSONObject("location").getDouble("lng"),
          jsonObject.getJSONObject("location").getDouble("lat"))
      } else LocationInformation(0, 0, 0)
    }
    catch {
      case _ => LocationInformation(0, 0, 0)
    }
    val data = try {
      Init
    } catch {
      case _ =>

        LocationInformation(0, 0, 0)
    }
    if (info.accuracy != 0 && info.accuracy < data.accuracy) info else data
  }
}
