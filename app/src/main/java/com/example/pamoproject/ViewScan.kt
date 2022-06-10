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
        val scannedData: TextView =view.findViewById(R.id.txtScanResult)
        val ctime: TextView = view.findViewById(R.id.txtData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.book_information,parent,false)
        return ReadViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReadViewHolder, position: Int) {
        val book=itemList[position]
        holder.scannedData.text=book.scannedData
        holder.ctime.text=book.data
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}