package com.advanced.advanceddragonball.ui.herolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.advanced.advanceddragonball.databinding.FragmentHeroListBinding

class HeroListFragment : Fragment() {
    private var _binding: FragmentHeroListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter = HeroListAdapter()

    private val viewModel: HeroListViewModel by viewModels{ HeroListViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHeroListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            heroList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            heroList.adapter = adapter

            viewModel.heroes.observe(viewLifecycleOwner) {heroList ->
                adapter.submitList(heroList)
            }

//            // Init database from viewModel -> Not it isn't use it
//            viewModel.initDatabase(requireContext())
            // Get heroes from viewModel
            viewModel.getHeroes()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}