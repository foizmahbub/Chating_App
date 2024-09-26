package com.example.alocona

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.alocona.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

          lateinit var  binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSignInBinding.inflate(inflater,container,false)

        binding.signinBtn.setOnClickListener {
            val email=binding.emailET.text.toString() .trim()
            val password =binding.passwordET.text.toString().trim()
            val user =binding.userName.text.toString().trim()
            if (isEmailValid(email) && isPasswordValid(password)){
                signInUser(email,password,user)
            }
            else{
                Toast.makeText(requireContext(), "Invalid Email and Password",
                    Toast.LENGTH_SHORT).show()
            }
        }





        return binding.root
    }

    private fun signInUser(email: String, password: String, user: String) {
        val auth=FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
            if (task.isSuccessful){
                Toast.makeText(requireContext(), "Create Account Successfully",
                    Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_signInFragment_to_loginFragment3)
            }
            else{
                Toast.makeText(requireContext(), "${task.exception?.message}",
                    Toast.LENGTH_SHORT).show()
            }
            

        }

    }

    private fun isEmailValid(email:String):Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
        private fun isPasswordValid(password:String):Boolean{
            return password.length >=6
        }


}