package com.advanced.advanceddragonball.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.advanced.advanceddragonball.R
import com.advanced.advanceddragonball.databinding.FragmentDetailBinding
import com.advanced.advanceddragonball.domain.Hero
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroDetailFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: HeroDetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getHeroDetail(args.hero)

        setUpMap()

        setObservers()
    }

    private fun setHeroFavorite(hero: Hero) {
        when(hero.favorite) {
            true -> binding.favoriteButton.setImageResource(R.drawable.ic_heart_full)
            false -> binding.favoriteButton.setImageResource(R.drawable.ic_heart_empty)
        }
    }

    private fun setListeners(hero: Hero) {
        with(binding) {
            favoriteButton.setOnClickListener {
                viewModel.switchHeroLike()
            }
        }
        setHeroFavorite(hero)
    }

    private fun setObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when(state) {

                is HeroDetailState.Success -> {
                    setHero(state.hero)
                    setListeners(state.hero)
                    setHeroLocations(state.hero)
                    zoomToFirstPosition(state.hero)
                }

                is HeroDetailState.Failure -> {
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
                }

                is HeroDetailState.NetworkError -> {
                    Toast.makeText(requireContext(), state.code, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setUpMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
    }

    private fun setHero(hero: Hero){
        binding.imageHeroDetail.load(hero.photo) {
            placeholder(R.drawable.enigma_image)
        }
        binding.heroName.text = hero.name
        binding.descriptionHeroDetail.text = hero.description
    }

    private fun setHeroLocations(hero: Hero) {
        val heroLocations = hero.locations
        if (hero.locations?.isNotEmpty() == true) {
            heroLocations?.forEach { map.addMarker(viewModel.getHeroLocation(it)) }
        }
    }

    private fun zoomToFirstPosition(hero: Hero) {

        if (hero.locations?.isNotEmpty() == true) {
            val firstHeroLocation = hero.locations?.first()
            firstHeroLocation?.apply {
                val position = LatLng(
                    firstHeroLocation.latitude.toDouble(),
                    firstHeroLocation.longitude.toDouble()
                )
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 6F))
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}