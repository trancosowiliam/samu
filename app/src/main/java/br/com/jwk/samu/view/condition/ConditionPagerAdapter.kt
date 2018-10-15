package br.com.jwk.samu.view.condition

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import br.com.jwk.samu.R
import br.com.jwk.samu.component.conditionview.ConditionView
import br.com.jwk.samu.data.model.Condition
import kotlinx.android.synthetic.main.activity_condition.view.*
import kotlinx.android.synthetic.main.condition_page.view.*

class ConditionPagerAdapter(private val context: Context, private val conditions: List<Condition>) : PagerAdapter() {

    private val inflater by lazy { LayoutInflater.from(context) }
    private lateinit var layout: View
    private lateinit var conditiomViews: List<ConditionView>
    private var filterCondition = conditions.toMutableList()
    private var selectedCondictions = mutableListOf<Condition>()

    var onButtonClickListener: ((condition: Condition, selected: Boolean) -> Unit)? = null


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layout = inflater.inflate(R.layout.condition_page, container, false)
        container.addView(layout)

        initListConditiomViews()

        var initialPosition = position * 9

        conditiomViews.forEachIndexed { index, conditionView ->
            val currentCondition = filterCondition.getOrNull(initialPosition + index)
            conditionView.populate(currentCondition)
            conditionView.onButtonClickListener = { condition, selected ->
                onButtonClickListener?.invoke(condition, selected)

                if (selected) {
                    selectedCondictions.add(condition)
                } else {
                    selectedCondictions.remove(condition)
                }
            }

            conditionView.isSelected = selectedCondictions.contains(currentCondition)
        }

        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getItemPosition(obj: Any) = POSITION_NONE

    override fun getCount(): Int = ((filterCondition.size - 1) / 9) + 1

    private fun initListConditiomViews() {
        conditiomViews = listOf(
                layout.cpCvcondition11,
                layout.cpCvcondition12,
                layout.cpCvcondition13,
                layout.cpCvcondition21,
                layout.cpCvcondition22,
                layout.cpCvcondition23,
                layout.cpCvcondition31,
                layout.cpCvcondition32,
                layout.cpCvcondition33
        )
    }

    fun filter(query: String) {
        filterCondition = conditions.asSequence().filter { condition ->
            condition.name.contains(query, true)
        }.toMutableList()

        notifyDataSetChanged()
    }

}