package com.example.shyf_message.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.shyf_message.R
import com.example.shyf_message.activity.BaseFragments
import com.example.shyf_message.activity.IntroActivity
import com.example.shyf_message.activity.MainActivity
import com.example.shyf_message.databinding.FragmentSettingsBinding
import com.example.shyf_message.models.User
import com.example.shyf_message.screenstate.ScreenState
import com.example.shyf_message.ui.accountdetails.AccountDetailFragment
import com.example.shyf_message.ui.themesetting.ThemeSettingFragment
import com.example.shyf_message.utils.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.callback.StoryClickListeners
import omari.hamza.storyview.model.MyStory
import java.text.SimpleDateFormat

class SettingsFragment : BaseFragments() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferenceManager: PreferenceManager
    var listStory: ArrayList<MyStory> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val viewModel: SettingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)
        val root: View = binding.root
        //....
        preferenceManager = PreferenceManager(activity?.applicationContext!!)
        viewModel.userLiveData.observe(viewLifecycleOwner) { state ->
            setUpUi(state)
        }
        setListeners()
        return root
    }

    private fun setListeners() {
        binding.btnLogout.setOnClickListener {
            signOut()
        }
//        binding.settingscreenIvProfile.setOnClickListener {
//            val currentStory = MyStory(
//                "https://firebasestorage.googleapis.com/v0/b/shyf-f9631.appspot.com/o/USER_IMAGE1694283333997.jpg?alt=media&token=4d5356ec-c28c-4ea2-9ff9-2df3185349c4",
//                SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").parse("٠٩/١١/٢٠٢٣ ٠٤:٠٦:٤٥ م"),
//                "Hi My name is yazan"
//            )
//            listStory.add(currentStory)
//            StoryView.Builder(requireActivity().supportFragmentManager)
//                .setStoriesList(listStory)
//                .setStoryDuration(5000)
//                .setTitleText("Yazan Abu Ali")
//                .setSubtitleText("Like & Share")
//                .setTitleLogoUrl("https://firebasestorage.googleapis.com/v0/b/shyf-f9631.appspot.com/o/USER_IMAGE1694283333997.jpg?alt=media&token=4d5356ec-c28c-4ea2-9ff9-2df3185349c4")
//                .setStoryClickListeners(object :StoryClickListeners{
//                    override fun onDescriptionClickListener(position: Int) {
//                       //
//                    }
//
//                    override fun onTitleIconClickListener(position: Int) {
//                        //
//                    }
//
//                })
//                .build()
//                .show()
//        }
        binding.settingScreenCvAccountdetailicon.setOnClickListener {
            val bottomSheetFragment = AccountDetailFragment()
            bottomSheetFragment.show(requireActivity().supportFragmentManager, "bottomSheetTag")
        }
        binding.settingscreenCvAccount.setOnClickListener {
            val bottomSheetFragment = AccountDetailFragment()
            bottomSheetFragment.show(requireActivity().supportFragmentManager, "bottomSheetTag")
        }
        binding.settingscreenCvSettingtheme.setOnClickListener {
            val bottomSheetFragment = ThemeSettingFragment()
            bottomSheetFragment.show(requireActivity().supportFragmentManager, "bottomSheetTag")
        }
    }

    private fun setUpUi(state: ScreenState<User?>) {
        binding.settingscreenTvProfilename.text = state.data?.name
        binding.settingscreenTvProfileemail.text = state.data?.email
        binding.settingscreenTvProfilephone.text = state.data?.whatsappLink
        Glide
            .with(requireContext())
            .load(state.data?.userImage)
            .centerCrop()
            .placeholder(R.drawable.profile_icon_placeholder_1)
            .into(binding.settingscreenIvProfile)
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        preferenceManager.clear()
        val intent = Intent(activity, IntroActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        activity?.finish()
        MainActivity().finish()
    }

}