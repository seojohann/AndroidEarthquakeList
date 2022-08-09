package com.jsbomb.earthquakelist.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jsbomb.earthquakelist.databinding.FragmentEarthquakeDetailsBinding


class EarthquakeDetailsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentEarthquakeDetailsBinding
    private val viewModel: EarthquakeDetailsViewModel by viewModels {
        EarthquakeDetailsViewModelFactory()
    }
    private var map: GoogleMap? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEarthquakeDetailsBinding.inflate(inflater, container, false)

        binding.mapView?.onCreate(savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.earthquakeDetails.observe(viewLifecycleOwner) { details ->
            details?.let {
                binding.details = it
                val marker = LatLng(it.latitude, it.longitude)
                map?.let { googleMap ->
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(marker)
                            .title(it.place)
                    )

                    googleMap.moveCamera(CameraUpdateFactory.zoomTo(4f))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
                }
            }
        }

        viewModel.goToWebClicked.observe(viewLifecycleOwner) { url ->
            url?.let {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(webIntent)
                viewModel.onPostLinkClicked()
            }
        }

        binding.context = requireContext()
        binding.lifecycleOwner = viewLifecycleOwner

        binding.gotoLinkText?.setOnClickListener {
            onGoToLinkClicked()
        }

        binding.mapView?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map

        requireArguments().let { arguments ->
            val time = arguments.getLong(ARG_ITEM_TIME)
            val place = arguments.getString(ARG_ITEM_PLACE)
            val mag = arguments.getFloat(ARG_ITEM_MAG)
            val longitude = arguments.getFloat(ARG_ITEM_LONG)
            val latitude = arguments.getFloat(ARG_ITEM_LAT)
            val tsunami = arguments.getInt(ARG_ITEM_TSUNAMI)
            val url = arguments.getString(ARG_ITEM_URL)

            viewModel.setEarthquakeDetails(
                time,
                place,
                mag.toDouble(),
                longitude.toDouble(),
                latitude.toDouble(),
                tsunami,
                url
            )
        }
    }

    override fun onStart() {
        super.onStart()

        binding.mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()

        binding.mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()

        binding.mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()

        binding.mapView?.onStop()
    }


    override fun onDestroy() {
        super.onDestroy()

        binding.mapView?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        binding.mapView?.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()

        binding.mapView?.onLowMemory()
    }

    private fun onGoToLinkClicked() {
        viewModel.onLinkClicked()
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "id"
        const val ARG_ITEM_TIME = "time"
        const val ARG_ITEM_PLACE = "place"
        const val ARG_ITEM_MAG = "mag"
        const val ARG_ITEM_LONG = "longitude"
        const val ARG_ITEM_LAT = "latitude"
        const val ARG_ITEM_TSUNAMI = "tsunami"
        const val ARG_ITEM_URL = "url"
    }
}