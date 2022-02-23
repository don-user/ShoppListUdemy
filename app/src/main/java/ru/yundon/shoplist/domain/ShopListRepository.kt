package ru.yundon.shoplist.domain

//Этот репозиторий будет и умеет работать с данными

interface ShopListRepository {

    //добавлять элемент
    fun addShopItem(shopItem: ShopItem)

    //удалять элемент
    fun deleteShopItem(shopItem: ShopItem)

    //редактировать элемент
    fun editShopItem(shopItem: ShopItem)

    //получать элемент по item
    fun getShopItem(shopItemId: Int): ShopItem

    //получать список элементов
    fun getShopList(): List<ShopItem>

}