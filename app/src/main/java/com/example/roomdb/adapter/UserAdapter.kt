package com.example.roomdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdb.data.model.User
import com.example.roomdb.databinding.UserItemBinding
import com.example.roomdb.presenter.ui.UsersFragmentDirections

class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder>(ItemCallback) {

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = getItem(adapterPosition)
            binding.apply {
                tvFirstname.text = item.firstName
                tvLastname.text = item.lastName
                tvAge.text = item.age.toString()
                tvId.text = item.id.toString()

                listItem.setOnClickListener {
                    itemView.findNavController().navigate(UsersFragmentDirections.actionUsersFragmentToUpdateFragment(item))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(UserItemBinding.inflate(LayoutInflater.from(parent.context), parent,
            false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind()


    object ItemCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}