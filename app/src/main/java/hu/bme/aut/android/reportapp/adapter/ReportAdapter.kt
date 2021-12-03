package hu.bme.aut.android.reportapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.reportapp.R
import hu.bme.aut.android.reportapp.data.Report
import java.util.concurrent.TimeoutException

class ReportAdapter():
RecyclerView.Adapter<ReportAdapter.TrashLocationItemViewHolder>() {
    private val items = mutableListOf<Report>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrashLocationItemViewHolder {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_location_list, parent, false)
        return TrashLocationItemViewHolder(itemView)
    }

    inner class TrashLocationItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvReportTypeName: TextView = itemView.findViewById(R.id.tvReportTypeName)
        val tvReportTypeId: TextView = itemView.findViewById(R.id.tvReportTypeId)
        val tvStationName: TextView = itemView.findViewById(R.id.tvStationName)
        val tvTransportType: TextView = itemView.findViewById(R.id.tvTransportType)
        val tvCoords: TextView = itemView.findViewById(R.id.tvCoords)
        var item: Report? = null

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TrashLocationItemViewHolder, position: Int) {
        val item = items[position]

        /**
         * Conversion
         * holder.<name in class ReportItemViewHolder.text = item.<name in ReportItem>[.toString()]>
         */

        holder.tvReportTypeName.text = item.reportType
        holder.tvReportTypeId.text = item.id.toString()
        holder.tvStationName.text = item.stationName
        holder.tvTransportType.text = item.transportType
        holder.tvCoords.text = item.latitude.toString() + " " + item.longitude.toString()

        holder.item = item
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun addCollectionPoint(report: Report){
        items.add(report)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAllCollectionPoint(){
        items.clear()
        notifyDataSetChanged()
    }

    /*private fun getTodayAsString(): LocalDateTime {
        return LocalDateTime.now()
    }*/
}