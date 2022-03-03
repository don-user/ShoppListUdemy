package ru.yundon.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.yundon.shoplist.R
import ru.yundon.shoplist.databinding.ActivityShopItemBinding
import ru.yundon.shoplist.domain.ShopItem.Companion.UNDEFINED_ID

class ShopItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopItemBinding
    private lateinit var viewModel: ShopItemViewModel
    private var screenMode = MODE_UNKNOWN
    private var shopItemId = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.errorInputCount.observe(this){
            val messageCount = if (it) getString(R.string.error_input_count) else null
            binding.tilCount.error = messageCount
        }
        viewModel.errorInputName.observe(this){
            val messageName = if (it) getString(R.string.error_input_name) else null
            binding.tilName.error = messageName
        }
        viewModel.shouldCloseScreen.observe(this){
            finish()
        }
    }
    private fun addTextChangeListeners(){
        binding.edName.addTextChangedListener ( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.edCount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun launchRightMode(){
        when(screenMode){
            MODE_EDIT -> launchEditMode()
            MODE_ADD-> launchAddMode()
        }
    }

    private fun launchEditMode() = with(binding) {
        viewModel.getShopItemById(shopItemId)
        viewModel.shopItem.observe(this@ShopItemActivity){
            edName.setText(it.name)
            edCount.setText(it.count.toString())
        }
        btSave.setOnClickListener {
            viewModel.editShopItem(edName.text?.toString(), edCount.text?.toString())
        }
    }

    private fun launchAddMode() = with(binding) {
        btSave.setOnClickListener {
            Toast.makeText(this@ShopItemActivity, "BUTTON", Toast.LENGTH_SHORT).show()
            viewModel.addShopItem(edName.text?.toString(), edCount.text?.toString())
            viewModel.shouldCloseScreen.observe(this@ShopItemActivity) {
                finish()
            }
        }
    }
    //проверка параметров которые передаются через интент
    private fun parseIntent(){
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) throw RuntimeException("Param screen mode is absent")

        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) throw RuntimeException("Unknown screen mode $mode")

        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item ID is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, UNDEFINED_ID)
        }
    }

    fun newIntentAddItem(context: Context): Intent{
        val intent = Intent(context, ShopItemActivity::class.java)
        intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
        return intent
    }

    fun newIntentEditItem(context: Context, shopItemId: Int): Intent{
        val intent = Intent(context, ShopItemActivity::class.java)
        intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
        intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)

        return intent
    }

    companion object{
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""
    }
}