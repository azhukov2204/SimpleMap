package ru.androidlearning.simplemap.ui.fragment_markers

import androidx.recyclerview.widget.DiffUtil
import ru.androidlearning.simplemap.domain.MarkerInfo

object MarkersListDiff : DiffUtil.ItemCallback<MarkerInfo>() {
    override fun areItemsTheSame(oldItem: MarkerInfo, newItem: MarkerInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MarkerInfo, newItem: MarkerInfo): Boolean {
        return oldItem == newItem
    }
}
