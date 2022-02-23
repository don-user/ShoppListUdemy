package ru.yundon.shoplist.domain

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopList(): ShopItem{
        return shopListRepository.getShopList()
    }
}