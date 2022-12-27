package com.advanced.advanceddragonball.ui.herolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.advanced.advanceddragonball.databinding.FragmentHeroListBinding
import com.advanced.advanceddragonball.domain.Hero
import java.util.UUID

class HeroListFragment : Fragment() {

    private var _binding: FragmentHeroListBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter = HeroListAdapter()

    private val viewModel: HeroListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHeroListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            heroList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            heroList.adapter = adapter
            adapter.submitList(getHeroes(1000))

            fab.setOnClickListener {
                viewModel.tareaCostosa()
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getHeroes(size: Int): List<Hero> {
        val heroes = mutableListOf<Hero>()

        for (i in 0 .. size) {
            heroes.add(Hero("Hero $i",UUID.randomUUID().toString()))
        }

        return heroes
    }
}