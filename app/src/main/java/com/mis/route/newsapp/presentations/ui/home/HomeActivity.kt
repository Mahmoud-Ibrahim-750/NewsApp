package com.mis.route.newsapp.presentations.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.mis.route.newsapp.Constants
import com.mis.route.newsapp.DrawableClickListener
import com.mis.route.newsapp.R
import com.mis.route.newsapp.databinding.ActivityHomeBinding
import com.mis.route.newsapp.presentations.ui.home.fragments.catergories.CategoriesFragment
import com.mis.route.newsapp.presentations.ui.home.fragments.news.NewsFragment
import com.mis.route.newsapp.presentations.ui.home.fragments.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("ClickableViewAccessibility")
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val categoriesFragment = CategoriesFragment()
    private var newsFragment: NewsFragment? = null

    private fun toggleSearchViewVisibility() {
        binding.searchBar.isVisible = !(binding.searchBar.isVisible)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showFragment(CategoriesFragment()) // default fragment

        binding.topAppBar.setNavigationOnClickListener { binding.drawerLayout.open() }

        binding.searchIcon.setOnClickListener {
            newsFragment?.apply {
                if (isVisible) toggleSearchViewVisibility()
            }

        }

        binding.navigationView.setNavigationItemSelectedListener {
            showFragment(
                when (it.itemId) {
                    R.id.categories_nav_tab -> categoriesFragment
                    else -> SettingsFragment()
                }
            )
            binding.drawerLayout.close()
            true
        }

        binding.searchBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                newsFragment?.performSearch(binding.searchBar.text.toString())
            }
            true
        }

        binding.searchBar.setOnTouchListener(
            object : DrawableClickListener.RightDrawableClickListener(binding.searchBar) {
                override fun onDrawableClick(): Boolean {
                    toggleSearchViewVisibility()
                    return true
                }
            })
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun onCategoryClick(view: View) {
        newsFragment = NewsFragment(
            when (view.id) {
                R.id.sports_container -> Constants.SPORTS_CATEGORY
                R.id.politics_container -> Constants.POLITICS_CATEGORY
                R.id.health_container -> Constants.HEALTH_CATEGORY
                R.id.business_container -> Constants.BUSINESS_CATEGORY
                R.id.environment_container -> Constants.ENVIRONMENT_CATEGORY
                else -> Constants.SCIENCE_CATEGORY
            }
        )

        showFragment(newsFragment!!)
    }
}
