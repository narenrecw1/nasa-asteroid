package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.Repository
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    
    private val viewModel :MainViewModel by viewModels(factoryProducer = ::viewModelFactory)

    private val viewModelFactory by lazy{
        val repository = Repository(requireContext().applicationContext)
        val defaultContentDescription = " This is NASA's picture of the day, showing nothing yet"
        val contentDescriptionFormat = " NASA's picture of the day: %s"
        MainViewModelFactory(repository, defaultContentDescription, contentDescriptionFormat)
    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val asteroidAdapter = AsteroidAdapter(this::onAsteroidClicked)
        val binding = FragmentMainBinding.inflate(inflater).apply {
            lifecycleOwner = this@MainFragment
            viewModel = this@MainFragment.viewModel
            asteroidRecycler.adapter = asteroidAdapter
        }
//        binding.lifecycleOwner = this
//
//        binding.viewModel = viewModel
        viewModel.asteroids.observe(viewLifecycleOwner, asteroidAdapter::items::set)

        setHasOptionsMenu(true)

        return binding.root
    }

    fun onAsteroidClicked(asteroid: Asteroid){
        val navigateTo = MainFragmentDirections.actionShowDetail(asteroid)
        findNavController().navigate(navigateTo)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.filer
//        }
        return true
    }
}
