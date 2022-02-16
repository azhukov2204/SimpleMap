package ru.androidlearning.simplemap.ui.fragment_markers

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.androidlearning.simplemap.R
import ru.androidlearning.simplemap.databinding.DialogEnterMarkerTitleBinding
import ru.androidlearning.simplemap.domain.MarkerInfo

class EditMarkerTitleDialog : DialogFragment(R.layout.dialog_enter_marker_title) {

    var onEnterTitleListener: ((MarkerInfo) -> Unit)? = null
    private val viewBinding: DialogEnterMarkerTitleBinding by viewBinding(DialogEnterMarkerTitleBinding::bind)
    private val markerInfo: MarkerInfo? by lazy { arguments?.getParcelable(MARKER_INFO_KEY) }

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
        enterMarkerTitleEt.setText(markerInfo?.title.orEmpty())
        enterMarkerTitleOkBtn.setOnClickListener {
            markerInfo?.let { markerInfo ->
                onEnterTitleListener?.invoke(
                    (markerInfo.copy(
                        title = enterMarkerTitleEt.text.toString()
                    ))
                )
                dismiss()
            }
        }
        enterMarkerTitleCancelBtn.setOnClickListener { dismiss() }
    }

    companion object {
        private const val MARKER_INFO_KEY = "MarkerInfo"

        fun newInstance(markerInfo: MarkerInfo) = EditMarkerTitleDialog().apply {
            arguments = bundleOf(
                MARKER_INFO_KEY to markerInfo
            )
        }
    }
}
