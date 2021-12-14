package zaloznaya.olga.app.gifviewer.presentation.detail_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.stripe.android.PaymentConfiguration
import com.stripe.android.model.Token
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import zaloznaya.olga.app.gifviewer.R
import zaloznaya.olga.app.gifviewer.databinding.FragmentImageBinding
import zaloznaya.olga.app.gifviewer.domain.model.GifImage
import zaloznaya.olga.app.gifviewer.presentation.adapters.ImageViewPagerAdapter
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.stripe.android.paymentsheet.PaymentSheet
import zaloznaya.olga.app.gifviewer.utils.backendUrl
import zaloznaya.olga.app.gifviewer.utils.stripe.StripePaymentListener

class ImageFragment: Fragment(R.layout.fragment_image), StripePaymentListener {

    private val viewModel by viewModel<ImageViewModel>()
    private val args: ImageFragmentArgs by navArgs()
    private val adapter = ImageViewPagerAdapter()
    private lateinit var viewPager: ViewPager2

    private var paymentSheet: PaymentSheet? = null
    private var customerConfig: PaymentSheet.CustomerConfiguration? = null
    private var paymentIntentClientSecret: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentImageBinding
        .inflate(inflater, container, false)
        .apply {
            lifecycleOwner = this@ImageFragment
            lifecycleScope.launch {
                viewModel.setInputParamsImage(args.images.toList() as ArrayList<GifImage>, args.position)
                initViewPager(vpImages)
            }
            vm = viewModel
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paymentSheet = PaymentSheet(this@ImageFragment, ::onPaymentSheetResult)
//        PaymentConfiguration.init(requireContext(), PUBLISHABLE_KEY)
        backendUrl.httpPost().responseJson { _, _, result ->
            if (result is Result.Success) {
                val responseJson = result.get().obj()
                paymentIntentClientSecret = responseJson.getString("paymentIntent")
                customerConfig = PaymentSheet.CustomerConfiguration(
                    responseJson.getString("customer"),
                    responseJson.getString("ephemeralKey")
                )
                val publishableKey = responseJson.getString("publishableKey")
                PaymentConfiguration.init(requireContext(), publishableKey)
            }
        }

        viewModel.getImagesWithPosition().observe(viewLifecycleOwner) { pair ->
            adapter.setImagesList(pair.first)
            viewPager.setCurrentItem(pair.second, false)
        }

        viewModel.paymentClickEvent.observe(viewLifecycleOwner) {
//            val stripe = StripeHelper.StripeHelperBuilder(requireActivity(), PUBLISHABLE_KEY, this)
//                .setLabel("Card detail")
//                .setSaveButtonLabel("Done") // Default label Save
//                .changeSaveBackGroundColor(resources.getColor(R.color.cardview_dark_background, null)) // Default save button bg color background_material_dark
//                .changeSaveTextColor(resources.getColor(R.color.orange, null))   // Default save text color #AAAAAA
//                .setCancelButtonLabel("Dismiss") // Default label cancel
//                .changeCancelBackGroundColor(resources.getColor(R.color.cardview_light_background, null)) // Default Cancel button bg color background_material_dark
//                .changeCancelTextColor(resources.getColor(R.color.dark_gray, null))    // Default Cancel text color #AAAAAA
//                .buildNewStripeHelper()
//            stripe.showCardDialog()

            presentPaymentSheet()
        }

        viewModel.backActionLiveEvent.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    fun presentPaymentSheet() {
        paymentSheet?.presentWithPaymentIntent(
            paymentIntentClientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "My merchant name",
                customer = customerConfig,
                // Set `allowsDelayedPaymentMethods` to true if your business
                // can handle payment methods that complete payment after a delay, like SEPA Debit and Sofort.
                allowsDelayedPaymentMethods = true
            )
        )
    }

    private fun initViewPager(vp: ViewPager2) {
        viewPager = vp
        vp.adapter = adapter
    }

    override fun onStripPaymentInFail(errorMessage: String) {
        Log.e("Error", errorMessage)
    }

    override fun onStripPaymentInSuccess(token: Token) {
        Log.e("TOKEN", token.id)
    }

    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when(paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                print("Canceled")
            }
            is PaymentSheetResult.Failed -> {
                print("Error: ${paymentSheetResult.error}")
            }
            is PaymentSheetResult.Completed -> {
                // Display e.g., an order confirmation screen
                print("Completed")
            }
        }
    }
}