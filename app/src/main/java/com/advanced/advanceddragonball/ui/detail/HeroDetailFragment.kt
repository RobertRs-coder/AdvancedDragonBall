package com.advanced.advanceddragonball.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.advanced.advanceddragonball.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroDetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: HeroDetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                is HeroDetailState.Success -> {

                    binding.imageHeroDetail.load(it.hero.photo)
                    binding.heroName.text = it.hero.name
                    binding.descriptionHeroDetail.text = it.hero.description

                }
                else -> {

                }
            }
        }

        viewModel.getHeroDetail(args.hero)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}