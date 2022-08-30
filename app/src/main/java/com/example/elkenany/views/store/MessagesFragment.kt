package com.example.elkenany.views.store

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.elkenany.R
import com.example.elkenany.databinding.FragmentMessagesBinding
import com.example.elkenany.viewmodels.MessagesViewModel
import com.example.elkenany.viewmodels.ViewModelFactory
import com.example.elkenany.views.store.adapter.MessagesAdapter


class MessagesFragment : Fragment() {
    private lateinit var binding: FragmentMessagesBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MessagesViewModel
    private lateinit var messagesAdapter: MessagesAdapter

    private val args: MessagesFragmentArgs by navArgs()

    override fun onResume() {
        super.onResume()
        viewModel.getAllChatsData(args.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messages, container, false)
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[MessagesViewModel::class.java]
        binding.appBarTitle.text = args.name

        messagesAdapter = MessagesAdapter(args.name.toString())
        binding.messagesRecyclerView.adapter = messagesAdapter

        viewModel.messageList.observe(viewLifecycleOwner) {
            if (it!!.massages.isEmpty()) {
                Log.i("massages", "data not found")
                binding.messagesRecyclerView.visibility = View.GONE
            } else {
                Log.i("massages", "data found")
                binding.messagesRecyclerView.visibility = View.VISIBLE
                messagesAdapter.submitList(it.massages)
            }
        }
        return binding.root
    }

}