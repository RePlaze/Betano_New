package com.example.betano

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class Store : AppCompatActivity(), View.OnClickListener {
    private lateinit var balanceTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private var isDarkItemPurchased = false
    private var isBlueItemPurchased = false
    private var isPurpleItemPurchased = false
    private lateinit var darkItemBuyButton: Button
    private lateinit var darkDisableButton: Button
    private lateinit var darkUseButton: Button
    private lateinit var blueItemBuyButton: Button
    private lateinit var blueUseButton: Button
    private lateinit var blueDisableButton: Button
    private lateinit var purpleItemBuyButton: Button
    private lateinit var purpleUseButton: Button
    private lateinit var purpleDisableButton: Button
    private lateinit var cardBackgroundImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)
        balanceTextView = findViewById(R.id.balanceTextView)

        // Get shared preferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // Load item status from shared preferences
        isDarkItemPurchased = sharedPreferences.getBoolean("isDarkItemPurchased", false)
        isBlueItemPurchased = sharedPreferences.getBoolean("isBlueItemPurchased", false)
        isPurpleItemPurchased = sharedPreferences.getBoolean("isPurpleItemPurchased", false)

        // Initialize views
        darkItemBuyButton = findViewById(R.id.darkItemBuyButton)
        darkUseButton = findViewById(R.id.darkUseButton)
        darkDisableButton = findViewById(R.id.darkDisableButton)
        blueItemBuyButton = findViewById(R.id.blueItemBuyButton)
        blueUseButton = findViewById(R.id.blueUseButton)
        blueDisableButton = findViewById(R.id.blueDisableButton)
        purpleItemBuyButton = findViewById(R.id.purpleItemBuyButton)
        purpleUseButton = findViewById(R.id.purpleUseButton)
        purpleDisableButton = findViewById(R.id.purpleDisableButton)
        cardBackgroundImageView = findViewById(R.id.cardBackgroundImageView)

        // Set click listeners
        darkItemBuyButton.setOnClickListener(this)
        darkUseButton.setOnClickListener(this)
        darkDisableButton.setOnClickListener(this)
        blueItemBuyButton.setOnClickListener(this)
        blueUseButton.setOnClickListener(this)
        blueDisableButton.setOnClickListener(this)
        purpleItemBuyButton.setOnClickListener(this)
        purpleUseButton.setOnClickListener(this)
        purpleDisableButton.setOnClickListener(this)

        // Update UI based on item purchase status
        updateUI()
        updateBalance()
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun updateUI() {
        if (isDarkItemPurchased) {
            darkItemBuyButton.visibility = View.GONE
            darkUseButton.visibility = View.VISIBLE
            darkDisableButton.visibility = View.GONE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cardBackgroundImageView.setBackgroundColor(getColor(R.color.white))
            }
        }
        if (isBlueItemPurchased) {
            blueItemBuyButton.visibility = View.GONE
            blueUseButton.visibility = View.VISIBLE
            blueDisableButton.visibility = View.GONE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cardBackgroundImageView.setBackgroundColor(getColor(R.color.white))
            }
        }
        if (isPurpleItemPurchased) {
            purpleItemBuyButton.visibility = View.GONE
            purpleUseButton.visibility = View.VISIBLE
            purpleDisableButton.visibility = View.GONE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cardBackgroundImageView.setBackgroundColor(getColor(R.color.white))
            }
        }
    }

    private fun performPurchase(item: String, itemPrice: Int) {
        val balance = sharedPreferences.getInt("balance", 0)
        if (balance < itemPrice) {
            showInsufficientBalanceDialog()
        } else {
            showConfirmationDialog(item, itemPrice)
        }
    }

    private fun showInsufficientBalanceDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Insufficient Balance")
            .setMessage("You don't have enough balance to make this purchase.")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showConfirmationDialog(item: String, itemPrice: Int) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Confirm Purchase")
            .setMessage("Are you sure you want to buy this item?")
            .setPositiveButton("Buy") { _: DialogInterface?, _: Int ->
                makePurchase(
                    item,
                    itemPrice
                )
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun makePurchase(item: String, itemPrice: Int) {
        val editor = sharedPreferences.edit()
        var balance = sharedPreferences.getInt("balance", 0)
        when (item) {
            "dark" -> {
                isDarkItemPurchased = true
                editor.putBoolean("isDarkItemPurchased", true)
                darkItemBuyButton.visibility = View.GONE
                darkUseButton.visibility = View.VISIBLE
                darkDisableButton.visibility = View.GONE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cardBackgroundImageView.setBackgroundColor(getColor(R.color.white))
                }
            }
            "blue" -> {
                isBlueItemPurchased = true
                editor.putBoolean("isBlueItemPurchased", true)
                blueItemBuyButton.visibility = View.GONE
                blueUseButton.visibility = View.VISIBLE
                blueDisableButton.visibility = View.GONE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cardBackgroundImageView.setBackgroundColor(getColor(R.color.white))
                }
            }
            "purple" -> {
                isPurpleItemPurchased = true
                editor.putBoolean("isPurpleItemPurchased", true)
                purpleItemBuyButton.visibility = View.GONE
                purpleUseButton.visibility = View.VISIBLE
                purpleDisableButton.visibility = View.GONE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cardBackgroundImageView.setBackgroundColor(getColor(R.color.white))
                }
            }
        }
        balance -= itemPrice
        editor.putInt("balance", balance)
        editor.apply()
        updateBalance()
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun usePurpleItem() {
        // Handle "Use" functionality for purple item
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardBackgroundImageView.setBackgroundColor(getColor(R.color.white))
        }
        purpleUseButton.visibility = View.GONE
        purpleDisableButton.visibility = View.VISIBLE
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun disablePurpleItem() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardBackgroundImageView.setBackgroundColor(getColor(R.color.white))
        }
        purpleUseButton.visibility = View.VISIBLE
        purpleDisableButton.visibility = View.GONE
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun useDarkItem() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardBackgroundImageView.setBackgroundColor(getColor(R.color.white))
        }
        darkUseButton.visibility = View.GONE
        darkDisableButton.visibility = View.VISIBLE
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun disableDarkItem() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardBackgroundImageView.setBackgroundColor(getColor(R.color.white))
        }
        darkUseButton.visibility = View.VISIBLE
        darkDisableButton.visibility = View.GONE
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun useBlueItem() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardBackgroundImageView.setBackgroundColor(getColor(R.color.white))
        }
        blueUseButton.visibility = View.GONE
        blueDisableButton.visibility = View.VISIBLE
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun disableBlueItem() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardBackgroundImageView.setBackgroundColor(getColor(R.color.white))
        }
        blueUseButton.visibility = View.VISIBLE
        blueDisableButton.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun updateBalance() {
        val balance = sharedPreferences.getInt("balance", 0)
        balanceTextView.text = "Balance: $balance"
    }

    @SuppressLint("NonConstantResourceId")
    override fun onClick(v: View) {
        when (v.id) {
            R.id.darkItemBuyButton -> performPurchase("dark", 10)
            R.id.blueItemBuyButton -> performPurchase("blue", 15)
            R.id.purpleItemBuyButton -> performPurchase("purple", 20)
            R.id.darkUseButton -> useDarkItem()
            R.id.darkDisableButton -> disableDarkItem()
            R.id.blueUseButton -> useBlueItem()
            R.id.blueDisableButton -> disableBlueItem()
            R.id.purpleUseButton -> usePurpleItem()
            R.id.purpleDisableButton -> disablePurpleItem()
        }
    }
}
