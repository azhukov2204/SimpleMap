package ru.androidlearning.simplemap.ui.fragment_markers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.androidlearning.simplemap.R
import ru.androidlearning.simplemap.databinding.ItemMarkerBinding
import ru.androidlearning.simplemap.domain.MarkerInfo

class MarkersListAdapter(
    private val onItemClickListener: (MarkerInfo) -> Unit
) :
    ListAdapter<MarkerInfo, MarkersListAdapter.MarkersListViewHolder>(MarkersListDiff) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkersListViewHolder {
        return MarkersListViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_marker, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MarkersListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MarkersListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val viewBinding: ItemMarkerBinding by viewBinding(ItemMarkerBinding::bind)

        fun bind(markerInfo: MarkerInfo) = with(viewBinding) {
            itemView.setOnClickListener { onItemClickListener(markerInfo) }
            itemMarkerTitleTv.text = itemView.context.resources.getString(
                R.string.item_marker_title_tv_text,
                markerInfo.title
            )
            itemMarkerLatLngTv.text = itemView.context.resources.getString(
                R.string.item_marker_lat_lng_tv_text,
                markerInfo.position.latitude.toString(),
                markerInfo.position.longitude.toString()
            )
        }
    }
}
