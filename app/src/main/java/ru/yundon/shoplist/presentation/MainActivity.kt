package ru.yundon.shoplist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.yundon.shoplist.R
import ru.yundon.shoplist.databinding.ActivityMainBinding
import ru.yundon.shoplist.presentation.ShopListAdapter.Companion.MAX_PULL_SIZE
import ru.yundon.shoplist.presentation.ShopListAdapter.Companion.TYPE_VIEW_DISABLED
import ru.yundon.shoplist.presentation.ShopListAdapter.Companion.TYPE_VIEW_ENABLED

class MainActivity: AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterShopList: ShopListAdapter
    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        shopItemContainer = binding.shopItemContainer
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopList.observe(this, {
            adapterShopList.submitList(it)
        })

        binding.buttonAddShopItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity().newIntentAddItem(this)
                startActivity(intent)
            }else{
                launchFragment(ShopItemFragment.newInstanceAddNewItem())
            }
        }
    }

    //проверяем если shopItemContainer равен null то вертикальный вид в 1 колонку, если нет то горизонтальный в 2 колонки
    private fun isOnePaneMode(): Boolean{
        return shopItemContainer == null
    }
    //запуск верного контейнера фрагмента
    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() = with(binding.rvShopList){

        adapterShopList = ShopListAdapter()
        adapter = adapterShopList

        //recycledViewPool определяет количество неиспользуемых элементов для каждого типа
        recycledViewPool.setMaxRecycledViews(TYPE_VIEW_ENABLED, MAX_PULL_SIZE)
        recycledViewPool.setMaxRecycledViews(TYPE_VIEW_DISABLED, MAX_PULL_SIZE)
        //вызов лямба функции из адаптера
        setupOnShopItemLongClickListener()
        setupClickListener()
        setupSwipeTouchListener(this)
    }


    //функция свайпа
    private fun setupSwipeTouchListener(rvShopItem: RecyclerView) {
        val swipeCallback = object :
            ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapterShopList.currentList[viewHolder.adapterPosition]
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        viewModel.deleteShopListItem(item)
                    }
                    ItemTouchHelper.RIGHT -> {
                        viewModel.deleteShopListItem(item)
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(rvShopItem)
    }

    private fun setupClickListener() {
        adapterShopList.onShopItemClickListener = {
            if(isOnePaneMode()){
                val intent = ShopItemActivity().newIntentEditItem(this, it.id)
                startActivity(intent)
            } else launchFragment(ShopItemFragment.newInstanceEditNewItem(it.id))


            //Toast.makeText(this@MainActivity, "TEST", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupOnShopItemLongClickListener() {
        adapterShopList.onShopItemLongClickListener = {
            viewModel.editShopListItem(it)
        }
    }
}
