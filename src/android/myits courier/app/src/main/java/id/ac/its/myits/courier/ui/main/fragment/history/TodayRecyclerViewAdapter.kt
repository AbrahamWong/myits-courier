package id.ac.its.myits.courier.ui.main.fragment.history

import id.ac.its.myits.courier.ui.main.fragment.history.dummy.Today
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.its.myits.courier.R

class TodayRecyclerViewAdapter(
    private val values: List<Today.TodayItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return ITEM_VIEW_TYPE_HEADER
        }
        return ITEM_VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            val header =
                LayoutInflater.from(parent.context).inflate(R.layout.history_header, parent, false)
            return ViewHolderHeader(header)
        }

        val header =
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return ViewHolder(header)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderHeader) {
            holder.historyHeaderTitle.setText("Total Pekerjaan")
            holder.numOfJobs.text = values.size.toString()
        }

        if (holder is ViewHolder) {
            val listItem = values[position - 1]
            holder.deliveryType.text = listItem.deliveryType
            holder.packageCode.text = "Kode : " + listItem.packageCode
            holder.numOfPackage.text = listItem.numOfPackage.toString() + " Paket"
            holder.reciverName.text = listItem.reciverName
            holder.status.text = listItem.status
        }
    }

    override fun getItemCount(): Int = values.size + 1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val deliveryType: TextView = view.findViewById(R.id.deliveryType)
        val packageCode: TextView = view.findViewById(R.id.packageCode)
        val numOfPackage: TextView = view.findViewById(R.id.numOfPacket)
        val reciverName: TextView = view.findViewById(R.id.reciverName)
        val status: TextView = view.findViewById(R.id.packageStatus)
    }

    class ViewHolderHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val historyHeaderTitle = itemView.findViewById(R.id.historyHeaderTitle) as TextView
        val numOfJobs = itemView.findViewById(R.id.numOfJobs) as TextView
    }
}