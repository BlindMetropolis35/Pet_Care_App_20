package com.example.petcareapp20.mainhome.ui.doctor

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petcareapp20.R
import com.example.petcareapp20.retrofit.DoctorCallList
import com.example.petcareapp20.retrofit.DoctorCardInfo
import com.example.petcareapp20.retrofit.DoctorDetailService
import androidx.appcompat.widget.SearchView
import androidx.core.widget.ContentLoadingProgressBar
import com.example.petcareapp20.HomeService.MainDoctorListAdapter
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorFragment : Fragment() {

    lateinit var adapter: DoctorFragmentAdapter
    private var doctorCardInfo = mutableListOf<DoctorCardInfo>()
    private var searchQuery = ""
    private lateinit var refreshButton: MaterialButton
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_doctor, container, false)

        adapter = DoctorFragmentAdapter(requireActivity(), doctorCardInfo)
        val doctorcards = view.findViewById<RecyclerView>(R.id.doctorcards)
        doctorcards.adapter = adapter
        doctorcards.layoutManager = LinearLayoutManager(requireActivity())

        progressBar = view.findViewById(R.id.progress_bar_frag)

        getdoctor()

        refreshButton = view.findViewById(R.id.refresh_button_frag)
        refreshButton.setOnClickListener {
            doctorCardInfo.clear()
            adapter.notifyDataSetChanged()
            getdoctor()
            refreshButton.visibility = View.GONE
        }

        val searchView = view.findViewById<SearchView>(R.id.search_view_doctor_frag)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                searchQuery = newText
                getdoctor()
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })

        return view
    }

    private fun getdoctor() {
        if (isNetworkAvailable()) {
            progressBar.visibility = View.VISIBLE

            val vets: Call<DoctorCallList> = DoctorDetailService.doctorInstance.getVetInfo(1, "us")

            vets.enqueue(object : Callback<DoctorCallList> {
                override fun onResponse(call: Call<DoctorCallList>, response: Response<DoctorCallList>
                ) {
                    Log.d("Request Vets", "Request accepted $response.body")
                    val vets = response.body()
                    if (vets != null) {
                        val filteredVets = vets.vetinfos.filter {
                            it.vet_name.contains(searchQuery, ignoreCase = true) ?: false ||
                                    it.specialization.contains(searchQuery, ignoreCase = true) ?: false ||
                                    it.clinic_name.contains(searchQuery, ignoreCase = true) ?: false
                        }
                        doctorCardInfo.clear()
                        doctorCardInfo.addAll(filteredVets)
                        adapter.notifyDataSetChanged()
                        progressBar.visibility = View.GONE

                        if (filteredVets.isEmpty()) {
                            progressBar.visibility = View.VISIBLE
                            refreshButton.visibility = View.VISIBLE
                        } else {
                            progressBar.visibility = View.GONE
                            refreshButton.visibility = View.GONE
                        }
                    } else {
                        Toast.makeText(requireActivity(), "No data found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<DoctorCallList>, t: Throwable) {
                    Log.d("Request Vets", "Error occurred", t)
                    progressBar.visibility = View.VISIBLE
                    refreshButton.visibility = View.VISIBLE
                    Toast.makeText(requireActivity(), "Network Error", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        } else {
            Toast.makeText(requireActivity(), "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}