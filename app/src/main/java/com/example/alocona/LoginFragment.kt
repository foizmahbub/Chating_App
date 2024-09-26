package com.example.alocona

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.alocona.databinding.FragmentLoginBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    lateinit var firebaseUser: FirebaseUser


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentLoginBinding.inflate(inflater,container,false)

      FirebaseAuth.getInstance().currentUser?.let {
          firebaseUser=it
          findNavController().navigate(R.id.action_loginFragment3_to_homeFragment)
      }


        binding.loginBtn.setOnClickListener {
            val email=binding.emailET.text.toString() .trim()
            val password =binding.passwordET.text.toString().trim()
            if (isEmailValid(email) && isPasswordValid(password)){
               LoginUser(email,password)

                
            }else{
                Toast.makeText(requireContext(), "Invalid Email and Password",
                    Toast.LENGTH_SHORT)
                    .show()
            }



        }

   binding.createAccount.setOnClickListener{
       findNavController().navigate(R.id.action_loginFragment3_to_signInFragment)
   }

        return binding.root
    }

    private fun LoginUser(email: String, password: String) {
        val auth=FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task->
            if (task.isSuccessful){
                val user = auth.currentUser
                Toast.makeText(requireContext(), "Login Successfully${user?.email}", Toast.LENGTH_SHORT)
                    .show()
         findNavController().navigate(R.id.action_loginFragment3_to_homeFragment)

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