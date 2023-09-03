package com.mis.route.newsapp.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mis.route.newsapp.R
import com.mis.route.newsapp.databinding.ActivityHomeBinding
import com.mis.route.newsapp.ui.home.fragments.catergories.CategoriesFragment
import com.mis.route.newsapp.ui.home.fragments.settings.SettingsFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener { binding.drawerLayout.open() }

        binding.searchIcon.setOnClickListener {
            Toast.makeText(this@HomeActivity, "Search Icon Clicked", Toast.LENGTH_SHORT).show()
        }

        binding.navigationView.setNavigationItemSelectedListener {
            showFragment(
                when (it.itemId) {
                    R.id.categories_nav_tab -> CategoriesFragment()
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
}