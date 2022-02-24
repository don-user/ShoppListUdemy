package ru.yundon.shoplist.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.yundon.shoplist.R
import ru.yundon.shoplist.databinding.ActivityMainBinding
import ru.yundon.shoplist.domain.ShopItem

class MainActivity: AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopList.observe(this, {
            Log.d("MyTag", it.toString())
            showList(it)
        })
    }

    private fun showList(list: List<ShopItem>){

        binding.llShopList.removeAllViews()

        for (shopItem in list){

            val layoutId = if (shopItem.enable) R.layout.item_shop_enabled else R.layout.item_shop_disabled

            val view = LayoutInflater.from(this).inflate(layoutId, binding.llShopList, false)
            val tvName = view.findViewById<TextView>(R.id.tv_name)
            val tvCount = view.findViewById<TextView>(R.id.tv_count)
            tvName.text = shopItem.name
            tvCount.text = shopItem.count.toString()

            view.setOnLongClickListener {
//                Toast.makeText(this, "TEST", Toast.LENGTH_SHORT).show()
                viewModel.editShopListItem(shopItem)
                true
            }
            binding.llShopList.addView(view)

        }
    }
}
