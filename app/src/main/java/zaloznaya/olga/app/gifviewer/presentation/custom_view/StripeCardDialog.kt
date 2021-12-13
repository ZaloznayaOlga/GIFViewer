package zaloznaya.olga.app.gifviewer.presentation.custom_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.stripe.android.Stripe
import com.stripe.android.model.Card
import com.stripe.android.model.Token
import com.stripe.android.view.CardMultilineWidget
import zaloznaya.olga.app.gifviewer.R
import zaloznaya.olga.app.gifviewer.utils.stripe.StripePaymentListener


class StripeCardDialog : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(
            stripePublicKey: String, title: String, saveLabel: String, cancelLabel: String,
            cancelTextColor: Int, saveTextColor: Int, bgColorCancel: Int, bgColorSaveRes: Int
        ): StripeCardDialog {
            val cardDialog = StripeCardDialog()
            val bundle = Bundle()
            bundle.putString("STRIPPUBLICKEY", stripePublicKey)
            bundle.putString("TITLE", title)
            bundle.putString("SAVE_LABEL", saveLabel)
            bundle.putString("CANCEL_LABEL", cancelLabel)

            bundle.putInt("TEXT_COLOR_CANCEL", cancelTextColor)
            bundle.putInt("TEXT_COLOR_SAVE", saveTextColor)

            bundle.putInt("BG_COLOR_CANCEL", bgColorCancel)

            bundle.putInt("BG_COLOR_SAVE_RES", bgColorSaveRes)

            cardDialog.arguments = bundle
            return cardDialog
        }
    }

    private var stripePublicKey: String = ""

    private var title: String? = ""

    private var saveLabel: String? = ""
    private var cancelLabel: String? = ""

    var mListener: StripePaymentListener? = null

    private var btnCancel: AppCompatButton? = null
    private var btnSave: AppCompatButton? = null
    private var txtHeader: AppCompatTextView? = null
    private var cardMultilineWidget: CardMultilineWidget? = null
    private var progressCircular: ProgressBar? = null

    private var cancelTextColor: Int? = null
    private var saveTextColor: Int? = null
    private var bgColorCancel: Int? = null
    private var bgColorSaveRes: Int? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.stripe_card_dialog, container, false)

        if (arguments != null) {
            stripePublicKey = requireArguments().getString("STRIPPUBLICKEY") ?: ""
            title = arguments?.getString("TITLE","Add payment details")

            saveLabel = arguments?.getString("SAVE_LABEL", "SAVE")
            cancelLabel = arguments?.getString("CANCEL_LABEL", "CANCEL")

            cancelTextColor = arguments?.getInt("TEXT_COLOR_CANCEL")
            saveTextColor = arguments?.getInt("TEXT_COLOR_SAVE")

            bgColorCancel = arguments?.getInt("BG_COLOR_CANCEL")
            bgColorSaveRes = arguments?.getInt("BG_COLOR_SAVE_RES")
        }
//        KeyboardUtil(activity, view)
        btnSave = view.findViewById(R.id.save_payment) as AppCompatButton
        btnCancel = view.findViewById(R.id.cancel_payment) as AppCompatButton
        txtHeader = view.findViewById(R.id.payment_form_title) as AppCompatTextView
        cardMultilineWidget = view.findViewById(R.id.card_multiline_widget) as CardMultilineWidget
        progressCircular = view.findViewById(R.id.progress_circular) as ProgressBar

        // setLabel
        //  payment_form_title.text = title
        btnSave?.text = saveLabel
        btnCancel?.text = cancelLabel
        txtHeader?.text = title


        //Set Color if pass
        bgColorSaveRes?.let { btnSave?.setBackgroundColor(it) }
        bgColorCancel?.let { btnCancel?.setBackgroundColor(it) }

        saveTextColor?.let { btnSave?.setTextColor(it) }
        cancelTextColor?.let { btnCancel?.setTextColor(it) }


        btnSave?.setOnClickListener {
            // DO SOMETHING
//            KeyboardUtil.hideKeyboard(activity)
//            var card = cardMultilineWidget?.card
//            if (card != null) {
//                progressCircular?.visibility = View.VISIBLE
//                createToken(card)
//            }
        }

        btnCancel?.setOnClickListener {
            // DO SOMETHING
//            KeyboardUtil.hideKeyboard(activity)
            this@StripeCardDialog.dialog?.hide()
        }

        return view
    }

    private fun createToken(card: Card) {
        val strip = Stripe(this.requireActivity(), stripePublicKey)
//        strip.createToken(card, object : TokenCallback {
//            override fun onSuccess(token: Token) {
//                progressCircular?.visibility = View.GONE
//                mListener?.onStripPaymentInSuccess(token)
//                this@StripeCardDialog.dialog?.hide()
//            }
//
//            override fun onError(error: Exception) {
//                progressCircular?.visibility = View.GONE
//                mListener?.onStripPaymentInFail(error.localizedMessage)
//                this@StripeCardDialog.dialog?.hide()
//            }
//        })


    }

    fun registerCallback(@NonNull mListener: StripePaymentListener) {
        this.mListener = mListener
    }

    override fun onDestroy() {
        super.onDestroy()
        mListener = null
    }
}