package com.alexeyyuditsky.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.alexeyyuditsky.test.room.customers.CustomerDbEntity
import com.alexeyyuditsky.test.room.orders.OrderDbEntity
import com.alexeyyuditsky.test.room.orders.OrderUpdateDateTuple
import com.alexeyyuditsky.test.room.orders.OrderWithIdTuple
import com.alexeyyuditsky.test.room.products.ProductDbEntity
import kotlinx.coroutines.*
import java.lang.NumberFormatException

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

        binding.deleteOrderButton.setOnClickListener {
            val text = binding.editText.text.toString()
            val orderWithIdTuple = OrderWithIdTuple(productId = text.toLong())
            binding.editText.text.clear()
            lifecycleScope.launch(Dispatchers.IO) {
                Repositories.database.getOrdersDao().deleteOrder(orderWithIdTuple)
            }
        }

        binding.changeOrderButton.setOnClickListener {
            val text = binding.editText.text.toString().split(',')
            val orderDbEntity = OrderDbEntity(
                id = 3,
                productId = text[0].toLong(),
                customerId = text[1].toLong(),
                createdAt = text[2],
                itemsCount = text[3].toInt(),
                price = text[4].toInt()
            )
            binding.editText.text.clear()
            lifecycleScope.launch(Dispatchers.IO) {
                Repositories.database.getOrdersDao().changeOrder(orderDbEntity)
            }
        }

        binding.updateOrderButton.setOnClickListener {
            val text = binding.editText.text.toString().split(',')
            val orderUpdateDateTuple = OrderUpdateDateTuple(
                id = 3,
                createdAt = text[0]
            )
            binding.editText.text.clear()
            lifecycleScope.launch(Dispatchers.IO) {
                Repositories.database.getOrdersDao().updateOrderDate(orderUpdateDateTuple)
            }
        }

        binding.getCustomersAndProductsButton.setOnClickListener {
            val id = try {
                binding.editText.text.toString().toLong()
            } catch (e: NumberFormatException) {
                1
            }
            binding.editText.text.clear()
            lifecycleScope.launch(Dispatchers.IO) {
                val res = Repositories.database.getOrdersDao().getCustomersAndProductsAndOrders(id)
                lifecycleScope.launch {
                    binding.customersAndProductsTextView.text = res.toString()
                }
            }
        }
    }

}