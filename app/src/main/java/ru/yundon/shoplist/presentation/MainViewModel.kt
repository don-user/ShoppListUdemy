package ru.yundon.shoplist.presentation

import androidx.lifecycle.ViewModel
import ru.yundon.shoplist.data.ShopListRepositoryImpl
import ru.yundon.shoplist.domain.DeleteShopItemUseCase
import ru.yundon.shoplist.domain.EditShopItemUseCase
import ru.yundon.shoplist.domain.GetShopListUseCase
import ru.yundon.shoplist.domain.ShopItem

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl  // не правильная реализация, в дальнейщем поправим, presentation не должен зависить от data

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()


    fun deleteShopListItem(shopItem: ShopItem){
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun editShopListItem(shopItem: ShopItem){
        val newItem = shopItem.copy(enable = !shopItem.enable)
        editShopItemUseCase.editShopItem(newItem)
    }

}