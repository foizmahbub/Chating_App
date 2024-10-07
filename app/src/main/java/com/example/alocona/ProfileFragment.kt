package com.example.alocona

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alocona.databinding.FragmentProfileBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var  userDB:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProfileBinding.inflate(inflater,container,false)

        userDB=FirebaseDatabase.getInstance().reference
        requireArguments().getString("id")?.let {

            getUserById(it)
        }
        return binding.root
    }

    private fun getUserById(userId: String) {
        userDB.child(DBNODES.USER).child(userId).addValueEventListener(
            object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.getValue(User::class.java)?.let {
                        binding.apply {
                            fullName.text=it.fullName
                           bioTV.text=it.bio
                            emailTV.text=it.email
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
        )

    }


}