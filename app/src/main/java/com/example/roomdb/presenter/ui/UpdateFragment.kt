package com.example.roomdb.presenter.ui

import android.app.AlertDialog
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomdb.R
import com.example.roomdb.common.BaseFragment
import com.example.roomdb.data.model.User
import com.example.roomdb.databinding.FragmentUpdateBinding
import com.example.roomdb.presenter.viewModel.UserVideModel

class UpdateFragment : BaseFragment<FragmentUpdateBinding>(FragmentUpdateBinding::inflate) {

    private val args by navArgs<UpdateFragmentArgs>()
    private val userViewModel : UserVideModel by viewModels()

    override fun listeners() {
    }

    override fun init() {
        binding.apply {
            etFirstName.setText(args.currentUser.firstName)
            etLastName.setText(args.currentUser.lastName)
            etAge.setText(args.currentUser.age.toString())
        }
        binding.btnUpdate.setOnClickListener {
            updateUser()
        }
    }

    private fun updateUser(){
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val age = binding.etAge.text.toString().toInt()

        if (inputChecks()){
            Toast.makeText(context, "field all fields!", Toast.LENGTH_SHORT).show()
        }else{
            val user = User(args.currentUser.id, firstName, lastName, age)
            userViewModel.updateUser(user)
            findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToUsersFragment())
            Toast.makeText(context, "User updated successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputChecks(): Boolean = with(binding) {
        return@with binding.etFirstName.text.toString().isEmpty() ||
                binding.etLastName.text.toString().isEmpty() ||
                binding.etAge.text.toString().isEmpty()
    }

    override fun observers() {
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete)
            deleteUser()
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes"){ _,_ ->
            userViewModel.deleteUser(user = args.currentUser)
            Toast.makeText(context, "${args.currentUser.firstName} successfully deleted!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToUsersFragment())
        }
        builder.setNegativeButton("No"){ _,_ -> }
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Are you sure to delete ${args.currentUser.firstName}?")
        builder.create()
            .show()
    }
}