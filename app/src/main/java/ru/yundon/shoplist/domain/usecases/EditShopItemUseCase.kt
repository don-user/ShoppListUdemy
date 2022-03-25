package ru.yundon.shoplist.domain.usecases

import ru.yundon.shoplist.domain.model.ShopItem
import ru.yundon.shoplist.domain.ShopListRepository

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun editShopItem(shopItem: ShopItem){
        shopListRepository.editShopItem(shopItem)
    }
}