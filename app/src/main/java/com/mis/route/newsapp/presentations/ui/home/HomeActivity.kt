package com.mis.route.newsapp.presentations.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mis.route.newsapp.Constants
import com.mis.route.newsapp.R
import com.mis.route.newsapp.databinding.ActivityHomeBinding
import com.mis.route.newsapp.presentations.ui.home.fragments.catergories.CategoriesFragment
import com.mis.route.newsapp.presentations.ui.home.fragments.news.NewsFragment
import com.mis.route.newsapp.presentations.ui.home.fragments.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val categoriesFragment = CategoriesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showFragment(CategoriesFragment()) // by default
        categoriesFragment.onCategoryPicked = CategoriesFragment.OnCategoryPicked {
            showFragment(NewsFragment(it))
        }

        binding.topAppBar.setNavigationOnClickListener { binding.drawerLayout.open() }

        binding.searchIcon.setOnClickListener {
            Toast.makeText(this@HomeActivity, "Search Icon Clicked", Toast.LENGTH_SHORT).show()
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

    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun onCategoryClick(view: View) {
        showFragment(
            NewsFragment(
                when (view.id) {
                    R.id.sports_container -> Constants.SPORTS_CATEGORY
                    R.id.politics_container -> Constants.POLITICS_CATEGORY
                    R.id.health_container -> Constants.HEALTH_CATEGORY
                    R.id.business_container -> Constants.BUSINESS_CATEGORY
                    R.id.environment_container -> Constants.ENVIRONMENT_CATEGORY
                    else -> Constants.SCIENCE_CATEGORY
                }
            )
        )
    }
}