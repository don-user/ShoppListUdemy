package ru.yundon.shoplist.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.yundon.shoplist.domain.model.ShopItem

@Entity(tableName = "shop_items")
data class ShopItemEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val count: Int,
    val enable: Boolean
)