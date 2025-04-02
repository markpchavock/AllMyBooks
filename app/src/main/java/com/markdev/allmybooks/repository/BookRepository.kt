package com.markdev.allmybooks.repository

import android.content.ContentValues
import android.content.Context
import com.markdev.allmybooks.entity.BookEntity
import com.markdev.allmybooks.helper.BookConstants
import com.markdev.allmybooks.helper.BooksDataBaseConstants

class BookRepository private constructor(context: Context) {

    private var database = BookDataBaseHelper(context)


    companion object {
        private lateinit var instance: BookRepository

        //Singleton

        fun getInstance(context: Context): BookRepository {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = BookRepository(context)
                }
            }
            return instance
        }
    }


    fun getAllBooks(): List<BookEntity> {

        val db = database.readableDatabase
        val books = mutableListOf<BookEntity>()

        val cursor =
            db.query(BooksDataBaseConstants.BOOK.TABLE_NAME, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val id =
                    cursor.getInt(cursor.getColumnIndexOrThrow(BooksDataBaseConstants.BOOK.COLUMNS.ID))
                val title =
                    cursor.getString(cursor.getColumnIndexOrThrow(BooksDataBaseConstants.BOOK.COLUMNS.TITLE))
                val author =
                    cursor.getString(cursor.getColumnIndexOrThrow(BooksDataBaseConstants.BOOK.COLUMNS.AUTHOR))
                val genre =
                    cursor.getString(cursor.getColumnIndexOrThrow(BooksDataBaseConstants.BOOK.COLUMNS.GENRE))
                val favorite =
                    cursor.getInt(cursor.getColumnIndexOrThrow(BooksDataBaseConstants.BOOK.COLUMNS.FAVORITE)) == 1

                books.add(BookEntity(id, title, author, favorite, genre))

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return books
    }

    fun getFavoriteBooks(): List<BookEntity> {
        var db = database.readableDatabase
        var books = mutableListOf<BookEntity>()

        val cursor = db.query(
            BooksDataBaseConstants.BOOK.TABLE_NAME, null,
            "${BooksDataBaseConstants.BOOK.COLUMNS.FAVORITE} = ?", arrayOf("1"), null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                var id =
                    cursor.getInt(cursor.getColumnIndexOrThrow(BooksDataBaseConstants.BOOK.COLUMNS.ID))
                var title =
                    cursor.getString(cursor.getColumnIndexOrThrow(BooksDataBaseConstants.BOOK.COLUMNS.TITLE))
                var author =
                    cursor.getString(cursor.getColumnIndexOrThrow(BooksDataBaseConstants.BOOK.COLUMNS.AUTHOR))
                var genre =
                    cursor.getString(cursor.getColumnIndexOrThrow(BooksDataBaseConstants.BOOK.COLUMNS.GENRE))
                var favorite =
                    cursor.getInt(cursor.getColumnIndexOrThrow(BooksDataBaseConstants.BOOK.COLUMNS.FAVORITE)) == 1

                books.add(BookEntity(id, title, author, favorite, genre))

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return books
    }

    fun getBookById(id: Int): BookEntity? {
        val db = database.readableDatabase
        var book: BookEntity? = null

        val cursor = db.query(
            BooksDataBaseConstants.BOOK.TABLE_NAME,
            null,
            "${BooksDataBaseConstants.BOOK.COLUMNS.ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            var id =
                cursor.getInt(cursor.getColumnIndexOrThrow(BooksDataBaseConstants.BOOK.COLUMNS.ID))
            var title =
                cursor.getString(cursor.getColumnIndexOrThrow(BooksDataBaseConstants.BOOK.COLUMNS.TITLE))
            var author =
                cursor.getString(cursor.getColumnIndexOrThrow(BooksDataBaseConstants.BOOK.COLUMNS.AUTHOR))
            var genre =
                cursor.getString(cursor.getColumnIndexOrThrow(BooksDataBaseConstants.BOOK.COLUMNS.GENRE))
            var favorite =
                cursor.getInt(cursor.getColumnIndexOrThrow(BooksDataBaseConstants.BOOK.COLUMNS.FAVORITE)) == 1
            book = BookEntity(id, title, author, favorite, genre)
        }

        cursor.close()
        db.close()

        return book
    }

    fun deleteBook(id: Int): Boolean {
        var db = database.writableDatabase

        val rowsDeleted = db.delete(BooksDataBaseConstants.BOOK.TABLE_NAME,"${BooksDataBaseConstants.BOOK.COLUMNS.ID} = ?",
            arrayOf(id.toString())
        )

        return rowsDeleted > 0
    }

    fun toggleFavoriteStatus(id: Int) {
        var book = getBookById(id)
        val newFavoriteStatus = if (book?.favorite == true) 0 else 1

        val db = database.writableDatabase
        val values = ContentValues().apply {
            put(BooksDataBaseConstants.BOOK.COLUMNS.FAVORITE, newFavoriteStatus)
        }
        db.update(
            BooksDataBaseConstants.BOOK.TABLE_NAME,
            values,
            "${BooksDataBaseConstants.BOOK.COLUMNS.ID} = ?",
            arrayOf(id.toString())
        )
        db.close()
    }
}