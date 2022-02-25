package ru.yundon.shoplist.presentation

import androidx.recyclerview.widget.DiffUtil
import ru.yundon.shoplist.domain.ShopItem

//класс который сравнивает конкретные элементы из списка
class ShopItemDIffCallback: DiffUtil.ItemCallback<ShopItem>(){

    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }
//сравниваем все поля в двух элементы
    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}