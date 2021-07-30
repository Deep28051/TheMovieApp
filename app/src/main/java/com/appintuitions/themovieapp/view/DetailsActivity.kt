package com.appintuitions.themovieapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.appintuitions.themovieapp.APIService
import com.appintuitions.themovieapp.R
import com.appintuitions.themovieapp.databinding.ActivityDetailsBinding
import com.appintuitions.themovieapp.repo.AppDatabase
import com.appintuitions.themovieapp.repo.RetroInstance
import com.appintuitions.themovieapp.util.Util
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailsActivity : AppCompatActivity() {

    var binding: ActivityDetailsBinding? = null

    val URL_PREFIX = "https://www.themoviedb.org/t/p/w300_and_h300_face"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_details)

        val id = intent.getIntExtra("movie_id", -1)

        if (id == -1) {
            Toast.makeText(this, "Movie Not Found", Toast.LENGTH_LONG).show()
            gotoMain()
        }
        if (Util.isNetworkAvailable(this))
            CoroutineScope(Dispatchers.IO).launch {
                val response =
                    RetroInstance.getInstance().create(APIService::class.java).getMovieDetails(id)

                Log.e("response details", response.body().toString())

                processResponse(response.body())
            }
        else {
            val movie = AppDatabase.getAppDatabase(application).userDao()?.getMovie(id)

            processResponse(Gson().toJsonTree(movie))

        }
    }

    private fun gotoMain() {

        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()

    }

    private fun processResponse(element: JsonElement?) {

        val body = element?.asJsonObject

        CoroutineScope(Dispatchers.Main).launch {
            if (body != null) {

                pb_loading.visibility = View.GONE
                lt_root.visibility = View.VISIBLE

                title = body.get("title").asString
                tv_title.text = body.get("title").asString

                Glide
                    .with(this@DetailsActivity)
                    .load(URL_PREFIX + body.get("poster_path").asString)
                    .centerCrop()
                    .placeholder(R.drawable.tmd)
                    .into(iv_dp)

                tv_overview.text = body.get("overview").asString
                tv_date.text = body.get("release_date").asString
                tv_lang.text = body.get("original_language").asString

                if (body.has("genres"))
                    body.get("genres").asJsonArray.forEach {
                        chipGroup.addChip(
                            this@DetailsActivity,
                            it.asJsonObject.get("name").asString
                        )
                    }

                rb_rating.rating = body.get("vote_average").asFloat / 2

                rb_rating.isEnabled = false
            }
        }
    }


}

fun ChipGroup.addChip(context: Context, label: String) {
    Chip(context).apply {
        id = View.generateViewId()
        text = label
        isClickable = true
        isCheckable = false
        isCheckedIconVisible = false
        isFocusable = true
        addView(this)
    }
}