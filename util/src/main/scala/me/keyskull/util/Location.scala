package me.keyskull.util

import java.net.{HttpURLConnection, URL}
import javax.net.ssl.HttpsURLConnection

import org.json.{JSONObject, JSONTokener}

import scala.util.{Failure, Success, Try}

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

  def get(): LocationInformation = {
    val info = Try(
        LocationInformation(jsonObject.getDouble("accuracy"),
          jsonObject.getJSONObject("location").getDouble("lng"),
          jsonObject.getJSONObject("location").getDouble("lat"))
    )
    val data = Try(Init)
    info match {
      case Success(x) => data.map(y => if(x.accuracy != 0 && x.accuracy < y.accuracy)x else y).getOrElse(x)
      case Failure(ex) => data.map(y=>y).getOrElse(LocationInformation(0,0,0))
    }
  }
}
