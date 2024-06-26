package com.example.petcareapp20.mainhome.ui.personal

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.petcareapp20.ComingSoon
import com.example.petcareapp20.LoginActivity
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.FragmentPersonalBinding
import com.example.petcareapp20.mainhome.HomeActivity
import com.example.petcareapp20.mainhome.ui.personal.account.AccountActivity
import com.example.petcareapp20.mainhome.ui.personal.chatsupport.ui.ChatBotActivity
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
        }

        val setting_view=view.findViewById<LinearLayout>(R.id.setting_view)
        setting_view.setOnClickListener{
            val intent=Intent(requireActivity(), ComingSoon::class.java)
            startActivity(intent)
        }

        val notifications_view=view.findViewById<LinearLayout>(R.id.notifications_view)
        notifications_view.setOnClickListener{
            val intent=Intent(requireActivity(), ComingSoon::class.java)
            startActivity(intent)
        }

        val help_view=view.findViewById<LinearLayout>(R.id.help_view)
        help_view.setOnClickListener{
            val intent=Intent(requireActivity(), ComingSoon::class.java)
            startActivity(intent)
        }

        val support_view=view.findViewById<LinearLayout>(R.id.support_view)
        support_view.setOnClickListener{
            val intent=Intent(requireActivity(), ChatBotActivity::class.java)
            startActivity(intent)
        }

        val about_view=view.findViewById<LinearLayout>(R.id.about_view)
        about_view.setOnClickListener{
            val intent=Intent(requireActivity(), ComingSoon::class.java)
            startActivity(intent)
        }

        val logout=view.findViewById<LinearLayout>(R.id.logout_here)
        logout.setOnClickListener{
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("Confirm Logout")
            builder.setMessage("Are you sure you want to log out?")
            builder.setPositiveButton("Logout") { _, _ ->
                firebaseAuth.signOut()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
                Toast.makeText(requireActivity(), "Logged out", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            builder.create().show()
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}