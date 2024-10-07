package com.example.alocona

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.alocona.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.firestore.auth.User


class HomeFragment : Fragment(),UserAdapter.ItemClick {
    lateinit var  binding: FragmentHomeBinding
    lateinit var userDB :DatabaseReference
    lateinit var adapter: UserAdapter
    var userlist : MutableList<com.example.alocona.User> = mutableListOf()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        userDB=FirebaseDatabase.getInstance().reference

        binding.logoutBtn.setOnClickListener {
            val auth =FirebaseAuth.getInstance()
            auth.signOut().apply {
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment3)
            }


        }
        adapter=UserAdapter(this@HomeFragment)
        binding.recyclerView.adapter=adapter

    getAvailableUser()

        return binding.root
    }

    private fun getAvailableUser() {
        userDB.child(DBNODES.USER).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
              userlist.clear()
                snapshot.children.forEach {
                   val user:com.example.alocona.User=it.getValue(com.example.alocona.User::class.java)!!

                    userlist.add(user)
                }
                adapter.submitList(userlist)
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }

    override fun onItemClick(user: com.example.alocona.User) {
        var bundle=Bundle()
        bundle.putString("id",user.userId)
        findNavController().navigate(R.id.action_homeFragment_to_profileFragment,bundle)
    }


}





