package com.example.dogapp.views.home

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dogapp.databinding.FragmentHomeBinding
import com.example.dogapp.views.adapters.HomeViewAdapter
import com.example.dogapp.views.base.BaseFragment
import com.example.dogapp.views.home.uistate.HomeViewUIState
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
), TabLayout.OnTabSelectedListener {

    private lateinit var homeAdapter: HomeViewAdapter

    private val viewModel: HomeViewModel by activityViewModels()

    override fun postInit() {
        super.postInit()
        initializeUI()
        observeChanges()
    }

    private fun observeChanges() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collectLatest {
                    updateUI(it)
                }
            }
        }
    }

    private fun initializeUI() {
        homeAdapter = HomeViewAdapter(requireActivity())
        binding.viewPagerList.adapter = homeAdapter
        binding.dogsTab.addOnTabSelectedListener(this@HomeFragment)
        TabLayoutMediator(binding.dogsTab, binding.viewPagerList) { tab, position ->
            tab.text = homeAdapter.getTabTitle(position)
        }.attach()
        viewModel.getAllDogBreeds()
    }

    private fun updateUI(state: HomeViewUIState) {
        if (!state.isLoading) {
            if(homeAdapter.itemCount == 0) {
                state.breedImages.keys.sortedBy { it }.forEach { name ->
                    homeAdapter.addTabPage(name)
                }
                binding.viewPagerList.setCurrentItem(0,true)
                homeAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onTabSelected(p0: TabLayout.Tab?) {
        p0?.let {
            binding.viewPagerList.setCurrentItem(p0.position,true)
        }
    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {

    }

    override fun onTabReselected(p0: TabLayout.Tab?) {

    }
}