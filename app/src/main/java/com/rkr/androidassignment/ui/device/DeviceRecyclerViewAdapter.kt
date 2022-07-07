package com.rkr.androidassignment.ui.device

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rkr.androidassignment.data.model.DeviceDiscovered
import com.rkr.androidassignment.databinding.FragmentDeviceBinding

/**
 * [RecyclerView.Adapter] that can display a [DeviceDiscovered].
 * TODO: Replace the implementation with code for your data type.
 */
class DeviceRecyclerViewAdapter : RecyclerView.Adapter<DeviceRecyclerViewAdapter.ViewHolder>() {

    private var values: List<DeviceDiscovered> = emptyList()

    fun setValues(list: List<DeviceDiscovered>) {
        values = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentDeviceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.hostAddressView.text = item.hostAddress
        holder.nameView.text = item.serviceName
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentDeviceBinding) : RecyclerView.ViewHolder(binding.root) {
        val hostAddressView: TextView = binding.hostAddress
        val nameView: TextView = binding.name

        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }
    }

}
