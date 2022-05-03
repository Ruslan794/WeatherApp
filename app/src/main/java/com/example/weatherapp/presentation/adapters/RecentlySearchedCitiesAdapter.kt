package com.example.weatherapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.City
import com.example.weatherapp.R

class RecentlySearchedCitiesAdapter(
    context: Context,
    var cityList: List<City?>
) : RecyclerView.Adapter<RecentlySearchedCitiesAdapter.BaseViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int = cityList.size

    private fun getItem(position: Int): City? = cityList[position]

    override fun getItemViewType(position: Int): Int {
        return if (cityList.contains(null)) R.layout.empty_list_item_view_holder
        else R.layout.recently_searched_city_view_holder
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            R.layout.recently_searched_city_view_holder -> CityItemViewHolder(
                inflater.inflate(
                    R.layout.recently_searched_city_view_holder,
                    parent,
                    false
                )
            )
            else -> EmptyListViewHolder(
                inflater.inflate(
                    R.layout.empty_list_item_view_holder,
                    parent,
                    false
                )
            )
        }
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(cityItem: City?)
    }

    class CityItemViewHolder(view: View) : BaseViewHolder(view) {
        private val city: TextView = itemView.findViewById(R.id.city)
        override fun bind(cityItem: City?) {
            city.text = "${cityItem!!.name}, ${cityItem.country}"
        }
    }

    class EmptyListViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(cityItem: City?) {}
    }
}
