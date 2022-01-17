package com.alexeyyuditsky.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var isFirstPlayer = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        binding.ticTacToeView.ticTacToeField = TicTacToeField(10, 10)

        binding.ticTacToeView.actionListener = { row, column, field ->
            val cell = field.getCell(row, column)
            if (cell == Cell.EMPTY) {
                if (isFirstPlayer) {
                    field.setCell(row, column, Cell.PLAYER_1)
                } else {
                    field.setCell(row, column, Cell.PLAYER_2)
                }
                isFirstPlayer = !isFirstPlayer
            }
        }

        binding.randomSizeButton.setOnClickListener {
            binding.ticTacToeView.ticTacToeField =
                TicTacToeField(Random.nextInt(3, 10), Random.nextInt(3, 10))
        }
    }

}