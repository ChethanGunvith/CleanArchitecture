package com.chethan.example.view.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.chethan.common.AppExecutors
import com.chethan.common.DataBoundListAdapter
import com.chethan.data.model.ProductOverview
import com.chethan.example.databinding.ProductEntryBinding

/**
 * Created by Chethan on 7/28/2019.
 */

class ProductGridAdapter(
    appExecutors: AppExecutors,
) : DataBoundListAdapter<ProductOverview, ProductEntryBinding>(
    appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<ProductOverview>() {
        override fun areItemsTheSame(
            oldItem: ProductOverview,
            newItem: ProductOverview
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ProductOverview,
            newItem: ProductOverview
        ) = oldItem == newItem
    }
) {

    override fun createBinding(parent: ViewGroup): ProductEntryBinding =
        ProductEntryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

    override fun bind(
        binding: ProductEntryBinding,
        item: ProductOverview
    ) {
        binding.productOverview = item
    }
}
