package com.example.roomdb.presenter.ui

import android.app.AlertDialog
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdb.R
import com.example.roomdb.adapter.UserAdapter
import com.example.roomdb.common.BaseFragment
import com.example.roomdb.presenter.viewModel.UserVideModel
import com.example.roomdb.databinding.FragmentUsersBinding
import kotlinx.coroutines.launch

class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {

    private lateinit var userAdapter: UserAdapter
    private val userViewModel: UserVideModel by viewModels()

    override fun listeners() {

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(UsersFragmentDirections.actionUsersFragmentToAddFragment())
        }
    }

    override fun init() {

        initRecycler()

    }
    private fun initRecycler(){

        userAdapter = UserAdapter()

        binding.rvUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun observers() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                userViewModel.readAllData.collect{
                    userAdapter.submitList(it)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete)
            deleteAllUsers()
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes"){ _,_ ->
            userViewModel.deleteAllUser()
            Toast.makeText(context, "Users successfully deleted!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){ _,_ -> }
        builder.setTitle("Delete all users")
        builder.setMessage("Are you sure to delete all users?")
        builder.create()
            .show()
    }
}