package ru.androidlearning.simplemap.ui.fragment_markers

import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.androidlearning.simplemap.R
import ru.androidlearning.simplemap.core.BaseMVVMFragment
import ru.androidlearning.simplemap.core.UiState
import ru.androidlearning.simplemap.databinding.FragmentMarkersBinding
import ru.androidlearning.simplemap.domain.MarkerInfo

class MarkersFragment : BaseMVVMFragment(R.layout.fragment_markers) {
    private val router: Router by inject()
    private val viewModel: MarkersViewModel by viewModel()
    private val markersListAdapter: MarkersListAdapter by lazy {
        MarkersListAdapter { markerInfo ->
            println("!!! markerInfo click = $markerInfo")
            EditMarkerTitleDialog.newInstance(markerInfo)
                .apply {
                    onEnterTitleListener = ::updateMarker
                }
                .show(parentFragmentManager, EDIT_MARKER_TITLE_DIALOG_TAG)
        }
    }

    private val viewBinding: FragmentMarkersBinding by viewBinding(FragmentMarkersBinding::bind)

    override fun initViews() {
        initToolbar()
        initRecyclerView()
    }

    private fun initToolbar() = with(viewBinding) {
        (context as AppCompatActivity).apply {
            setSupportActionBar(markersToolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeButtonEnabled(true)
                setDisplayShowTitleEnabled(true)
            }
        }
        setHasOptionsMenu(true)
    }

    private fun initRecyclerView() = with(viewBinding) {
        ItemTouchHelper(ItemTouchHelperCallback()).apply { attachToRecyclerView(markersRv) }
        markersRv.adapter = markersListAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            router.exit()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun observeToLiveData() {
        viewModel.apply {
            getLiveData().observe(viewLifecycleOwner) { uiState ->
                render(uiState)
            }
            getAllMarkers()
        }
    }

    private fun updateMarker(markerInfo: MarkerInfo) {
        viewModel.updateMarker(markerInfo)
    }

    private fun render(uiState: UiState<List<MarkerInfo>>) {
        when (uiState) {
            is UiState.Success -> doOnSuccess(uiState.data)
            is UiState.Error -> doOnError(uiState.error)
        }
    }

    private fun doOnSuccess(markersInfo: List<MarkerInfo>) {
        markersListAdapter.submitList(markersInfo)
    }

    private fun doOnError(error: Throwable) {
        Toast.makeText(requireContext(), getString(R.string.error_message_prefix) + error.message, Toast.LENGTH_SHORT)
            .show()
    }

    inner class ItemTouchHelperCallback : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val currentMarkerInfo = markersListAdapter.currentList[viewHolder.absoluteAdapterPosition]
            viewModel.deleteMarker(currentMarkerInfo)
        }
    }

    companion object {
        private const val EDIT_MARKER_TITLE_DIALOG_TAG = "EditMarkerTitleDialog"
        fun newInstance() = MarkersFragment()
    }
}
