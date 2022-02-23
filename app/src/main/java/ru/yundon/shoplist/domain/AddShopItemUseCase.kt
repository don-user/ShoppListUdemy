package ru.yundon.shoplist.domain

//метод добавления итема
class AddShopItemUseCase (private val shopListRepository: ShopListRepository) {

    fun addShopItem(shopItem: ShopItem){

        shopListRepository.addShopItem(shopItem)
    }
}