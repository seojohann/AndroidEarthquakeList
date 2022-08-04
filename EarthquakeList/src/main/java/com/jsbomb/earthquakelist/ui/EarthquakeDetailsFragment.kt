package com.jsbomb.earthquakelist.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jsbomb.earthquakelist.databinding.FragmentEarthquakeDetailsBinding


class EarthquakeDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEarthquakeDetailsBinding
    private val viewModel: EarthquakeDetailsViewModel by viewModels {
        EarthquakeDetailsViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEarthquakeDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireArguments().let { arguments ->
            val time = arguments.getLong(ARG_ITEM_TIME)
            val place = arguments.getString(ARG_ITEM_PLACE)
            val mag = arguments.getFloat(ARG_ITEM_MAG)
            val longitude = arguments.getFloat(ARG_ITEM_LONG)
            val latitude = arguments.getFloat(ARG_ITEM_LAT)
            val tsunami = arguments.getInt(ARG_ITEM_TSUNAMI)

            viewModel.setEarthquakeDetails(
                time,
                place,
                mag.toDouble(),
                longitude.toDouble(),
                latitude.toDouble(),
                tsunami
            )
        }

        viewModel.earthquakeDetails.observe(viewLifecycleOwner) { details ->
            details?.let {
                binding.details = it
            }
        }

        viewModel.queryString.observe(viewLifecycleOwner) { uriString ->
            uriString?.let {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                viewModel.onQueryLocationStart()
            }
        }

        binding.context = requireContext()
        binding.lifecycleOwner = viewLifecycleOwner

        binding.placeView.setOnClickListener {
            viewModel.onQueryLocation()
        }

        binding.coordinatesView.setOnClickListener {
            viewModel.onQueryLocation()
        }
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
    }
}