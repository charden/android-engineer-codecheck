package jp.co.yumemi.android.codecheck

import androidx.recyclerview.widget.DiffUtil

/**
 * RecyclerViewで利用するためのItemクラスが同じかチェックする
 */
object DiffUtil : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

}