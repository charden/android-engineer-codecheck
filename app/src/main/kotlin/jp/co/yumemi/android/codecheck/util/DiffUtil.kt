package jp.co.yumemi.android.codecheck.util

import androidx.recyclerview.widget.DiffUtil
import jp.co.yumemi.android.codecheck.model.Item

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