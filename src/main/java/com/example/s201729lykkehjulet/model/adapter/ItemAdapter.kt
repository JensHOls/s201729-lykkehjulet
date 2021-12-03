package com.example.s201729lykkehjulet.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s201729lykkehjulet.R
import com.example.s201729lykkehjulet.fragment.PlayFragment
import com.example.s201729lykkehjulet.model.data.BonusDescription
// Code in this class is slightly altered code from
// "https://developer.android.com/codelabs/basic-android-kotlin-training-affirmations-app#3"

// the purpose of this class is to convert data for the recyclerview to display it
class ItemAdapter(
    private val context: PlayFragment,
    private val dataset: List<BonusDescription>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.stringResource
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
}