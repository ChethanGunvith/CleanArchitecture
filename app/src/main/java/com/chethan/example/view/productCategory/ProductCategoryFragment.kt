package com.chethan.example.view.productCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chethan.common.autoCleared
import com.chethan.example.databinding.ProductCategoryFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import testing.OpenForTesting

/**
 * Created by Chethan on 10/12/2021.
 */

@OpenForTesting
@AndroidEntryPoint
class ProductCategoryFragment : Fragment() {
    private val productCategoryViewModel: ProductCategoryViewModel by viewModels()

    var binding by autoCleared<ProductCategoryFragmentBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductCategoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.productCategory = productCategoryViewModel.productCategories
        productCategoryViewModel.productCategories.observe(viewLifecycleOwner) { result ->
            result.data?.let { listOfProductCategories ->
                if (listOfProductCategories.isNotEmpty()) {
                    binding.productCategoryViewPager.adapter =
                        ProductCategoryPagerAdapter(listOfProductCategories, this)
                    TabLayoutMediator(
                        binding.tabs,
                        binding.productCategoryViewPager
                    ) { tab, position ->
                        tab.text = listOfProductCategories[position].name
                    }.attach()
                }
            }
        }
    }
}
