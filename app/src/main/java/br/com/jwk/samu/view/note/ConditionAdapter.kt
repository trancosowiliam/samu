package br.com.jwk.samu.view.note

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.jwk.samu.R
import br.com.jwk.samu.component.conditionview.ConditionView
import br.com.jwk.samu.data.model.Condition

class ConditionAdapter(private val conditions: List<Condition>, private val context:Context) : RecyclerView.Adapter<ConditionAdapter.ConditionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConditionHolder {
        return ConditionHolder(LayoutInflater.from(context).inflate(R.layout.condition_view, parent, false))
    }

    override fun getItemCount() = conditions.size

    override fun onBindViewHolder(holder: ConditionHolder, position: Int) {
        holder.render(conditions[position])
    }

    class ConditionHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun render(condition: Condition) {
            (itemView as? ConditionView)?.populate(condition)
        }

    }
}