package id.ac.its.myits.courier.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.data.db.model.DetilStatus;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    // Masih menunggu CDM dan PDM, benerin dlu modelmu
    ArrayList<DetilStatus> detilStatuses;

    public HistoryAdapter(ArrayList<DetilStatus> detilStatuses) {
        this.detilStatuses = detilStatuses;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.activity_history_detail_timeline_item, parent, false),
                viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        DetilStatus status = detilStatuses.get(position);

        if (!status.getDate().isEmpty()) {
            holder.date.setVisibility(View.VISIBLE);

            // Please find and create the equivalent of
            // holder.date.text =
            //                    timeLineModel.date.formatDateTime("yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy")
            holder.date.setText(status.getDate());
        } else {
            holder.date.setVisibility(View.GONE);
        }

        holder.status.setText(status.getStatus());
    }

    @Override
    public int getItemCount() {
        return detilStatuses.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_timeline_date)
        TextView date;

        @BindView(R.id.text_timeline_title)
        TextView status;

        @BindView(R.id.timeline)
        TimelineView timeline;

        public HistoryViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            timeline.initLine(viewType);
        }

    }
}
