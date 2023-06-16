package com.example.sqlite_example

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sqlite_example.databinding.ActivityMainBinding
import java.sql.SQLException

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = Repository.database

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS "users"(
            	"id" INTEGER PRIMARY KEY AUTOINCREMENT,
            	"name" TEXT NOT NULL COLLATE NOCASE,
            	"age" INTEGER NOT NULL,
            	"created_at" TEXT NOT NULL,
            UNIQUE("name","age"))
            """.trimIndent()
        )

        try {
            db.execSQL(
                """
                INSERT INTO "users"
                    ("name", "age", "created_at")
                VALUES
                    ("alex22", "4443", "15.06.2023")
                """.trimIndent()
            )
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(this, "Дубликатная запись", Toast.LENGTH_SHORT).show()
        }





        try {
            db.rawQuery("SELECT * FROM users", null).use {
                val result = StringBuilder()
                while (it.moveToNext()) {
                    result
                        .append("name: ${it.getString(it.getColumnIndexOrThrow("name"))}, ")
                        .append("age: ${it.getString(it.getColumnIndexOrThrow("age"))} ")
                        .append("\n")
                    binding.textView.text = result
                }
            }
        } catch (e: SQLiteException) {
            Toast.makeText(this, "Таблицы не существует", Toast.LENGTH_SHORT).show()
        }



    }

}

fun main() {
    val res = """
    INSERT INTO "users"
        ("name", "age", "created_at")
    VALUES
        ("alex", "26", "16.06.2023"),
        ("ivan", "26", "15.06.2023")
    """.trimIndent()

    println(res)
}