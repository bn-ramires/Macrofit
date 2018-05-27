package com.example.fujiwara.macrocalc.activities

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.fujiwara.macrocalc.R
import com.example.fujiwara.macrocalc.adapters.EntriesAdapter
import com.example.fujiwara.macrocalc.api.model.Login
import com.example.fujiwara.macrocalc.api.service.Api
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.intentFor


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val TAG: String = javaClass.simpleName
    val api = Api.Request(this)

    fun updateEntries() {

        api.listOfEntries(api.user.token)

        Handler().postDelayed({
            val adapter = EntriesAdapter(api.entries, this)
            adapter.setHasStableIds(true)
            recycler_view.adapter = adapter

            Toast.makeText(this, "Sync successful!",
                    Toast.LENGTH_SHORT).show()

        }, 1200)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val test = Login("testandopravaler@gmail.com", "testando86")

        api.login(test)

        recycler_view.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false)

        fab.setOnClickListener { _ ->

            startActivity(intentFor<AddUserActivity>())

        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//        longToast("Displays settings!")
//
//        when (item.itemId) {
//            R.id.action_settings -> return true
//            else -> return super.onOptionsItemSelected(item)
//        }
//    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_intro -> {

                startActivity(intentFor<TestActivity>())

            }
            R.id.nav_sync -> {
                updateEntries()
            }
            R.id.nav_preferences -> {

                startActivity(intentFor<SettingsActivity>())

            }
            R.id.nav_share -> {

            }
            R.id.nav_contact -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true

    }
}
