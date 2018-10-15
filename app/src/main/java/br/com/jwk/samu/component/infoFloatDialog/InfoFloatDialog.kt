package br.com.jwk.samu.component.infoFloatDialog

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import br.com.jwk.samu.R
import kotlinx.android.synthetic.main.info_float_dialog.view.*

class InfoFloatDialog : FrameLayout {

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.info_float_dialog, this)

        context.theme.obtainStyledAttributes(attrs, R.styleable.InfoFloatDialog, 0, 0).apply {
            try {
                ifdLblInfo.text = getString(R.styleable.InfoFloatDialog_info_text)
            } finally {
                recycle()
            }
        }
    }
}