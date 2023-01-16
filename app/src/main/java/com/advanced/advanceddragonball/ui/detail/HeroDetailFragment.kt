package com.advanced.advanceddragonball.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.android.gms.maps.model.MarkerOptions
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


    private fun setObservers() {
        viewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                is HeroDetailState.Success -> {

                    setHero(it.hero)
                    setHeroLocations(it.hero)
                    zoomToFirstPosition(it.hero)
                }

                else -> {}

            }
        }
    }

    private fun setUpMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        val sydney = LatLng(-34.0, 151.0)
        map.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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
        heroLocations?.forEach { map.addMarker(viewModel.getMarker(it)) }
    }

    private fun zoomToFirstPosition(hero: Hero) {
        val firstHeroLocation = hero.locations?.first()
        firstHeroLocation?.apply {
            val position = LatLng(
                firstHeroLocation.latitude.toDouble(),
                firstHeroLocation.longitude.toDouble()
            )
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 6F))
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}