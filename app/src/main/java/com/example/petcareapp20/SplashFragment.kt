import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.petcareapp20.MainActivity
import com.example.petcareapp20.R
import com.example.petcareapp20.RegisterActivity
import com.example.petcareapp20.mainhome.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.math.sign

class SplashFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        firebaseAuth=FirebaseAuth.getInstance()
        val user=firebaseAuth.currentUser

        Handler().postDelayed({
                if (user != null) {
                    val intent = Intent(requireActivity(), HomeActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                else {
                    findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
                }
        }, 2000)

        return view
    }
}
