package com.harry_potter_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent


class MainActivity : AppCompatActivity() {

    private lateinit var charactersRecyclerView: RecyclerView
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))  // ActionBar

        setupRecyclerView()
        setupBottomNavigationView()
    }

    private fun setupRecyclerView() {
        val characterList = listOf(
            Character("Harry", R.drawable.harry, "The boy who lived.", "https://en.wikipedia.org/wiki/Harry_Potter"),
            Character("Ron", R.drawable.ron, "Harry's loyal friend.", "https://en.wikipedia.org/wiki/Ron_Weasley"),
            Character("Hermione", R.drawable.hermione, "Brilliant witch with a sharp mind.", "https://en.wikipedia.org/wiki/Hermione_Granger"),
            Character("Snape", R.drawable.snape, "Complicated man with deep loyalties.", "https://en.wikipedia.org/wiki/Severus_Snape"),
            Character("Voldemort", R.drawable.voldemort, "The Dark Lord, enemy of Harry.", "https://en.wikipedia.org/wiki/Lord_Voldemort"),
            Character("Draco", R.drawable.draco, "Harry's rival at Hogwarts.", "https://en.wikipedia.org/wiki/Draco_Malfoy")
        )

        charactersRecyclerView = findViewById(R.id.characterRecyclerView)
        charactersRecyclerView.layoutManager = GridLayoutManager(this, 2)
        characterAdapter = CharacterAdapter(characterList)
        charactersRecyclerView.adapter = characterAdapter
    }

    private fun setupBottomNavigationView() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_characters -> {

                    true
                }
                R.id.navigation_books -> {
                    startActivity(Intent(this, BooksActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    })
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

        bottomNavigationView.selectedItemId = R.id.navigation_characters
    }


    private fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
