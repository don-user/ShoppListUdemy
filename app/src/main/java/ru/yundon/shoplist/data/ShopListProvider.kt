package ru.yundon.shoplist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import ru.yundon.shoplist.data.database.ShopListDao
import ru.yundon.shoplist.presentation.ShopItemApp
import javax.inject.Inject

class ShopListProvider: ContentProvider() {

    //получаем экземпляр копмпонента от класса APP
    private val component by lazy {
        (context as ShopItemApp).component
    }

    //инжектим Дао
    @Inject
    lateinit var shopListDao: ShopListDao

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        //для передачи числа (например id в "path" через / добавляем # ("path/#"))
        //для передачи строки (например в "path" через / добавляем * ("path/*"))
        addURI("ru.yundon.shoplist", "shop_items", GET_SHOP_ITEM_QUERY)
        addURI("ru.yundon.shoplist", "shop_items/#", GET_SHOP_ITEM_BY_ID_QUERY)
        addURI("ru.yundon.shoplist", "shop_items/*", GET_SHOP_ITEM_BY_NAME_QUERY)
    }

    override fun onCreate(): Boolean {
        component.inject(this)
        return true
    }

    override fun query(
        uri: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {

        //получаем код
        val code = uriMatcher.match(uri)
        //проверяем код и возвращаем необходимый ответ
        return when(code){
            GET_SHOP_ITEM_QUERY -> {
                shopListDao.getShopListCursor()
            }
            else -> null
        }
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    companion object{
        const val GET_SHOP_ITEM_QUERY = 100
        const val GET_SHOP_ITEM_BY_ID_QUERY = 101
        const val GET_SHOP_ITEM_BY_NAME_QUERY = 102
    }
}