package com.example.shyf_message.ui.chats

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shyf_message.R
import com.example.shyf_message.activity.BaseFragments
import com.example.shyf_message.adapters.ChatsAdapter
import com.example.shyf_message.databinding.FragmentChatsBinding
import com.example.shyf_message.models.ChatRooms
import com.example.shyf_message.models.User
import com.example.shyf_message.screenstate.ScreenState
import com.example.shyf_message.utils.Constants
import com.example.shyf_message.viewmodelfactory.ChatsViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatsFragment : BaseFragments() {

    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ChatsViewModel
    private lateinit var database: FirebaseFirestore
    private val senderid: String = FirebaseAuth.getInstance().uid!!.toString()
    private lateinit var sender: User
    private var adapter: ChatsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        viewModel = ViewModelProvider(this, ChatsViewModelFactory(senderid,""))[ChatsViewModel::class.java]
        database = FirebaseFirestore.getInstance()
        database.collection(Constants.KEY_COLLECTION_USER).document(senderid).addSnapshotListener { value, error ->
            if(error==null && value!=null) {
                sender = value.toObject(User::class.java)!!
            }
        }
        viewModel.chatLiveData.observe(viewLifecycleOwner) { state ->
            processChatList(state)
        }
        setListeners()
        Log.d("YZ",senderid)
        return root
    }

    private fun setListeners() {
        binding.chatscreenCvOptiondel.setOnClickListener {
            val bottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            bottomSheet.setContentView(R.layout.chatroomscreen_delete_dialog)
            bottomSheet.show()
            val btnNo = bottomSheet.findViewById<CardView>(R.id.chatroom_cv_delno)
            val btnYes = bottomSheet.findViewById<CardView>(R.id.chatroom_cv_delyes)
            btnNo?.setOnClickListener {
                bottomSheet.dismiss()
            }
            btnYes?.setOnClickListener {
                val documents = adapter?.getSelectedItems()
                viewModel.deleteChatRooms(documents!!)
                bottomSheet.dismiss()
            }
        }
        binding.chatscreenCvOptioncancel.setOnClickListener {
            adapter?.getSelectedItems()
        }
    }

    private fun processChatList(state: ScreenState<List<ChatRooms>?>) {
        when(state) {
            is ScreenState.Loading -> {

            }
            is ScreenState.Success -> {

                if(state.data!=null) {
                    Log.e("Size of Contacts List", "${state.data.size}")
                    adapter = ChatsAdapter(requireContext(),state.data)
                    val rv = binding.chatscreenRvChats
                    rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                    rv.setHasFixedSize(true)
                    rv.adapter = adapter
                    adapter?.selectionEnabledLiveData?.observe(viewLifecycleOwner) { state ->
                        if(state==true) {
                            binding.chatscreenCvOptionbar.visibility = View.VISIBLE
                        } else {
                            binding.chatscreenCvOptionbar.visibility = View.GONE
                        }
                    }
                    adapter?.notifyDataSetChanged()
                }
            }
            is ScreenState.Error -> {

            }
        }
    }



}