package ru.yundon.shoplist.data.database

import ru.yundon.shoplist.domain.model.ShopItem

class ShopListMapper {

    fun mapModelToEntity(shopItem: ShopItem): ShopItemEntity{
        return ShopItemEntity(
            id = shopItem.id,
            name = shopItem.name,
            count = shopItem.count,
            enable = shopItem.enable
        )
    }

    fun mapEntityToModel(shopItemEntity: ShopItemEntity): ShopItem = ShopItem(
        id = shopItemEntity.id,
        name = shopItemEntity.name,
        count = shopItemEntity.count,
        enable = shopItemEntity.enable
    )

    fun mapEntityListToModelList(list: List<ShopItemEntity>) : List<ShopItem>{
        return list.map {
            mapEntityToModel(it)
        }
    }

}