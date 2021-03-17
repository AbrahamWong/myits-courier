package id.ac.its.myits.courier.ui.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
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
        @BindView(R.id.card)
        CardView card;

        @BindView(R.id.separatorVertical)
        View separatorVertical;
        @BindView(R.id.separatorHorizontal)
        View separatorHorizontal;
        @BindView(R.id.packetIcon)
        View packageIcon;
        @BindView(R.id.personIcon)
        View staffIcon;

        @BindView(R.id.statusLabel)
        TextView statusLabel;

        @BindView(R.id.statusText)
        TextView status;

        @BindView(R.id.deliveryTypeText)
        TextView deliveryType;

        @BindView(R.id.kodeLabel)
        TextView packageCodeLabel;

        @BindView(R.id.kodeText)
        TextView packageCode;

        @BindView(R.id.numOfPacket)
        TextView packageQty;

        @BindView(R.id.staffName)
        TextView staffName;

        @BindView(R.id.lateIdentifier)
        TextView lateIdentifier;

        @BindView(R.id.shiftLabel)
        TextView shiftLabel;

        @BindView(R.id.shiftText)
        TextView courierShift;

        public JobListVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(DetilPekerjaan pekerjaan) {
            lateIdentifier.setVisibility(pekerjaan.isTerlambat() == 1 ? View.VISIBLE : View.GONE);

            if (pekerjaan.getJenisPaket().equals("Eksternal")) {
                changeExternalTheme();
            } else {
                changeInternalTheme();
            }

            status.setText(pekerjaan.getStatus());
            deliveryType.setText(pekerjaan.getJenisPaket());
            packageCode.setText(pekerjaan.getKodePaket());
            packageQty.setText(String.format(Locale.ENGLISH, "%d paket",
                    pekerjaan.getJumlahPaket()));
            staffName.setText(pekerjaan.getNamaPetugas());
            courierShift.setText(pekerjaan.getPackageShift());
        }

        void changeInternalTheme() {
            // Ubah warna card menjadi warna kuning jika paket internal
            Context ctx = itemView.getContext();
            card.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.colorSecondary));

            statusLabel.setTextColor(ContextCompat.getColor(ctx, R.color.colorPrimary));
            status.setTextColor(ContextCompat.getColor(ctx, R.color.colorPrimary));

            deliveryType.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
            packageCodeLabel.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
            packageCode.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
            shiftLabel.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
            courierShift.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));

            packageQty.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
            staffName.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));

            separatorHorizontal.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorWhite));
            separatorVertical.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorPrimary));

            packageIcon.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.colorPrimary)));
            staffIcon.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.colorPrimary)));
        }

        void changeExternalTheme() {
            // Ubah warna card menjadi warna biru jika paket eksternal
            Context ctx = itemView.getContext();
            card.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.colorPrimary));

            statusLabel.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
            status.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));

            deliveryType.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
            packageCodeLabel.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
            packageCode.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
            shiftLabel.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
            courierShift.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));


            packageQty.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
            staffName.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));

            separatorHorizontal.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorSecondary));
            separatorVertical.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorSecondary));

            packageIcon.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.colorSecondary)));
            staffIcon.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.colorSecondary)));
        }
    }
}
