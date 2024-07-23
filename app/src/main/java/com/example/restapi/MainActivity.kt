package com.example.restapi

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.restapi.adapters.RvAdapter
import com.example.restapi.databinding.ActivityMainBinding
import com.example.restapi.models.ApiClient
import com.example.restapi.utils.MyNotes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), RvAdapter.RvAction {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var rvAdapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra("key", 1) //1 bu Add uchun
            startActivity(intent)
        }

        ApiClient.getApiService().getNotes()
            .enqueue(object : Callback<List<MyNotes>>{
                override fun onResponse(p0: Call<List<MyNotes>>, p1: Response<List<MyNotes>>) {
                    if (p1.isSuccessful){
                        rvAdapter = RvAdapter(this@MainActivity, p1.body() as ArrayList<MyNotes>)
                        binding.rv.adapter = rvAdapter
                    }
                }

                override fun onFailure(p0: Call<List<MyNotes>>, p1: Throwable) {
                    Toast.makeText(this@MainActivity, "ERROR", Toast.LENGTH_SHORT).show()
                }
            })


    }

    override fun onClick(myNotes: MyNotes, position: Int, imageView: ImageView) {
        val popupMenu = PopupMenu(this, imageView)
        popupMenu.inflate(R.menu.item_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.edit -> {
                    val intent = Intent(this, AddActivity::class.java)
                    intent.putExtra("keyTodo", myNotes)
                    intent.putExtra("key", 2)
                    startActivity(intent)
                }
                R.id.delete -> {
                    ApiClient.getApiService().deleteTodo(myNotes.id)
                        .enqueue(object : Callback<Any>{
                            override fun onResponse(p0: Call<Any>, p1: Response<Any>) {
                                if (p1.isSuccessful){
                                    Toast.makeText(this@MainActivity, "O'chirildi", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(p0: Call<Any>, p1: Throwable) {
                                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        })
                }
            }
            true
        }
        popupMenu.show()
    }
}