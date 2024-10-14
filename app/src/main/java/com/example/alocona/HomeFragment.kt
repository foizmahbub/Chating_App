package com.example.alocona

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil3.load
import com.example.alocona.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

    private var auth =FirebaseAuth.getInstance()
    private var bundle=Bundle()
    private  var currentUser: com.example.alocona.User? =null
    lateinit var firebaseUser: FirebaseUser
    var userlist : MutableList<com.example.alocona.User> = mutableListOf()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        userDB=FirebaseDatabase.getInstance().reference
     FirebaseAuth.getInstance().currentUser?.let {
         firebaseUser=it

     }

         binding.profileBtn.setOnClickListener {
             currentUser?.let {
                 bundle.putString("id",it.userId)
                 findNavController().navigate(R.id.action_homeFragment_to_profileFragment,bundle)

             }
         }
        binding.logoutBtn.setOnClickListener {

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
                    val user: com.example.alocona.User =
                        it.getValue(com.example.alocona.User::class.java)!!

                    if (firebaseUser.uid != user.userId){

                        userlist.add(user)
                   }else{
                       currentUser=user
                       // setProfile()
                    }

                }
                adapter.submitList(userlist)
            }

           // private fun setProfile() {
             //   currentUser.let {
                   // binding.profileBtn.load("https:" + "//www.google." + "com/url?sa=i&url" + "=https%3A%2F%2Fbd.linkedin.com%2Fin%2Ffoiz-mahbub-soroj-9b3053248&psig=" + "AOvVaw1QJEv7rE9bVFO3Pi_uJKJ1&ust=1728997892060000&source=images&cd=vfe&opi=" + "89978449&ved=0CBQQjRxqFwoTCNiCo6r5jYkDFQAAAAAdAAAAABAE")
              //  }

          //  }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }

    override fun onItemClick(user: com.example.alocona.User) {

        bundle.putString("id",user.userId)
        findNavController().navigate(R.id.action_homeFragment_to_profileFragment,bundle)
    }


}





