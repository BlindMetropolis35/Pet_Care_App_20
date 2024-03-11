package com.example.petcareapp20.mainhome.ui.personal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petcareapp20.LoginActivity
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.FragmentPersonalBinding
import com.google.firebase.auth.FirebaseAuth

class PersonalFragment : Fragment() {

    private var _binding: FragmentPersonalBinding? = null
    lateinit var auth: FirebaseAuth

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        //kondisi user sedang login atau tidak
        if (user != null) {
            binding.edtName.setText(user.displayName)
            binding.edtEmail.text = user.email

            val logout=binding.logout
            logout.setOnClickListener {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}