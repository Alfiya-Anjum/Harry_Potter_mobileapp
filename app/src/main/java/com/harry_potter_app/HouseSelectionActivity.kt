package com.harry_potter_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent



class HouseSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_selection)

        val userNameEditText: EditText = findViewById(R.id.etUserName)
        val housesRadioGroup: RadioGroup = findViewById(R.id.rgHouses)
        val submitButton: Button = findViewById(R.id.btnSubmit)
        val selectionsTextView: TextView = findViewById(R.id.tvHouseSelections)
        val showSelectionsButton: Button = findViewById(R.id.btnShowSelections)

        // Handle the submission of house selection
        submitButton.setOnClickListener {
            val userName = userNameEditText.text.toString().trim()
            if (userName.isNotEmpty() && housesRadioGroup.checkedRadioButtonId != -1) {
                val selectedHouseRadioButton: RadioButton = findViewById(housesRadioGroup.checkedRadioButtonId)
                val selectedHouse = selectedHouseRadioButton.text.toString()
                saveHouseSelection(userName, selectedHouse)
            } else {
                Toast.makeText(this, "Please enter your name and select a house.", Toast.LENGTH_SHORT).show()
            }
        }

        // Display saved selections
        showSelectionsButton.setOnClickListener {
            val selections = readHouseSelections()
            selectionsTextView.text = selections
        }

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_characters -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_books -> {
                    val intent = Intent(this, BooksActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_hogwarts -> {

                    true
                }
                else -> false
            }
        }


        bottomNavigationView.selectedItemId = R.id.navigation_hogwarts

    }


    private fun saveHouseSelection(userName: String, house: String) {
        val selection = "$userName chose $house.\n"
        try {

            // MODE_APPEND to append the new selection to an existing file or create it if it doesn't exist.
            openFileOutput("house_selections.txt", MODE_APPEND).use { outputStream ->
                outputStream.write(selection.toByteArray())
            }
            // Provide feedback that the operation was successful
            Toast.makeText(this, "Selection saved", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // If something goes wrong, provide feedback to the user
            Toast.makeText(this, "Failed to save selection", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
    private fun readHouseSelections(): String {
        val fileName = "house_selections.txt"
        return try {
            // Use a buffered reader to read the text file content
            openFileInput(fileName).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            "Failed to read selections"
        }
    }


}
