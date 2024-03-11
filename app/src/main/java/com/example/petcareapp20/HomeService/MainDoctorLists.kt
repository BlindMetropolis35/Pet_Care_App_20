package com.example.petcareapp20.HomeService

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petcareapp20.R
import com.example.petcareapp20.retrofit.DoctorCallList
import com.example.petcareapp20.retrofit.DoctorCardInfo
import com.example.petcareapp20.retrofit.DoctorDetailService
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainDoctorLists : AppCompatActivity() {

    lateinit var adapter: MainDoctorListAdapter
    private var doctorCardInfo = mutableListOf<DoctorCardInfo>()
    private var searchQuery = ""
    private lateinit var refreshButton: MaterialButton
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_doctor_lists)

        adapter = MainDoctorListAdapter(this@MainDoctorLists, doctorCardInfo)
        val doctorcards = findViewById<RecyclerView>(R.id.doctorcards)
        doctorcards.adapter = adapter

        doctorcards.layoutManager = LinearLayoutManager(this@MainDoctorLists)

        progressBar = findViewById(R.id.progress_bar)

        getdoctor()

        refreshButton = findViewById(R.id.refresh_button)
        refreshButton.setOnClickListener {
            doctorCardInfo.clear()
            adapter.notifyDataSetChanged()
            getdoctor()
            refreshButton.visibility = View.GONE
        }


        val searchView = findViewById<SearchView>(R.id.search_view_doctor)
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
    }

    private fun getdoctor() {
        if (isNetworkAvailable()) {
            progressBar.visibility = View.VISIBLE

            val vets: Call<DoctorCallList> = DoctorDetailService.doctorInstance.getVetInfo(1, "us")

            vets.enqueue(object : Callback<DoctorCallList> {
                override fun onResponse(
                    call: Call<DoctorCallList>,
                    response: Response<DoctorCallList>
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
                        Toast.makeText(this@MainDoctorLists, "No data found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<DoctorCallList>, t: Throwable) {
                    Log.d("Request Vets", "Error occurred", t)
                    progressBar.visibility = View.VISIBLE
                    refreshButton.visibility = View.VISIBLE
                    Toast.makeText(this@MainDoctorLists, "Network Error", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this@MainDoctorLists, "No internet connection", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}