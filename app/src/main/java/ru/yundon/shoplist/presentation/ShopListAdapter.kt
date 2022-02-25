package ru.yundon.shoplist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.yundon.shoplist.R
import ru.yundon.shoplist.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var listItem = listOf<ShopItem>()
        set(value) {
            val callback = ShopListDiffCallback(listItem, value) // value это новый список
            val diffResult = DiffUtil.calculateDiff(callback) //делает все расчеты, по каким элементам прошли изменения и хранит эти изменения
            diffResult.dispatchUpdatesTo(this) //делает обновления в адаптаре удаляя конкретный элемент или перерисовывая конкретный элемент

            field = value // обновляем сам список

        }

    var cnt = 0
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null //переменная в которой хранится лябмда функция

    var onShopItemClickListener: ((ShopItem) -> Unit)? = null //переменная в которой хранится лябмда функция

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        val layout = when (viewType){
            TYPE_VIEW_ENABLED -> R.layout.item_shop_enabled
            TYPE_VIEW_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {

        Log.d("MyTag", "onBindViewHolder ${++cnt}")

        val shopItem = listItem[position]

        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()

        holder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem) // если тип hullable то необходимо вызывать invoke
            true
        }

        holder.view.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
    }

    override fun getItemCount(): Int = listItem.size

    override fun getItemViewType(position: Int): Int {
        val item = listItem[position]
        return if (item.enable) TYPE_VIEW_ENABLED else TYPE_VIEW_DISABLED
    }

    class ShopItemViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
    }

    interface OnShopItemLongClickListener{
        fun shopItemLongClick(shopItem: ShopItem)
    }
    interface OnShopItemClickListener{
        fun shopItemClick(shopItem: ShopItem)
    }

    companion object{
        const val TYPE_VIEW_ENABLED = 1
        const val TYPE_VIEW_DISABLED = 0

        const val MAX_PULL_SIZE = 15
    }
}