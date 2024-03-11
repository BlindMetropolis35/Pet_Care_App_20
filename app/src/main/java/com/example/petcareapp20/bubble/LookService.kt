package com.example.petcareapp20.bubble

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.FragmentLookServiceBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions

class LookService : Fragment() {

    private var _binding: FragmentLookServiceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflater.inflate(R.layout.fragment_look_service, container, false)
        // Initialize map fragment
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?

        // Async map
        supportMapFragment!!.getMapAsync { googleMap ->
            // When map is loaded
            googleMap.setOnMapClickListener { latLng -> // When clicked on map
                // Initialize marker options
                val markerOptions = MarkerOptions()
                // Set position of marker
                markerOptions.position(latLng)
                // Set title of marker
                markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude)
                // Remove all marker
                googleMap.clear()
                // Animating to zoom the marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                // Add marker on map
                googleMap.addMarker(markerOptions)
            }
        }
        // Return view
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}