package ru.androidlearning.simplemap.ui.fragment_map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.Router
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.androidlearning.simplemap.R
import ru.androidlearning.simplemap.core.UiState
import ru.androidlearning.simplemap.databinding.FragmentMapBinding
import ru.androidlearning.simplemap.domain.MarkerInfo
import ru.androidlearning.simplemap.navigation.MarkersFragmentScreen

class MapFragment : CheckFineLocationPermissionFragment(R.layout.fragment_map) {
    private val viewBinding: FragmentMapBinding by viewBinding(FragmentMapBinding::bind)
    private val viewModel: MapViewModel by viewModel()
    private val router: Router by inject()
    private var googleMap: GoogleMap? = null
    private var markers: MutableList<Marker> = mutableListOf()

    private val onMapReadyCallback: OnMapReadyCallback by lazy {
        OnMapReadyCallback { googleMap ->
            this.googleMap = googleMap
            googleMap.uiSettings.isZoomControlsEnabled = true
            checkPermissionToFineLocation()
            googleMap.setOnMapClickListener { latLng -> openEnterMarkerTitleDialog(latLng) }
            viewModel.getAllMarkers()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllMarkers()
    }

    override fun initViews() {
        initMaps()
        initToolbar()
    }

    private fun initMaps() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapContainer) as? SupportMapFragment
        mapFragment?.getMapAsync(onMapReadyCallback)
    }

    private fun initToolbar() {
        (context as AppCompatActivity).apply {
            setSupportActionBar(viewBinding.mapToolbar)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_map_fragment_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.item_open_markers_list -> {
                router.navigateTo(MarkersFragmentScreen)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun observeToLiveData() {
        viewModel.getLiveData().observe(viewLifecycleOwner) { uiState ->
            render(uiState)
        }
    }

    private fun render(uiState: UiState<List<MarkerInfo>>) {
        when (uiState) {
            is UiState.Success -> doOnSuccess(uiState.data)
            is UiState.Error -> doOnError(uiState.error)
        }
    }

    private fun doOnSuccess(markersInfo: List<MarkerInfo>) {
        markers.forEach { marker ->
            marker.remove()
        }
        markers.clear()
        markersInfo.forEach { markerInfo ->
            val marker = googleMap?.addMarker(MarkerOptions().position(markerInfo.position).title(markerInfo.title))
            marker?.let { markers.add(marker) }
        }
    }

    private fun doOnError(error: Throwable) {
        Toast.makeText(requireContext(), getString(R.string.error_message_prefix) + error.message, Toast.LENGTH_SHORT)
            .show()
    }

    override fun doOnFineLocationPermissionGranted() {
        val isPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        googleMap?.apply {
            isMyLocationEnabled = isPermissionGranted
            uiSettings.isMyLocationButtonEnabled = isPermissionGranted
        }
    }

    override fun doOnLocationChanged(location: Location) {
        val position = LatLng(location.latitude, location.longitude)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(position, DEFAULT_ZOOM))
    }

    private fun openEnterMarkerTitleDialog(latLng: LatLng) {
        EnterMarkerTitleDialog.newInstance(latLng)
            .apply {
                onEnterTitleListener = ::saveMarker
            }
            .show(childFragmentManager, ENTER_MARKER_TITLE_DIALOG_TAG)
    }

    private fun saveMarker(markerInfo: MarkerInfo) {
        viewModel.saveMarker(markerInfo)
    }

    companion object {
        private const val DEFAULT_ZOOM = 10f
        private const val ENTER_MARKER_TITLE_DIALOG_TAG = "EnterMarkerTitleDialog"

        fun newInstance() = MapFragment()
    }
}