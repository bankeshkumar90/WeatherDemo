package com.app.weatherdemo.appdata.api

interface BaseMapper<A, B> {

    fun transformToDomain(type: A): B

    fun transformToDo(type: B): A

}