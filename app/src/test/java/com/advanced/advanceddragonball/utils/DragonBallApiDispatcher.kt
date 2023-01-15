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
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("json/heroes.json"))
            }
            else -> MockResponse().throttleBody(1024, 5, TimeUnit.SECONDS)
        }
    }
}