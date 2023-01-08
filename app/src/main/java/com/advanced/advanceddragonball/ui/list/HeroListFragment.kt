package com.advanced.advanceddragonball.ui.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.advanced.advanceddragonball.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter = HeroListAdapter {
        Log.d("Adapter click", it.toString())
        //TODO: New fragment detail
//        findNavController().navigate(R.id.action_HeroListFragment_to_HeroDetailFragment)
        findNavController().navigate(
            HeroListFragmentDirections.actionHeroListFragmentToHeroDetailFragment(
                    it,
                    it.name
            )
        )
    }

    private val viewModel: HeroListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)
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

            viewModel.error.observe(viewLifecycleOwner) {error ->
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
            }

            viewModel.state.observe(viewLifecycleOwner){ state ->
                when(state){
                    is HeroListState.Failure -> {
                        Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                    }
                    is HeroListState.Success -> {
                        adapter.submitList(state.heroes)
                    }
                    is HeroListState.NetworkFailure -> {
                    }
                }
            }

            viewModel.getHeroes()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}