package ru.yundon.shoplist.domain.usecases

import androidx.lifecycle.LiveData
import ru.yundon.shoplist.domain.model.ShopItem
import ru.yundon.shoplist.domain.ShopListRepository

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopList(): LiveData <List<ShopItem>>{
        return shopListRepository.getShopList()
    }
}