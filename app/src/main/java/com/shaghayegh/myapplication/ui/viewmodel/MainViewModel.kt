package com.shaghayegh.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.transit.realtime.GtfsRealtime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.net.URL

class MainViewModel : ViewModel() {

    private val _feedStateFlow = MutableStateFlow<GtfsRealtime.FeedMessage?>(null)
    val feedStateFlow: StateFlow<GtfsRealtime.FeedMessage?> = _feedStateFlow

    private val _alertFeedStateFlow = MutableStateFlow<GtfsRealtime.FeedMessage?>(null)
    val alertFeedStateFlow: StateFlow<GtfsRealtime.FeedMessage?> = _alertFeedStateFlow


    fun loadBusPositions() {
        Thread {
            val url = URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb")
            val feed = GtfsRealtime.FeedMessage.parseFrom(url.openStream())

            val url2 = URL("https://gtfs.halifax.ca/realtime/Alert/Alerts.pb")
            val feed2 = GtfsRealtime.FeedMessage.parseFrom(url2.openStream())


            Log.i("Feed:", feed.toString())
            Log.i("Feed2:", feed2.toString())


            _alertFeedStateFlow.value = feed2

            feed2.entityList.forEach { entity ->
                if (entity.hasAlert()) {
                    val alert = entity.alert
                    val routeIdAffected = alert.informedEntityList[0].routeId
                    val cause = alert.cause
                    val effect = alert.effect
                    val description = alert.headerText.translationList[0].text
                    Log.i("Alert", "Route: $routeIdAffected, Cause: $cause, Effect: $effect, Description: $description")
                }
            }



            _feedStateFlow.value = feed

            feed.entityList.forEach { entity ->
                if (entity.hasVehicle()) {
                    val vehicle = entity.vehicle // Get the vehicle information from the entity
                    if (vehicle.hasTrip()) {
                        val trip = vehicle.trip// Get the trip information from the vehicle
                        val routeId = if (trip.hasRouteId()) trip.routeId else "Unknown" // Get the route ID from the trip, if available
                        val latitude = vehicle.position.latitude// Get the latitude of the vehicle's position
                        val longitude = vehicle.position.longitude// Get the longitude of the vehicle's position
                        Log.i("BusPosition", "Route: $routeId, Latitude: $latitude, Longitude: $longitude") // Log the route ID, latitude, and longitude of the vehicle
                    }
                }
            }
        }.start()
    }
}
