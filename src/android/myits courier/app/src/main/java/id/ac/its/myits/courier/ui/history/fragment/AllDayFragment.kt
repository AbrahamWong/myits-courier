package id.ac.its.myits.courier.ui.history.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.its.myits.courier.R
import id.ac.its.myits.courier.ui.history.HistoryDetailActivity
import id.ac.its.myits.courier.ui.history.fragment.dummy.AllDay

/**
 * A fragment representing a list of Items.
 */
class AllDayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history_allday, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = AllDayRecyclerViewAdapter(AllDay.ITEMS) {
                    // handle onclick listener
                    startActivity(Intent(context, HistoryDetailActivity::class.java))
                }
            }
        }
        return view
    }
}