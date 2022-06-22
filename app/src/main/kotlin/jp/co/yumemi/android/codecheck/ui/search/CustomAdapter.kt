package jp.co.yumemi.android.codecheck.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import jp.co.yumemi.android.codecheck.R
import jp.co.yumemi.android.codecheck.model.Item
import jp.co.yumemi.android.codecheck.util.DiffUtil

/**
 * RecyclerView用のAdapter
 *
 * @param itemClickListener OnItemClickListener Itemをクリックしたとき用Listener
 */
class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<Item, CustomAdapter.ViewHolder>(DiffUtil) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun itemClick(item: Item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.findViewById<TextView>(R.id.repositoryNameView).text =
            item.name

        holder.itemView.findViewById<ImageView>(R.id.repositoryImageView).load(item.ownerIconUrl) {
            transformations(CircleCropTransformation())
        }

        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(item)
        }
    }
}