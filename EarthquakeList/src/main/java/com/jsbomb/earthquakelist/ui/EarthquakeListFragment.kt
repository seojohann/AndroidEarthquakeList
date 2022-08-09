package com.jsbomb.earthquakelist.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jsbomb.earthquakelist.R
import com.jsbomb.earthquakelist.data.EarthquakeData
import com.jsbomb.earthquakelist.databinding.FragmentEarthquakeListBinding
import com.jsbomb.earthquakelist.rest.summaryApi
import com.jsbomb.earthquakelist.ui.EarthquakeListFragmentDirections.Companion.actionToEarthquakeDetails

class EarthquakeListFragment : Fragment() {

    private lateinit var binding: FragmentEarthquakeListBinding
    private var magnitudeFilter = MagnitudeFilter.ALL
    private val viewModel: EarthquakeListViewModel by viewModels {
        EarthquakeListViewModelFactory(networkApi = summaryApi)
    }

    private val adapter: EarthquakeListAdapter by lazy {
        EarthquakeListAdapter { earthquakeData ->
            onEarthquakeClick(earthquakeData)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("JSBOMB", "earthquakelist oncreateview")
        binding = FragmentEarthquakeListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("JSBOMB", "earthquakelist onviewcreated")

        binding.earthquakeList.adapter = adapter
        binding.earthquakeList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.earthquakes.observe(viewLifecycleOwner) { earthquakeList ->
            earthquakeList?.let {
                displayUpdatedData(it)
            }
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.main_menu_filter -> {

                        val selectMagnitudeDialog = SelectMagnitudeDialog()
                        selectMagnitudeDialog.onFilterSelected = { which ->
                            viewModel.setMagnitudeFilter(which)
                        }

                        selectMagnitudeDialog.show(parentFragmentManager, "select");
                        true
                    }

                    R.id.main_menu_view_ad -> {
//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                }
                        true
                    }
                    R.id.main_menu_menu -> {
                        findNavController().navigate(EarthquakeListFragmentDirections.actionToGdaxFragment())
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    private fun displayUpdatedData(updatedData: List<EarthquakeData>) {
        Log.d("JSBOMB", "display updated list")
        adapter.submitList(updatedData)

        if (updatedData.isEmpty()) {
            binding.listEmptyText.visibility = View.VISIBLE
            binding.retrievalFailedLayout.root.visibility = View.GONE
        } else {
            binding.earthquakeList.visibility = View.VISIBLE
            binding.listEmptyText.visibility = View.GONE
            binding.retrievalFailedLayout.root.visibility = View.GONE
        }

        requireActivity().actionBar?.setTitle(SUBTITLE_RESOURCES[magnitudeFilter.mIndex])
    }

    private fun onEarthquakeClick(earthquake: EarthquakeData) {
        // utilize viewmodel in the next change
        earthquake.properties?.let { props ->
            val navAction =
                actionToEarthquakeDetails(
                    time = props.time!!,
                    place = props.place!!,
                    mag = props.mag!!.toFloat(),
                    longitude = earthquake.geometry!!.getLongitude()!!.toFloat(),
                    latitude = earthquake.geometry!!.getLatitude()!!.toFloat(),
                    tsunami = props.tsunami!!,
                    url = props.url
                )
            findNavController().navigate(navAction)
        }
    }

    class SelectMagnitudeDialog : DialogFragment() {
        var onFilterSelected: OnFilterSelected? = null

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val dialogBuilder = AlertDialog.Builder(requireActivity())

            dialogBuilder.setTitle(R.string.select_dialog_title)
            dialogBuilder.setItems(R.array.magnitude_items) { _, which ->
                onFilterSelected?.let {
                    it(MagnitudeFilter.getFilter(which))
                }
            }
            return dialogBuilder.create()
        }
    }

    companion object {
        private val URL_RESOURCES = intArrayOf(
            R.string.quakes_feed_all_day_url,
            R.string.quakes_feed_mag_1_0_day_url,
            R.string.quakes_feed_mag_2_5_day_url,
            R.string.quakes_feed_mag_4_5_day_url,
            R.string.quakes_feed_significant_day_url
        )

        private val SUBTITLE_RESOURCES = intArrayOf(
            R.string.list_subtitle_all,
            R.string.list_subtitle_m10,
            R.string.list_subtitle_m25,
            R.string.list_subtitle_m45,
            R.string.list_subtitle_significant
        )

    }
}

typealias OnFilterSelected = (MagnitudeFilter) -> Unit


enum class MagnitudeFilter(var mIndex: Int) {
    ALL(0),
    MAG_10(1), // 1.0
    MAG_25(2), // 2.5
    MAG_45(3), // 4.5
    SIG(4); // significant

    companion object {
        fun getFilter(value: Int): MagnitudeFilter {
            return when (value) {
                1 -> MAG_10
                2 -> MAG_25
                3 -> MAG_45
                4 -> SIG
                else -> ALL
            }
        }
    }
}