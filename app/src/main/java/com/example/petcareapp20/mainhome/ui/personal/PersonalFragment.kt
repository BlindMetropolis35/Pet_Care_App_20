package com.example.petcareapp20.mainhome.ui.personal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.petcareapp20.LoginActivity
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.FragmentPersonalBinding
import com.example.petcareapp20.mainhome.ui.personal.account.AccountActivity
import com.google.firebase.auth.FirebaseAuth

class PersonalFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_personal, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser

        val your_name_display=view.findViewById<TextView>(R.id.your_name_display)
        if (user != null) {
            your_name_display.text = user.displayName
        }

        val account_view=view.findViewById<LinearLayout>(R.id.account_view)
        account_view.setOnClickListener{
            val intent=Intent(requireActivity(),AccountActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}