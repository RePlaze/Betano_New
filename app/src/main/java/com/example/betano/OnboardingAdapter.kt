package com.example.betano

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.betano.OnboardingAdapter.OnboardingViewHolder

private class OnboardingAdapter(private val onboardingItems: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_onboarding, parent, false)
        return OnboardingViewHolder(view)
    }


    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        val item = onboardingItems[position]
        holder.imageView.setImageResource(item.imageResId)
        holder.textTitle.text = item.title
        holder.textDescription.text = item.description
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }

    class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var textTitle: TextView
        var textDescription: TextView

        init {
            imageView = itemView.findViewById(R.id.imageView)
            textTitle = itemView.findViewById(R.id.textTitle)
            textDescription = itemView.findViewById(R.id.textDescription)
        }
    }
}