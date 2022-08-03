package com.jsbomb.earthquakelist.ui

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
            val mag = arguments.getDouble(ARG_ITEM_MAG)
            val longitude = arguments.getDouble(ARG_ITEM_LONGI)
            val latitude = arguments.getDouble(ARG_ITEM_LATTI)
            val tsunami = arguments.getInt(ARG_ITEM_TSUNAMI)

            viewModel.setEarthquakeDetails(time, place, mag, longitude, latitude, tsunami)
        }

        viewModel.earthquakeDetails.observe(viewLifecycleOwner) { details ->
            details?.let {
                binding.details = it
            }
        }

        binding.context = requireContext()
        binding.lifecycleOwner = viewLifecycleOwner
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
        const val ARG_ITEM_TIME = "item_time"
        const val ARG_ITEM_PLACE = "item_place"
        const val ARG_ITEM_MAG = "item_mag"
        const val ARG_ITEM_LONGI = "item_longi"
        const val ARG_ITEM_LATTI = "item_latti"
        const val ARG_ITEM_TSUNAMI = "item_tsunami"
    }
}