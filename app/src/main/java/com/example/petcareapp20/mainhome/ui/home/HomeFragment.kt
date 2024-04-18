package com.example.petcareapp20.mainhome.ui.home

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.petcareapp20.ComingSoon
import com.example.petcareapp20.HomeService.AllServicesTogether
import com.example.petcareapp20.HomeService.MainDoctorLists
import com.example.petcareapp20.R
import com.example.petcareapp20.doctor.DoctorDetailed
import com.example.petcareapp20.retrofit.DoctorCallList
import com.example.petcareapp20.retrofit.DoctorDetailService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var doctorList = mutableListOf<VetData>()
    private var shuffleddoctorList = mutableListOf<VetData>()
    private lateinit var doctorImageViews: Array<ImageView>
    private lateinit var doctorNameTextViews: Array<TextView>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val see_more1 = view.findViewById<View>(R.id.see_more1)
        see_more1.setOnClickListener {
            val intent = Intent(requireActivity(), AllServicesTogether::class.java)
            startActivity(intent)
        }

        val see_more2 = view.findViewById<View>(R.id.see_more2)
        see_more2.setOnClickListener {
            val intent = Intent(requireActivity(), MainDoctorLists::class.java)
            startActivity(intent)
        }

        val findAVet = view.findViewById<LinearLayout>(R.id.findAVet)
        findAVet.setOnClickListener {
            val intent = Intent(requireActivity(), MainDoctorLists::class.java)
            startActivity(intent)
        }

        val donate_card=view.findViewById<LinearLayout>(R.id.donate_card)
        donate_card.setOnClickListener{
            val intent= Intent(requireActivity(), ComingSoon::class.java)
            startActivity(intent)
        }

        val insurance_card=view.findViewById<LinearLayout>(R.id.insurance_card)
        insurance_card.setOnClickListener{
            val intent= Intent(requireActivity(), ComingSoon::class.java)
            startActivity(intent)
        }

        val buy_med_card=view.findViewById<LinearLayout>(R.id.buy_med_card)
        buy_med_card.setOnClickListener{
            val intent= Intent(requireActivity(), ComingSoon::class.java)
            startActivity(intent)
        }

        val consult_card=view.findViewById<LinearLayout>(R.id.consult_card)
        consult_card.setOnClickListener{
            val intent= Intent(requireActivity(), ComingSoon::class.java)
            startActivity(intent)
        }

        doctorImageViews =arrayOf(view.findViewById(R.id.doctorimg1),
            view.findViewById(R.id.doctorimg2),
            view.findViewById(R.id.doctorimg3),
            view.findViewById(R.id.doctorimg4),
            view.findViewById(R.id.doctorimg5)
        )

        doctorNameTextViews =arrayOf(
            view.findViewById(R.id.doctorimg1_title),
            view.findViewById(R.id.doctorimg2_title),
            view.findViewById(R.id.doctorimg3_title),
            view.findViewById(R.id.doctorimg4_title),
            view.findViewById(R.id.doctorimg5_title)
        )

        val findAVet1=view.findViewById<LinearLayout>(R.id.findAVet1)
        findAVet1.setOnClickListener{
            val intent = Intent(requireActivity(), DoctorDetailed::class.java)
            intent.putExtra("doctorId", shuffleddoctorList[0]._id)
            requireActivity().startActivity(intent)
        }
        val findAVet2=view.findViewById<LinearLayout>(R.id.findAVet2)
        findAVet2.setOnClickListener{
            val intent = Intent(requireActivity(), DoctorDetailed::class.java)
            intent.putExtra("doctorId", shuffleddoctorList[1]._id)
            requireActivity().startActivity(intent)
        }
        val findAVet3=view.findViewById<LinearLayout>(R.id.findAVet3)
        findAVet3.setOnClickListener{
            val intent = Intent(requireActivity(), DoctorDetailed::class.java)
            intent.putExtra("doctorId", shuffleddoctorList[2]._id)
            requireActivity().startActivity(intent)
        }
        val findAVet4=view.findViewById<LinearLayout>(R.id.findAVet4)
        findAVet4.setOnClickListener{
            val intent = Intent(requireActivity(), DoctorDetailed::class.java)
            intent.putExtra("doctorId", shuffleddoctorList[3]._id)
            requireActivity().startActivity(intent)
        }
        val findAVet5=view.findViewById<LinearLayout>(R.id.findAVet5)
        findAVet5.setOnClickListener{
            val intent = Intent(requireActivity(), DoctorDetailed::class.java)
            intent.putExtra("doctorId", shuffleddoctorList[4]._id)
            requireActivity().startActivity(intent)
        }

        getdoctorImages()

        val banner11=view.findViewById<LinearLayout>(R.id.banner11)
        banner11.setOnClickListener{
            val intent= Intent(requireActivity(), ComingSoon::class.java)
            startActivity(intent)
        }

        val banner22=view.findViewById<LinearLayout>(R.id.banner22)
        banner22.setOnClickListener{
            val intent= Intent(requireActivity(), ComingSoon::class.java)
            startActivity(intent)
        }

        val banner33=view.findViewById<LinearLayout>(R.id.banner33)
        banner33.setOnClickListener{
            val intent= Intent(requireActivity(), ComingSoon::class.java)
            startActivity(intent)
        }

        val see_more3=view.findViewById<TextView>(R.id.see_more3)
        see_more3.setOnClickListener{
            val intent= Intent(requireActivity(), ComingSoon::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun getdoctorImages() {
        if (isNetworkAvailable()) {
            val call: Call<DoctorCallList> = DoctorDetailService.doctorInstance.getVetInfo(1, "us")
            call.enqueue(object : Callback<DoctorCallList> {
                override fun onResponse(call: Call<DoctorCallList>, response: Response<DoctorCallList>) {
                    if (response.isSuccessful) {
                        val vetList = response.body()
                        if (vetList != null) {
                            doctorList = vetList.vetinfos.map { VetData(it._id, it.urlToImage, it.vet_name) }.toMutableList()
                            shuffledSortedImgName(doctorList)
                            updateVetImg(shuffleddoctorList)
                        }
                    }
                }

                override fun onFailure(call: Call<DoctorCallList>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        } else {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun shuffledSortedImgName(doctorList:MutableList<VetData>) {
        shuffleddoctorList=doctorList.shuffled() as MutableList<VetData>
    }

    private fun updateVetImg(shuffleddoctorList: MutableList<VetData>) {
        if (isNetworkAvailable()) {
                Glide.with(context as Context).load(shuffleddoctorList[0].urlToImage)
                    .into(doctorImageViews[0])
                doctorNameTextViews[0].text = shuffleddoctorList[0].vet_name

                Glide.with(context as Context).load(shuffleddoctorList[1].urlToImage)
                    .into(doctorImageViews[1])
                doctorNameTextViews[1].text = shuffleddoctorList[1].vet_name

                Glide.with(context as Context).load(shuffleddoctorList[2].urlToImage)
                    .into(doctorImageViews[2])
                doctorNameTextViews[2].text = shuffleddoctorList[2].vet_name

                Glide.with(context as Context).load(shuffleddoctorList[3].urlToImage)
                    .into(doctorImageViews[3])
                doctorNameTextViews[3].text = shuffleddoctorList[3].vet_name

                Glide.with(context as Context).load(shuffleddoctorList[4].urlToImage)
                    .into(doctorImageViews[4])
                doctorNameTextViews[4].text = shuffleddoctorList[4].vet_name
        }else {
            Toast.makeText(requireActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}