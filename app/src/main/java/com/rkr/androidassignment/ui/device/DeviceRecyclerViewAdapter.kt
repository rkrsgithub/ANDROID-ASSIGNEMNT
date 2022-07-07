package com.rkr.androidassignment.ui.device

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rkr.androidassignment.data.model.DeviceDiscovered
import com.rkr.androidassignment.databinding.FragmentDeviceBinding

/**
 * [RecyclerView.Adapter] that can display a [DeviceDiscovered]
 */
class DeviceRecyclerViewAdapter : RecyclerView.Adapter<DeviceRecyclerViewAdapter.ViewHolder>() {

    private var values: List<DeviceDiscovered> = emptyList()

    private var onClickItem: ((`object`: Any?, position: Int, actionView: View) -> Unit)? = null

    fun setOnItemClickListener(onClickItem: (`object`: Any?, position: Int, actionView: View) -> Unit) {
        this.onClickItem = onClickItem
    }

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
        ).apply {
            setOnItemClickListener { position, actionView ->
                onClickItem?.let { onClick -> onClick(values[position], position, actionView) }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var onClickItem: ((position: Int, actionView: View) -> Unit)? = null

        fun setOnItemClickListener(onClickItem: (position: Int, actionView: View) -> Unit) {
            this.onClickItem = onClickItem
        }

        init {
            itemView.setOnClickListener {
                onClickItem?.let { onClick ->
                    onClick(
                        bindingAdapterPosition,
                        it
                    )
                }
            }
        }

        fun bind(deviceDiscovered: DeviceDiscovered) {
            binding.model = deviceDiscovered
            binding.executePendingBindings()
        }
    }
}
