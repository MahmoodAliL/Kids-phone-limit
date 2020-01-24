package com.teaml.kidsphonelimit.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teaml.kidsphonelimit.R

class MinutesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val minutesView =
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_item_minute,
                parent,
                false
            )

        return MinuteViewHolder(minutesView)
    }

    override fun getItemCount(): Int = 59

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val minutesVh = holder as MinuteViewHolder

        minutesVh.onBind(position)
    }

    class MinuteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val minuteTextView = itemView.findViewById<TextView>(R.id.minute_tv)
        fun onBind(minutes: Int) {
            minuteTextView.text = String.format("%d", minutes)
        }
    }

}