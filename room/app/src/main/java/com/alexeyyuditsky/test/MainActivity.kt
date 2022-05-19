package com.alexeyyuditsky.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.alexeyyuditsky.test.room.customers.CustomerDbEntity
import com.alexeyyuditsky.test.room.orders.OrderDbEntity
import com.alexeyyuditsky.test.room.orders.OrderWithIdTuple
import com.alexeyyuditsky.test.room.products.ProductDbEntity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Repositories.init(applicationContext)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        binding.getAllDataButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val customers = Repositories.database.getCustomersDao().getAllCustomers()
                val products = Repositories.database.getProductsDao().getAllProducts()
                val orders = Repositories.database.getOrdersDao().getAllOrders()

                lifecycleScope.launch {
                    customers.forEach {
                        binding.idCustomersTextView.text = it.id.toString()
                        binding.nameCustomersTextView.text = it.name
                        delay(1000)
                    }
                }
                lifecycleScope.launch {
                    products.forEach {
                        binding.idProductsTextView.text = it.id.toString()
                        binding.nameProductsTextView.text = it.name
                        binding.companyProductsTextView.text = it.company
                        binding.itemsCountProductsTextView.text = it.itemsCount.toString()
                        binding.priceProductsTextView.text = it.price.toString()
                        delay(1000)
                    }
                }
                lifecycleScope.launch {
                    if (orders.isEmpty()) {
                        binding.productIdOrdersTextView.text = "empty"
                        binding.customerIdOrdersTextView.text = "empty"
                        binding.createdAtOrdersTextView.text = "empty"
                        binding.itemsCountOrdersTextView.text = "empty"
                        binding.priceOrdersTextView.text = "empty"
                    }
                    orders.forEach {
                        binding.idOrdersTextView.text = it.id.toString()
                        binding.productIdOrdersTextView.text = it.productId.toString()
                        binding.customerIdOrdersTextView.text = it.customerId.toString()
                        binding.createdAtOrdersTextView.text = it.createdAt
                        binding.itemsCountOrdersTextView.text = it.itemsCount.toString()
                        binding.priceOrdersTextView.text = it.price.toString()
                        delay(1000)
                    }
                }
            }
        }

        binding.insertOrderButton.setOnClickListener {
            val text = binding.editText.text.toString()
            val orderDbEntity = OrderDbEntity.createOrder(text)
            binding.editText.text.clear()
            lifecycleScope.launch(Dispatchers.IO) {
                Repositories.database.getOrdersDao().createOrder(orderDbEntity)
            }
        }

        binding.deleteOrder.setOnClickListener {
            val text = binding.editText.text.toString()
            val orderWithIdTuple = OrderWithIdTuple(productId = text.toLong())
            lifecycleScope.launch(Dispatchers.IO) {
                Repositories.database.getOrdersDao().deleteOrder(orderWithIdTuple)
            }
        }
    }

}