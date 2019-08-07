package com.cloniamix.lesson12engurazovkotlin.mvp.screens.bridgedetailsscreen

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.cloniamix.lesson12engurazovkotlin.R
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.basemodel.pojo.Bridge
import com.cloniamix.lesson12engurazovkotlin.di.ApplicationComponents
import com.cloniamix.lesson12engurazovkotlin.mvp.contract.baseview.BridgeDetailsMvpView
import kotlinx.android.synthetic.main.bridge_details_fragment.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.view_bridge_item.*

class BridgeDetailsFragment :
    Fragment(),
    BridgeDetailsMvpView {

    companion object {
        private const val ARG_IMAGE_RES_ID = "param1"

        fun newInstance(bridgeId: Int): BridgeDetailsFragment {
            val bridgeDetailsFragment =
                BridgeDetailsFragment()
            val args = Bundle()
            args.putInt(ARG_IMAGE_RES_ID, bridgeId)

            bridgeDetailsFragment.arguments = args

            return bridgeDetailsFragment
        }
    }

    private var bridgeId: Int = -1
    private var listener: OnBridgeDetailsFragmentInteractionListener? = null
    private val bridgeDetailsFragmentPresenter =
        ApplicationComponents.getInstance().provideBridgeDetailsPresenter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBridgeDetailsFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnBridgeDetailsFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            bridgeId = (arguments as Bundle).getInt(ARG_IMAGE_RES_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bridge_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        textViewError.setOnClickListener { bridgeDetailsFragmentPresenter.onRefresh(bridgeId) }
        toolbarBridgeDetail.setNavigationOnClickListener { listener?.menuBackClicked() }
        bridgeDetailsFragmentPresenter.attachView(this)
        bridgeDetailsFragmentPresenter.onViewCreated(bridgeId)
    }

    override fun onDetach() {
        listener = null
        bridgeDetailsFragmentPresenter.detachView()
        super.onDetach()
    }

    override fun showBridgeDetails(bridge: Bridge) {
        textViewBridgeName.text = bridge.name
        textViewTime.text = bridgeDetailsFragmentPresenter.getDivorceTime(bridge)
        imageViewStatusIcon.setImageResource(bridgeDetailsFragmentPresenter.getStatusIconResId(bridge))

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textViewDescription.text = Html.fromHtml(bridge.description, Html.FROM_HTML_MODE_LEGACY)
        } else {
            textViewDescription.text = Html.fromHtml(bridge.description)
        }

        setBridgePhoto(bridge)
    }

    override fun showState(stateFlag: Int) {
        viewFlipperBridgeDetailsStates.displayedChild = stateFlag
    }

    private fun setBridgePhoto(bridge: Bridge) {
        Glide.with(this)
            .load(bridgeDetailsFragmentPresenter.getBridgePhotoUrl(bridge))
            .centerCrop()
            .placeholder(R.drawable.ic_no_content)
            .into(imageViewBridgePhoto)
    }


    interface OnBridgeDetailsFragmentInteractionListener {
        fun menuBackClicked()
    }
}