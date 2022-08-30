package com.example.elkenany.views.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.elkenany.ClickListener
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentChatsBinding
import com.example.elkenany.viewmodels.ChatsViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.store.adapter.ChatsListAdapter


class ChatsFragment : Fragment() {
    private lateinit var binding: FragmentChatsBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ChatsViewModel
    private lateinit var chatsListAdapter: ChatsListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chats, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[ChatsViewModel::class.java]
        chatsListAdapter = ChatsListAdapter(ClickListener {
            requireView().findNavController()
                .navigate(
                    ChatsFragmentDirections.actionChatsFragmentToMessagesFragment(
                        it.id!!,
                        it.name
                    )
                )
        })
        binding.notificationRecyclerView.adapter = chatsListAdapter
        binding.signInBtn.setOnClickListener {
            requireParentFragment().requireParentFragment().findNavController()
                .navigate(R.id.loginFragment)
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    notificationRecyclerView.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                    loginRequiredMessage.visibility = View.GONE
                    loadingProgressbar.visibility = View.VISIBLE
                    signInBtn.visibility = View.GONE
                }
            } else {
                binding.apply {
                    loadingProgressbar.visibility = View.GONE
                }
            }
        }
        viewModel.exception.observe(viewLifecycleOwner) {
            if (it == 401) {
                binding.apply {
                    loadingProgressbar.visibility = View.GONE
                    loginRequiredMessage.visibility = View.VISIBLE
                    signInBtn.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    loginRequiredMessage.visibility = View.GONE
                    signInBtn.visibility = View.GONE
                }
            }
        }

        viewModel.chatsList.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.apply {
                    notificationRecyclerView.visibility = View.GONE
                    errorMessage.visibility = View.VISIBLE
                }
            } else if (it.chat.isNotEmpty()) {
                binding.apply {
                    notificationRecyclerView.visibility = View.VISIBLE
                    chatsListAdapter.submitList(it.chat)
                    errorMessage.visibility = View.GONE
                    loginRequiredMessage.visibility = View.GONE
                    signInBtn.visibility = View.GONE
                }
            } else if (it.chat.isEmpty()) {
                binding.apply {
                    notificationRecyclerView.visibility = View.GONE
                    errorMessage.apply {
                        visibility = View.VISIBLE
                        text = "لا توجد لديك محادثات"
                    }
                }
            }
        }
        return binding.root
    }

}