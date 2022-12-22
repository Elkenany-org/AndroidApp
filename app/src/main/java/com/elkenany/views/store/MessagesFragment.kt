package com.elkenany.views.store

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.elkenany.R
import com.elkenany.api.auth.AuthImplementation.Companion.auth
import com.elkenany.databinding.FragmentMessagesBinding
import com.elkenany.viewmodels.MessagesViewModel
import com.elkenany.viewmodels.ViewModelFactory
import com.elkenany.views.store.adapter.MessagesAdapter


class MessagesFragment : Fragment() {
    private lateinit var binding: FragmentMessagesBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MessagesViewModel
    private lateinit var messagesAdapter: MessagesAdapter

    private val args: MessagesFragmentArgs by navArgs()
    private lateinit var userTwoName: String

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
        userTwoName = try {
            args.name!!
        } catch (e: Exception) {
            "الرسائل"
        }
        binding.appBarTitle.text = userTwoName

        messagesAdapter = MessagesAdapter(auth!!.name.toString())
        binding.messagesRecyclerView.adapter = messagesAdapter
        binding.sendMessageBtn.setOnClickListener {
            val message = binding.sendMessage.text.trim().toString()
            if (message.isNotEmpty()) {
                viewModel.sendMessageData(args.id, message)
            }
        }
        viewModel.messageList.observe(viewLifecycleOwner) {
            if (it!!.massages.isEmpty()) {
                binding.messagesRecyclerView.visibility = View.GONE
            } else {
                binding.messagesRecyclerView.visibility = View.VISIBLE
                binding.messagesRecyclerView.scrollToPosition(it.massages.size - 1)
                messagesAdapter.submitList(it.massages)
            }
        }
        viewModel.messageIndicatior.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    messageIdicator.visibility = View.VISIBLE
                    sendMessageBtn.visibility = View.GONE
                    sendMessage.text.clear()
                }
            } else {
                binding.apply {
                    messageIdicator.visibility = View.GONE
                    sendMessageBtn.visibility = View.VISIBLE
                }
            }
        }
        return binding.root
    }

}