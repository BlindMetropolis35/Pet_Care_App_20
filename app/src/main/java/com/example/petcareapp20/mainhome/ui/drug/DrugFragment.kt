package com.example.petcareapp20.mainhome.ui.drug

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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.petcareapp20.HomeService.MainDoctorListAdapter
import com.example.petcareapp20.retrofit_sec.DrugCallList
import com.example.petcareapp20.retrofit_sec.DrugCardInfo
import com.example.petcareapp20.retrofit_sec.DrugDetailService
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrugFragment : Fragment() {

    lateinit var adapter: DrugFragmentAdapter
    private var drugCardInfo = mutableListOf<DrugCardInfo>()
    private var searchQuery = ""
    private lateinit var refreshButton: MaterialButton
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_drug, container, false)

        adapter = DrugFragmentAdapter(requireActivity(), drugCardInfo)
        val drugcards = view.findViewById<RecyclerView>(R.id.drugcards)
        drugcards.adapter = adapter
        drugcards.layoutManager = GridLayoutManager(requireActivity(),2)

        progressBar = view.findViewById< ProgressBar>(R.id.progress_bar_frag)

        getdrug()

        refreshButton = view.findViewById< MaterialButton>(R.id.refresh_button_frag)
        refreshButton.setOnClickListener {
            drugCardInfo.clear()
            adapter.notifyDataSetChanged()
            getdrug()
            refreshButton.visibility = View.GONE
        }

        val searchView = view.findViewById<SearchView>(R.id.search_view_drug_frag)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                searchQuery = newText
                getdrug()
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })

        return view
    }

    private fun getdrug() {
        if (isNetworkAvailable()) {
            progressBar.visibility = View.VISIBLE

            val vets: Call<DrugCallList> = DrugDetailService.drugInstance.getDrugInfo(1, "us")

            vets.enqueue(object : Callback<DrugCallList> {
                override fun onResponse(call: Call<DrugCallList>, response: Response<DrugCallList>
                ) {
                    val drugs = response.body()
                    Log.d("Request Drug", "Request accepted $drugs")
                    if (drugs != null) {
                        val filteredDrugs = drugs.druginfos.filter {
                            it.drug_name.contains(searchQuery, ignoreCase = true) ?: false ||
                                    it.drug_price.contains(searchQuery, ignoreCase = true) ?: false ||
                                    it.description.contains(searchQuery, ignoreCase = true) ?: false
                        }
                        Log.d("Request Drug", "Request accepted $filteredDrugs")
                        drugCardInfo.clear()
                        drugCardInfo.addAll(filteredDrugs)
                        adapter.notifyDataSetChanged()
                        progressBar.visibility = View.GONE

                        if (filteredDrugs.isEmpty()) {
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

                override fun onFailure(call: Call<DrugCallList>, t: Throwable) {
                    Log.d("Request Drug", "Error occurred", t)
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