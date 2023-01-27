package com.advanced.advanceddragonball.utils

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class DragonBallApiDispatcher: Dispatcher(){

    override fun dispatch(request: RecordedRequest): MockResponse {

        return when (request.path) {
            "/api/heros/all" -> {
                if (request.body.toString().contains("\"\"")) {
                    return MockResponse()
                        .setResponseCode(HttpURLConnection.HTTP_OK)
                        .setBody(getJson("json/heroes.json"))
                } else {
                    return MockResponse()
                        .setResponseCode(HttpURLConnection.HTTP_OK)
                        .setBody(getJson("json/hero.json"))
                }
            }
            "/api/auth/login" -> {
                return MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setHeader("Content-type", "text/plain")
                    .setBody("TOKEN")
            }
            "/api/heros/locations" -> {
                return MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("json/heroLocations.json"))
            }
            else -> MockResponse().throttleBody(1024, 5, TimeUnit.SECONDS)
        }
    }
}