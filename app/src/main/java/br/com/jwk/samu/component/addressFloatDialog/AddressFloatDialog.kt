package br.com.jwk.samu.component.addressFloatDialog

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import br.com.jwk.samu.R
import br.com.jwk.samu.data.model.Address
import br.com.jwk.samu.extension.isVisible
import kotlinx.android.synthetic.main.float_address_dialog.view.*
import kotlinx.android.synthetic.main.float_address_dialog.view.fadTxtArea as txtArea
import kotlinx.android.synthetic.main.float_address_dialog.view.fadTxtStreet as txtStreet
import kotlinx.android.synthetic.main.float_address_dialog.view.fadTxtSubArea as txtSubArea

class AddressFloatDialog : FrameLayout {

    var onButtonClickListener: (() -> Unit)? = null

    var address: Address? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.float_address_dialog, this)
        fadImgArrow.setOnClickListener {
            onButtonClickListener?.invoke()
        }
    }

    fun isLoading(isLoading: Boolean) {
        if (isLoading) {
            txtStreet.text = "Carregando..."
            txtArea.text = ""
            txtSubArea.text = ""
        } else {
            populate(address)
        }

        fadImgArrow.isVisible = !isLoading
    }

    private fun populate(address: Address?) {
        address?.let { add ->
            val areaList = listOfNotNull(
                    add.neighborhood.takeUnless { it.isNullOrEmpty() },
                    add.county.takeUnless { it.isNullOrEmpty() }
            )

            val subAreaList = listOfNotNull(
                    add.postalCode.takeUnless { it.isNullOrEmpty() },
                    add.homeNumber.takeUnless { it.isNullOrEmpty() }
            )

            txtStreet.text = add.street
            txtArea.text = areaList.joinToString(" - ")
            txtSubArea.text = subAreaList.joinToString(", ")
        }
    }

}