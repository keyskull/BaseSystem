package me.keyskull.core

/**
  * Created by Jane on 2016/11/21.
  */

abstract class Initialization(programName:String,version:String) {
  def main(args: Array[String]): Unit = {
    new me.keyskull.util.Location{
      override def Init() = {null}
    }.getLocation()
  }
}
