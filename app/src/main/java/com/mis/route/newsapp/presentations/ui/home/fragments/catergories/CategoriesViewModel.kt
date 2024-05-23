package com.mis.route.newsapp.presentations.ui.home.fragments.catergories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoriesViewModel : ViewModel() {

    var category: MutableLiveData<String> = MutableLiveData()

    fun onCategoryClick(category: String) {
        this.category.postValue(category)
    }
}