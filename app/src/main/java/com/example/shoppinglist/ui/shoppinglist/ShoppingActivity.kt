package com.example.shoppinglist.ui.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.data.db.ShoppingDatabase
import com.example.shoppinglist.data.db.entities.ShoppingItem
import com.example.shoppinglist.data.repositories.ShoppingRepository
import com.example.shoppinglist.other.ShoppingItemAdapter
import kotlinx.android.synthetic.main.activity_shopping.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ShoppingActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: ShoppingViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        val viewModel = ViewModelProviders.of(this, factory).get(ShoppingViewModel::class.java)

        val adapter = ShoppingItemAdapter(listOf(),viewModel)

        rvShoppingItems.layoutManager = LinearLayoutManager(this)
        rvShoppingItems.adapter = adapter

        viewModel.getAllShoppingItem().observe(this, Observer{
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

        fab.setOnClickListener{
            AddShoppingItemDialog(this, object : AddDialogListener{
                override fun onAddButtonClicked(item: ShoppingItem) {
                    viewModel.upsert(item)
                }
            }).show()
        }
    }
}