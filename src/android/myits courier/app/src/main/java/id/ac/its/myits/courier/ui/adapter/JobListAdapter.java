package id.ac.its.myits.courier.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.data.db.model.DetilPekerjaan;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.JobListVH> {

    ArrayList<DetilPekerjaan> data;
    private OnClickListener listener;

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public JobListAdapter(ArrayList<DetilPekerjaan> data) {
        this.data = data;
    }

    @Override
    public void onBindViewHolder(@NonNull JobListVH holder, int position) {
        holder.bind(data.get(position));

        holder.itemView.setOnClickListener(view -> {
            listener.OnItemClicked(data.get(position));
        });
    }

    @NonNull
    @Override
    public JobListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JobListVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_job_list_item, parent, false));
    }

    public interface OnClickListener {
        void OnItemClicked(DetilPekerjaan detil);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class JobListVH extends RecyclerView.ViewHolder {
        @BindView(R.id.statusText)
        TextView status;

        @BindView(R.id.deliveryTypeText)
        TextView deliveryType;

        @BindView(R.id.kodeText)
        TextView packageCode;

        @BindView(R.id.numOfPacket)
        TextView packageQty;

        @BindView(R.id.staffName)
        TextView staffName;

        public JobListVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(DetilPekerjaan pekerjaan) {
            status.setText(pekerjaan.getStatus());
            deliveryType.setText(pekerjaan.getJenisPaket());
            packageCode.setText(pekerjaan.getKodePaket());
            packageQty.setText(String.format(Locale.ENGLISH, "%d paket",
                    pekerjaan.getJumlahPaket()));
            staffName.setText(pekerjaan.getNamaPetugas());
        }
    }
}
