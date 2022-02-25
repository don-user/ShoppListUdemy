package ru.yundon.shoplist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.yundon.shoplist.databinding.ActivityMainBinding
import ru.yundon.shoplist.presentation.ShopListAdapter.Companion.MAX_PULL_SIZE
import ru.yundon.shoplist.presentation.ShopListAdapter.Companion.TYPE_VIEW_DISABLED
import ru.yundon.shoplist.presentation.ShopListAdapter.Companion.TYPE_VIEW_ENABLED

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterShopList: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopList.observe(this, {
            adapterShopList.submitList(it)
        })
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
            Toast.makeText(this@MainActivity, "TEST", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupOnShopItemLongClickListener() {
        adapterShopList.onShopItemLongClickListener = {
            viewModel.editShopListItem(it)
        }
    }
}
