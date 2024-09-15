package com.example.dogapp.views.home

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.dogapp.databinding.FragmentHomeViewPagerBinding
import com.example.dogapp.views.adapters.DogListImagesAdapter
import com.example.dogapp.views.adapters.HomeViewAdapter
import com.example.dogapp.views.base.BaseFragment
import com.example.dogapp.views.home.uistate.HomeViewUIState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewPagerFragment: BaseFragment<FragmentHomeViewPagerBinding>(
    FragmentHomeViewPagerBinding::inflate
) {
    private val viewModel: HomeViewModel by activityViewModels()
    private val adapter: DogListImagesAdapter = DogListImagesAdapter()

    private var key: String = ""

    override fun postInit() {
        super.postInit()
        initializeUI()
        observeChanges()
    }

    private fun observeChanges() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.uiState.collectLatest {
                    updateUI(it)
                }
            }
        }
    }

    private fun updateUI(state: HomeViewUIState){
        state.breedImages.get(key)?.let {
            adapter.submitList(it)
        }
    }

    private fun initializeUI() {
        binding.rcylrData.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
            adapter = this@HomeViewPagerFragment.adapter
        }

        arguments?.getString("breedName")?.let {
            key = it
            viewModel.getDogBreedImage(it)
        }
    }

    companion object {
        fun newInstance(bundle: Bundle): HomeViewPagerFragment {
            val fragment = HomeViewPagerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}