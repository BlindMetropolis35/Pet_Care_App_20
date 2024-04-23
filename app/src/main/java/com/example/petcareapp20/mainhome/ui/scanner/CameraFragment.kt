package com.example.petcareapp20.mainhome.ui.scanner

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.petcareapp20.ComingSoon
import com.example.petcareapp20.R

class CameraFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_camera, container, false)

        val cameraButton=view.findViewById<Button>(R.id.cameraButton)
        cameraButton.setOnClickListener {
            val intent= Intent(requireActivity(), ComingSoon::class.java)
            startActivity(intent)
        }

        val galleryButton=view.findViewById<Button>(R.id.galleryButton)
        galleryButton.setOnClickListener {
            val intent= Intent(requireActivity(), ComingSoon::class.java)
            startActivity(intent)
        }

        val uploadButton=view.findViewById<Button>(R.id.uploadButton)
        uploadButton.setOnClickListener {
            val intent= Intent(requireActivity(), ComingSoon::class.java)
            startActivity(intent)
        }

        return view
    }
}