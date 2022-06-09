package com.example.pamoproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewScan(val context: Context, val itemList:ArrayList<Book>)
    :RecyclerView.Adapter<ViewScan.ReadViewHolder>() {

    class ReadViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtScanResult: TextView =view.findViewById(R.id.txtScanResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.book_information,parent,false)
        return ReadViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReadViewHolder, position: Int) {
        val book=itemList[position]
        holder.txtScanResult.text=book.barcode
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}