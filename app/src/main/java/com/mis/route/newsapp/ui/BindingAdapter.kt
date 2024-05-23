package com.mis.route.newsapp.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mis.route.newsapp.R

@BindingAdapter("image_url")
fun bindUrlToImage(imageView: ImageView, url: String) {
    //                val progress = CircularProgressDrawable(binding.root.context)
//                progress.isIndeterminate = true
    // TODO: revisit this and change as needed
    Glide
        .with(imageView)
        .load(url)
        .placeholder(R.drawable.ic_app_logo)
        // TODO: code unreachable

//                    .listener(object : RequestListener<Drawable>{
//                        override fun onLoadFailed(
//                            e: GlideException?,
//                            model: Any?,
//                            target: Target<Drawable>,
//                            isFirstResource: Boolean
//                        ): Boolean {
//                            Log.e("GlideException", e.toString())
//                            return false
//                        }
//
//                        override fun onResourceReady(
//                            resource: Drawable,
//                            model: Any,
//                            target: Target<Drawable>?,
//                            dataSource: DataSource,
//                            isFirstResource: Boolean
//                        ): Boolean {
//                            binding.articleImageProgressBar.visibility = View.GONE
//                            binding.articleImage.visibility = View.VISIBLE
//                            return false
//                        }
//
//                    })
        .into(imageView)
}


//@BindingAdapter("passed_category")
//fun passCategory(layout: LinearLayout, category: String, viewModel: CategoriesViewModel) {
//    viewModel.onCategoryClick(category)
//}