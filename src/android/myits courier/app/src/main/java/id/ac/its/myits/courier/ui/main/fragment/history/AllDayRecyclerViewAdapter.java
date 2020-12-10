package id.ac.its.myits.courier.ui.main.fragment.history;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.data.db.model.DetilPekerjaan;
import id.ac.its.myits.courier.ui.historydetail.HistoryDetailActivity;

public class AllDayRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DetilPekerjaan> jobDetails;
    private int totalJobs;

    public AllDayRecyclerViewAdapter(ArrayList<DetilPekerjaan> jobDetails, int totalJobs) {
        this.jobDetails = jobDetails;
        this.totalJobs = totalJobs;
    }

    private int ITEM_VIEW_TYPE_HEADER = 0;
    private int ITEM_VIEW_TYPE_ITEM = 1;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_VIEW_TYPE_HEADER;
        }
        return ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            View header = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_header, parent, false);
            return new ViewHolderHeader(header);
        }

        View header = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_item, parent, false);
        return new ViewHolder(header);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderHeader) {
            ViewHolderHeader vhh = (ViewHolderHeader) holder;
            vhh.historyHeaderTitle.setText("Total Pekerjaan");
            vhh.numOfJobs.setText(totalJobs + "");
        }

        if (holder instanceof ViewHolder) {
            DetilPekerjaan listItem = jobDetails.get(position - 1);
            ViewHolder vh = (ViewHolder) holder;
            vh.deliveryType.setText(listItem.getJenisPaket());
            vh.packageCode.setText("Kode : " + listItem.getKodePaket());
            vh.numOfPackage.setText(listItem.getJumlahPaket() + " Paket");
            vh.receiverName.setText(listItem.getNamaPetugas());
            vh.status.setText(listItem.getStatus());
            vh.itemView.setOnClickListener(view -> {
                Context ctx = view.getContext();
                Intent historyIntent = new Intent(ctx, HistoryDetailActivity.class);
                historyIntent.putExtra("KODE_PAKET", listItem.getKodePaket());

                ctx.startActivity(historyIntent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return jobDetails.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.deliveryType)
        TextView deliveryType;

        @BindView(R.id.packageCode)
        TextView packageCode;

        @BindView(R.id.numOfPacket)
        TextView numOfPackage;

        @BindView(R.id.receiverName)
        TextView receiverName;

        @BindView(R.id.packageStatus)
        TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolderHeader extends RecyclerView.ViewHolder {
        @BindView(R.id.historyHeaderTitle)
        TextView historyHeaderTitle;

        @BindView(R.id.numOfJobs)
        TextView numOfJobs;

        public ViewHolderHeader(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
