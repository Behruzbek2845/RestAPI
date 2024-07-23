package com.example.restapi

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.restapi.databinding.ActivityAddBinding
import com.example.restapi.models.ApiClient
import com.example.restapi.utils.MyNotes
import com.example.restapi.utils.PostRequestTodo
import com.example.restapi.utils.postErrorrespons
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "AddActivity"
class AddActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }
    lateinit var myNotes: MyNotes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val key = intent.getIntExtra("key", 1)
        if (key==1){
            postTodo()
        }else{
            myNotes = intent.getSerializableExtra("keyTodo") as MyNotes
            edit()
        }
    }

    private fun edit() {
        binding.apply {
            edtSarlavha.setText(myNotes.sarlavha)
            edtBatafsil.setText(myNotes.batafsil)
            edtZarurlik.setText(myNotes.zarurlik)
            edtBajarildi.setText(myNotes.bajarildi.toString())
            edtOxxirgiMuddat.setText(myNotes.oxirgi_muddat)

            btnSave.setOnClickListener {

                ApiClient.getApiService().update(
                    myNotes.id, PostRequestTodo(
                        myNotes.bajarildi,
                        edtBatafsil.text.toString(),
                        edtOxxirgiMuddat.text.toString(),
                        edtSarlavha.text.toString(),
                        edtZarurlik.text.toString()
                    )
                )
                    .enqueue(object : Callback<MyNotes> {
                        override fun onResponse(p0: Call<MyNotes>, p1: Response<MyNotes>) {
                            Log.d(TAG, "onResponse: $p1")
                            if (p1.isSuccessful) {
                                Toast.makeText(this@AddActivity, "saqlandi", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                val postErrorrespons = Gson().fromJson<postErrorrespons>(
                                    p1.errorBody()?.string(),
                                    postErrorrespons::class.java
                                )
                                val dialog = AlertDialog.Builder(this@AddActivity)
                                dialog.setTitle("Xatolik")
                                dialog.setMessage("${postErrorrespons.zarurlik}")
                                dialog.show()
                            }
                        }

                        override fun onFailure(p0: Call<MyNotes>, p1: Throwable) {
                            Toast.makeText(this@AddActivity, "Error", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
    }

    private fun postTodo() {
        binding.apply {
            binding.btnSave.setOnClickListener {
                val postRequestTodo = PostRequestTodo(
                    false,
                    edtBatafsil.text.toString(),
                    edtOxxirgiMuddat.text.toString(),
                    edtSarlavha.text.toString(),
                    edtZarurlik.text.toString()
                )
                ApiClient.getApiService().addTodo(postRequestTodo)
                    .enqueue(object : Callback<MyNotes>{
                        override fun onResponse(p0: Call<MyNotes>, p1: Response<MyNotes>) {
                            Log.d(TAG, "onResponse: $p1")
                            if (p1.isSuccessful){
                                Toast.makeText(this@AddActivity, "saqlandi", Toast.LENGTH_SHORT).show()
                            }else{
                                val postErrorrespons = Gson().fromJson<postErrorrespons>(p1.errorBody()?.string(), postErrorrespons::class.java)
                                val dialog = AlertDialog.Builder(this@AddActivity)
                                dialog.setTitle("Xatolik")
                                dialog.setMessage("${postErrorrespons.zarurlik}")
                                dialog.show()
                            }
                        }

                        override fun onFailure(p0: Call<MyNotes>, p1: Throwable) {
                            Toast.makeText(this@AddActivity, "Error", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
    }
}