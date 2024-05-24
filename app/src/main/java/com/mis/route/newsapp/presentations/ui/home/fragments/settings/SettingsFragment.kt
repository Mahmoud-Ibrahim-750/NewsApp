package com.mis.route.newsapp.presentations.ui.home.fragments.settings

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.mis.route.newsapp.Constants
import com.mis.route.newsapp.R
import com.mis.route.newsapp.databinding.FragmentSettingsBinding
import java.util.Locale

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var settingsSP: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsSP = requireActivity().getSharedPreferences(
            Constants.SP_SETTINGS_FILE_NAME,
            Context.MODE_PRIVATE
        )

        setupExposedDropdownMenus()

        binding.languageInput.doOnTextChanged { text, _, _, _ ->
            activateArabicLang(
                when (text.toString()) {
                    resources.getString(R.string.arabic_language) -> true
                    else -> false
                }
            )
        }
    }

    private fun activateArabicLang(activate: Boolean) {
        val languageCode = if (activate) Constants.ARABIC_CODE else Constants.ENGLISH_CODE
        settingsSP.edit().putString(Constants.PREF_LANGUAGE_KEY, languageCode).apply()
        setLocale(languageCode)
        activity?.let { ActivityCompat.recreate(it) }
    }

    private fun populateMenusWithOptions() {
        binding.languageInput.setText(
            when (isArabicLangActive()) {
                true -> resources.getString(R.string.arabic_language)
                false -> resources.getString(R.string.english_language)
            }, false
        )
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        activity?.let {
            @Suppress("DEPRECATION")
            requireActivity().baseContext.resources.updateConfiguration(
                config,
                requireActivity().baseContext.resources.displayMetrics
            )
        }
    }

    private fun isArabicLangActive(): Boolean {
        return when (context?.resources?.configuration?.locales!![0].language) {
            Constants.ARABIC_CODE -> true
            else -> false
        }
    }

    private fun setupExposedDropdownMenus() {
        // setup
        val langOptions = listOf(
            resources.getString(R.string.english_language),
            resources.getString(R.string.arabic_language)
        )
        val langAdapter =
            ArrayAdapter(requireContext(), R.layout.item_settings_dropdown_menus, langOptions)
        binding.languageInput.setAdapter(langAdapter)

        // populate
        populateMenusWithOptions()
    }
}
