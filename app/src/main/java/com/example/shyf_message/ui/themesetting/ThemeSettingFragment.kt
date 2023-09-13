package com.example.shyf_message.ui.themesetting

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shyf_message.R
import com.example.shyf_message.databinding.FragmentThemeSettingBinding
import com.example.shyf_message.ui.settingchatwallpaper.SettingChatWallpaperFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ThemeSettingFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentThemeSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ThemeSettingViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentThemeSettingBinding.inflate(inflater, container, false)
        setListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val bottomSheetInternal = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheetInternal!!).apply {
                peekHeight = ViewGroup.LayoutParams.MATCH_PARENT
                isHideable = true
                state = BottomSheetBehavior.STATE_EXPANDED
            }
//            val layout = dialog?.findViewById<CoordinatorLayout>(R.id.settingtheme_cl_parent)
//            layout?.minimumHeight = resources.displayMetrics.heightPixels
        }
    }

    private fun setListeners() {
        binding.settingthemeClWallpaper.setOnClickListener {
            val bottomSheetFragment = SettingChatWallpaperFragment()
            bottomSheetFragment.show(requireActivity().supportFragmentManager, "bottomSheetTag")
            dialog?.dismiss()
        }
    }

}