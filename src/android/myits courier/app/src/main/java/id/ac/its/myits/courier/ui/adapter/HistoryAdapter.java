package id.ac.its.myits.courier.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.data.db.model.DetilStatus;
import id.ac.its.myits.courier.utils.AppLogger;

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
        String simplifiedDate = null, statusDate;

        if (!status.getDate().isEmpty()) {
            holder.date.setVisibility(View.VISIBLE);

            statusDate = status.getDate();
            statusDate = statusDate.substring(0, statusDate.length() - 4).concat("Z");
            SimpleDateFormat ISODateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.S'Z'", Locale.getDefault());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy, hh:mm:ss", Locale.getDefault());
            try {
                Date d = ISODateFormat.parse(statusDate);
                simplifiedDate = simpleDateFormat.format(d);
            } catch (ParseException pe) {
                AppLogger.d("DATE: %s", statusDate);
                AppLogger.e(pe, "DATE: gagal");
            }

            holder.date.setText(simplifiedDate);
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
