package com.example.searchimageapp.imagedetails

import android.content.ContentValues
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.searchimageapp.R
import com.example.searchimageapp.network.response.GetImagesResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class ImageDetailsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var ivBack: ImageView
    private lateinit var ivImageSearched: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var etComment: EditText
    private lateinit var btnSubmit: Button
    private lateinit var listView: ListView
    private var imageSearch: GetImagesResponse? = null
    private lateinit var dbHelper: ImagesDBHelper
    lateinit var imageComments : ArrayList<String>
    lateinit var comments: String
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_details)
        findViewsNInitListeners()
    }

    private fun findViewsNInitListeners() {
        dbHelper = ImagesDBHelper(this)
        imageSearch = intent.getParcelableExtra("TAG");
        tvTitle = findViewById(R.id.tv_app_bar_title)
        ivBack = findViewById(R.id.iv_app_bar_back_close)
        ivBack.setOnClickListener(this)
        ivImageSearched = findViewById(R.id.iv_image)
        etComment = findViewById(R.id.et_comment)
        listView = findViewById(R.id.lv_comment_list)
        btnSubmit = findViewById(R.id.btn_submit)
        btnSubmit.setOnClickListener(this)
        imageComments = ArrayList<String>()

        setUI()
        getComments()
    }

    private fun getComments() {
        comments = dbHelper.readComment(imageSearch?.id)
        if(!TextUtils.isEmpty(comments)){
            val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type
            imageComments = gson.fromJson(comments, type);
            setAdapter(imageComments)
        }
    }

    private fun setAdapter(listItems: ArrayList<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter
    }

    private fun setUI() {
        tvTitle.setText(imageSearch?.title)
        Glide.with(this).load(imageSearch?.images?.get(0)?.link)
                .error(R.drawable.ic_launcher_foreground).placeholder(R.drawable.ic_launcher_foreground)
                .into(ivImageSearched)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.iv_app_bar_back_close->{
                finish()
            }
            R.id.btn_submit->{
                if(!TextUtils.isEmpty(etComment.text.toString().trim()))
                    submitComment()
                else
                    Toast.makeText(this, "Please enter comment", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun submitComment() {
        imageComments.add(etComment.text.toString())
        val inputString = gson.toJson(imageComments)
        // Gets the data repository in write mode
        val db = dbHelper.writableDatabase

        // Delete existing record before saving new record
        /*String query = "Select * From STUDENTS where name = '"+name+"'";
        if(sqLiteHelper.getData(query).getCount()>0){

        }*/

        if(comments.length > 0){
            dbHelper.delete(imageSearch?.id)
        }
        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(DBContract.UserEntry.COLUMN_ID, imageSearch?.id)
            put(DBContract.UserEntry.COLUMN_COMMENT, inputString)
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(DBContract.UserEntry.TABLE_NAME, null, values)

        if(newRowId!! > -1){
            Toast.makeText(this, "Comment Saved", Toast.LENGTH_LONG).show()
            getComments()
        }else
            Toast.makeText(this, "Comment not Saved", Toast.LENGTH_LONG).show()
    }
}
