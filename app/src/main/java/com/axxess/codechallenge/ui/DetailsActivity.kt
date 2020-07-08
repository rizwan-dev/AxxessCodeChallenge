package com.axxess.codechallenge.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.axxess.codechallenge.R
import com.axxess.codechallenge.db.AppDatabase
import com.axxess.codechallenge.db.MyDatabase
import com.axxess.codechallenge.db.model.Comments
import com.axxess.codechallenge.util.AxxessUtil
import com.axxess.codechallenge.util.Constant
import com.axxess.codechallenge.util.GalleryView
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {
    lateinit var id: String
    lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        enableBackButton()
        initializeViews()
    }

    private fun initializeViews() {
        db = AppDatabase(this)
        val galleryView = intent.getParcelableExtra<GalleryView>(Constant.DATA)
        id = galleryView.id
        img.setImageURI(galleryView.imgUrl)
        GlobalScope.launch(Dispatchers.Main) {
            val comment = db.commentsDao().findById(id)
            edtComments.setText(comment?.comments)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun enableBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    fun submitClick(view: View) {
        AxxessUtil.hideKeyBoard(this, view)
        val comments = edtComments.text.toString().trim()
        if (comments.isEmpty()) {
            toast("Please enter comments")
            return
        }
        GlobalScope.launch(Dispatchers.Main) {
            db.commentsDao().insert(Comments(id, comments))
            toast("Comment saved")
        }
    }

    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}