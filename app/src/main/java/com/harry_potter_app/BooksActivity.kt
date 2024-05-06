package com.harry_potter_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.text.TextWatcher
import android.text.Editable
import android.view.Menu
import android.view.MenuItem

class BooksActivity : AppCompatActivity() {

    private lateinit var itemsAdapter: ItemsAdapter
    private val itemsList = mutableListOf<BookMovie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Books and Movies"

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_books
        setupBottomNavigationView(bottomNavigationView)

        val rvItems = findViewById<RecyclerView>(R.id.rvItems)
        val etNewItem = findViewById<EditText>(R.id.etNewItem)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        itemsAdapter = ItemsAdapter(itemsList)
        rvItems.adapter = itemsAdapter
        rvItems.layoutManager = LinearLayoutManager(this)

        etNewItem.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Enable the add button only if there is text to add
                btnAdd.isEnabled = s.toString().trim().isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
                // Optional: can be used if needed
            }
        })

        etNewItem.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && etNewItem.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Item description cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        btnAdd.setOnClickListener {
            val itemText = etNewItem.text.toString()
            if (itemText.isNotEmpty()) {
                val newBookMovie = BookMovie(title = itemText, isFinished = false)
                itemsAdapter.addItem(newBookMovie)
                etNewItem.text.clear()
            }
        }
    }

    private fun setupBottomNavigationView(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_characters -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.navigation_books -> {
                    true
                }
                R.id.navigation_hogwarts -> {
                    startActivity(Intent(this, HouseSelectionActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    })
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_books, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                AlertDialog.Builder(this)
                    .setTitle("Delete All")
                    .setMessage("Are you sure you want to delete the list?")
                    .setPositiveButton("Yes") { dialog, which ->
                        itemsAdapter.clearItems()
                    }
                    .setNegativeButton("No", null) // null will just close the dialog
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
