package com.example.busvn

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var fragment: Fragment? = null
    private var bottomBar: BottomNavigationView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomBar = findViewById(R.id.bottomBar)

        fragment = HomeFragment()
        val fragmentManager: FragmentManager = supportFragmentManager
        var transaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragment?.let { transaction.replace(R.id.contentPanel, it).commit() }

        bottomBar?.setOnNavigationItemSelectedListener { item ->
            val id = item.itemId
            when (id) {
                R.id.actionTimDuong -> fragment = HomeFragment()
                R.id.actionTraCuu -> fragment = ListBusFragment()
                R.id.actionChat -> fragment = ChatFragment()
            }
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.contentPanel, fragment!!).commit()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}