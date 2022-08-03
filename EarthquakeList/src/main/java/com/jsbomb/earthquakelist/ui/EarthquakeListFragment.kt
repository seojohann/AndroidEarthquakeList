package com.jsbomb.earthquakelist.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jsbomb.earthquakelist.R
import com.jsbomb.earthquakelist.actors.DataRetriever
import com.jsbomb.earthquakelist.actors.DataRetriever.OnRetrieveCompleteListener
import com.jsbomb.earthquakelist.actors.EarthquakeDataRetriever
import com.jsbomb.earthquakelist.actors.EarthquakeDataViaVolleyRetriever
import com.jsbomb.earthquakelist.data.EarthquakeData
import com.jsbomb.earthquakelist.data.EarthquakesSummary
import com.jsbomb.earthquakelist.databinding.FragmentEarthquakeListBinding
import com.jsbomb.earthquakelist.ui.EarthquakeListFragmentDirections.Companion.actionToEarthquakeDetails
import java.net.URL

class EarthquakeListFragment : Fragment() {

    private lateinit var binding: FragmentEarthquakeListBinding
    private var magnitudeFilter = MagnitudeFilter.ALL

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

        gatherEarthquakeData()
    }

    /**
     * connect to USGS and retrieve earthquake data. it would be a good idea to prevent connecting
     * on every onResume(), but will consider that in the future
     */
    private fun gatherEarthquakeData() {
        // utilize viewmodel/repository in the next change
        Log.d("JSBOMB", "gathering earthquake data from the serer")

        // choose which method to retrieve. httpurlconnection
        var dataRetriever: DataRetriever = EarthquakeDataRetriever()
        // or volley, released from google
        dataRetriever = EarthquakeDataViaVolleyRetriever()
        val urlString = getString(URL_RESOURCES[magnitudeFilter.mIndex])
        try {
            val url = URL(urlString)
            dataRetriever.setUrlString(urlString)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Snackbar.make(binding.root, "invalid URL set", Snackbar.LENGTH_SHORT).show()
            return
        }
        val onRetrieveCompleteListener: OnRetrieveCompleteListener =
            object : OnRetrieveCompleteListener {
                override fun onRetrieveComplete(isSuccess: Boolean, retrievedData: Any?) {
                    Log.d("JSBOMB", "on retrieve complete - success($isSuccess)")
//                    requireActivity().runOnUiThread {
                    Log.d("JSBOMB", "on retrieve complete - on ui thread")
                    if (isSuccess && retrievedData is EarthquakesSummary) {
                        val (_, _, earthquakeDataList) = retrievedData
                        if (earthquakeDataList.isEmpty()) {
                            Log.d("JSBOMB", "on ui thread - empty list")
                            displayEmptyList()
                        } else {
                            Log.d("JSBOMB", "on ui thread - display list")
                            displayUpdatedData(earthquakeDataList)
                        }
                    } else if (!isSuccess) {
                        Log.d("JSBOMB", "on ui thread - retrieval failed")
                        displayRetrievalFailed()
                    } else {
                        Log.d("JSBOMB", "on ui thread - display empty list")
                        displayEmptyList()
                    }
//                    }
                }
            }
        dataRetriever.setOnRetrieveCompleteListener(onRetrieveCompleteListener)
        dataRetriever.retrieve(requireContext())
    }

    private fun displayUpdatedData(updatedData: List<EarthquakeData>) {
        Log.d("JSBOMB", "display updated list")
        adapter.submitList(updatedData)

        binding.earthquakeList.visibility = View.VISIBLE
        binding.listEmptyText.visibility = View.GONE
        binding.retrievalFailedLayout.root.visibility = View.GONE

        requireActivity().actionBar?.setTitle(SUBTITLE_RESOURCES[magnitudeFilter.mIndex])
    }

    private fun displayEmptyList() {
        Log.d("JSBOMB", "display empty list")
        adapter.submitList(null)

        binding.earthquakeList.visibility = View.GONE
        binding.listEmptyText.visibility = View.VISIBLE
        binding.retrievalFailedLayout.root.visibility = View.GONE

        requireActivity().actionBar?.setTitle(SUBTITLE_RESOURCES[magnitudeFilter.mIndex])
    }

    private fun displayRetrievalFailed() {
        Log.d("JSBOMB", "display on retrieval failed")
        adapter.submitList(null)

        binding.earthquakeList.visibility = View.GONE
        binding.listEmptyText.visibility = View.GONE
        binding.retrievalFailedLayout.root.visibility = View.VISIBLE

        binding.retrievalFailedLayout.retryButton.setOnClickListener { gatherEarthquakeData() }
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
                    tsunami = props.tsunami!!
                )
            findNavController().navigate(navAction)
        }
    }

    interface FilterSelectedListener {
        fun onFilterSelected(which: Int)
    }

    class SelectMagnitudeDialog : DialogFragment() {
        var mListener: FilterSelectedListener? = null
        fun setFilterSelectedListener(listener: FilterSelectedListener?) {
            mListener = listener
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val dialogBuilder = AlertDialog.Builder(requireActivity())

            dialogBuilder.setTitle(R.string.select_dialog_title)
            dialogBuilder.setItems(R.array.magnitude_items) { _, which ->
                if (mListener != null) {
                    mListener!!.onFilterSelected(which)
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

        enum class MagnitudeFilter(val mIndex: Int) {
            ALL(0),
            MAG_10(1), // 1.0
            MAG_25(2), // 2.5
            MAG_45(3), // 4.5
            SIG(4) // significant
        }
    }
}