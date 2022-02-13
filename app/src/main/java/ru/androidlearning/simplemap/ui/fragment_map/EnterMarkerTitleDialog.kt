package ru.androidlearning.simplemap.ui.fragment_map

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.maps.model.LatLng
import ru.androidlearning.simplemap.R
import ru.androidlearning.simplemap.databinding.DialogEnterMarkerTitleBinding
import ru.androidlearning.simplemap.domain.MarkerInfo

class EnterMarkerTitleDialog : DialogFragment(R.layout.dialog_enter_marker_title) {

    var onEnterTitleListener: ((MarkerInfo) -> Unit)? = null
    private val viewBinding: DialogEnterMarkerTitleBinding by viewBinding(DialogEnterMarkerTitleBinding::bind)
    private val latLng: LatLng? by lazy { arguments?.getParcelable(LAT_LNG_KEY) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        isCancelable = true
    }

    private fun initViews() = with(viewBinding) {
        enterMarkerTitleOkBtn.setOnClickListener {
            latLng?.let { latLng ->
                onEnterTitleListener?.invoke(getMarkerInfo(latLng))
                dismiss()
            }
        }
        enterMarkerTitleCancelBtn.setOnClickListener { dismiss() }
    }

    private fun getMarkerInfo(latLng: LatLng) = with(viewBinding) {
        MarkerInfo(
            position = latLng,
            title = enterMarkerTitleEt.text.toString()
        )
    }


    companion object {
        private const val LAT_LNG_KEY = "LatLng"

        fun newInstance(latLng: LatLng) = EnterMarkerTitleDialog().apply {
            arguments = bundleOf(
                LAT_LNG_KEY to latLng
            )
        }
    }
}
