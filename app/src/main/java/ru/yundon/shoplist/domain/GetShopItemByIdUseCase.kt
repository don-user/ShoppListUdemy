package ru.yundon.shoplist.domain

class GetShopItemByIdUseCase (private val shopListRepository: ShopListRepository){

    fun getShopItem(shopItemId: Int): ShopItem{
        return shopListRepository.getShopItem(shopItemId)
    }
}
