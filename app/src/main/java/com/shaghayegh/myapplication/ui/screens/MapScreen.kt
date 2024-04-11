package com.shaghayegh.myapplication.ui.screens

import android.widget.TextView
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import com.shaghayegh.myapplication.R
import com.shaghayegh.myapplication.ui.viewmodel.MainViewModel
import java.lang.Double

@Composable
fun MapScreen(mainViewModel: MainViewModel) {



    val feed = mainViewModel.feedStateFlow.collectAsState()
    val busData = feed.value?.entityList

    // widget.MapView
    AndroidView(factory = { context ->
        val mapView = MapView(context)

        mapView.mapboxMap.setCamera(
            CameraOptions.Builder()
                .center(Point.fromLngLat(-63.5724, 44.6488)) // Coordinates for Halifax
                .pitch(0.0)
                .zoom(14.0)
                .bearing(0.0)
                .build()
        )

        val viewAnnotationManager = mapView.viewAnnotationManager

        mapView.apply {
            busData?.forEach{bus ->
                val trip = bus.vehicle.trip
                val position = bus.vehicle.position
                val viewAnnotation = viewAnnotationManager.addViewAnnotation(
                    resId = R.layout.layout,
                    options = viewAnnotationOptions {
                        geometry(Point.fromLngLat(Double.parseDouble(position.longitude.toString()), Double.parseDouble(position.latitude.toString())))                                        }
                )
                val annotationTxtView = viewAnnotation.findViewById<TextView>(R.id.annotation)
                annotationTxtView.text = trip.routeId
            }
        }
        mapView
    })
}
