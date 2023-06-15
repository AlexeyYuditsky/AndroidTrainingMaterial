package ua.cn.stu.tabs.model.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppSQLiteHelper(
    private val applicationContext: Context
) : SQLiteOpenHelper(applicationContext, "database.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        applicationContext.assets.open("db_init.sql")
            .bufferedReader()
            .use { it.readText() }
            .split(';')
            .map { it.trim() }
            .forEach { db.execSQL(it) }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

}

fun main() {
    val text = "PRAGMA foreign_keys = ON;\n" +
            "\n" +
            "CREATE TABLE \"accounts\" (\n" +
            "  \"id\"\t\t\tINTEGER PRIMARY KEY,\n" +
            "  \"email\"\t\tTEXT NOT NULL UNIQUE COLLATE NOCASE,\n" +
            "  \"username\" \tTEXT NOT NULL,\n" +
            "  \"password\" \tTEXT NOT NULL,\n" +
            "  \"created_at\" \tINTEGER NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE \"boxes\" (\n" +
            "  \"id\"\t\t\tINTEGER PRIMARY KEY,\n" +
            "  \"color_name\"\tTEXT NOT NULL,\n" +
            "  \"color_value\" TEXT NOT NULL\n" +
            ");\n" +
            "\n" +
            "CREATE TABLE \"accounts_boxes_settings\" (\n" +
            "  \"account_id\"\t\tINTEGER NOT NULL,\n" +
            "  \"box_id\"\t\t\tINTEGER NOT NULL,\n" +
            "  \"is_active\"\t\tINTEGER NOT NULL,\n" +
            "  FOREIGN KEY(\"account_id\") REFERENCES \"accounts\"(\"id\")\n" +
            "    ON UPDATE CASCADE ON DELETE CASCADE,\n" +
            "  FOREIGN KEY(\"box_id\") REFERENCES \"boxes\"(\"id\")\n" +
            "    ON UPDATE CASCADE ON DELETE CASCADE,\n" +
            "  UNIQUE(\"account_id\",\"box_id\")\n" +
            ");\n" +
            "\n" +
            "INSERT INTO \"accounts\" (\"email\", \"username\", \"password\",\n" +
            "    \"created_at\")\n" +
            "VALUES\n" +
            "  (\"1\", \"admin\", \"1\", 0),\n" +
            "  (\"tester@google.com\", \"tester\", \"321\", 0);\n" +
            "\n" +
            "INSERT INTO \"boxes\" (\"color_name\", \"color_value\")\n" +
            "VALUES\n" +
            "   (\"Red\", \"#880000\"),\n" +
            "   (\"Green\", \"#008800\"),\n" +
            "   (\"Blue\", \"#000088\"),\n" +
            "   (\"Yellow\", \"#888800\"),\n" +
            "   (\"Violet\", \"#8800FF\"),\n" +
            "   (\"Black\", \"#000000\")"

    val split = text.split(';')

    println(split)

}