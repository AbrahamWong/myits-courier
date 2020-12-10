package id.ac.its.myits.courier.ui.adapter;

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
import id.ac.its.myits.courier.ui.job.JobActivity;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.JobListVH> {

    ArrayList<DetilPekerjaan> data;

    public JobListAdapter(ArrayList<DetilPekerjaan> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public JobListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JobListVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_job_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JobListVH holder, int position) {
        holder.bind(data.get(position));

        holder.itemView.setOnClickListener(view -> {
            Context ctx = view.getContext();
            Intent jobIntent = new Intent(ctx, JobActivity.class);

            if (data.get(position).getJenisPaket().equals("Eksternal")) {
                jobIntent.putExtra("ID_PAKET", data.get(position).getIdPaket());
                jobIntent.putExtra("TIPE_PAKET", data.get(position).getJenisPaket());

            } else {
                jobIntent.putExtra("KODE_INTERNAL", data.get(position).getKodePaket());
                jobIntent.putExtra("TIPE_PAKET", data.get(position).getJenisPaket());
            }

            ctx.startActivity(jobIntent);
        });
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
            packageQty.setText(pekerjaan.getJumlahPaket() + " paket");
            staffName.setText(pekerjaan.getNamaPetugas());
        }
    }
}
