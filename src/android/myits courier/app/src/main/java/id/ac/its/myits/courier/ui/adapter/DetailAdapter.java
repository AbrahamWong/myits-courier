package id.ac.its.myits.courier.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.its.myits.courier.R;
import id.ac.its.myits.courier.data.db.model.Paket;
import id.ac.its.myits.courier.ui.detail.DetailMvpPresenter;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {

    // Biasanya list, tapi setelah menelusuri internet, demi menerapkan MVP,
    // panggil presenter untuk mendapatkan data yang akan digunakan.
    DetailMvpPresenter presenter;

    public DetailAdapter(DetailMvpPresenter presenter) {
        this.presenter = presenter;
    }

    // ---------------- MASIH BELUM KELAR, JANGAN DIRUN DULU ---------------------

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_paket, parent, false);
        return new DetailViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        holder.bind((Paket) presenter.onPaketRequested().get(position));
    }

    @Override
    public int getItemCount() {
        return presenter.onPaketRequested().size();
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv_paket)
        CardView cvPaket;

        @BindView(R.id.tv_tipe_paket)
        TextView tvTipe;

        @BindView(R.id.tv_prioritas_paket)
        TextView tvPrioritas;

        @BindView(R.id.tv_status_paket)
        TextView tvStatus;

        @BindView(R.id.tv_berat_paket)
        TextView tvBerat;

        @BindView(R.id.tv_nomor_resi)
        TextView tvResi;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Paket paket) {
            tvTipe.setText(paket.getTipePaket().toString());
            tvPrioritas.setText(itemView.getResources().getString(R.string.package_priority, paket.getPrioritas()));
            tvStatus.setText(itemView.getResources().getString(R.string.package_status, paket.getStatusPaket()));
            tvBerat.setText(itemView.getResources().getString(R.string.package_weight, paket.getBerat()));
            tvResi.setText(itemView.getResources().getString(R.string.package_receipt_number, paket.getNomorResi()));

            switch (paket.getTipePaket()) {
                case PENGIRIMAN_EKSTERNAL:
                    cvPaket.setCardBackgroundColor(itemView.getResources().getColor(android.R.color.holo_green_light, null));
                    break;
                case PENGIRIMAN_INTERNAL:
                    cvPaket.setCardBackgroundColor(itemView.getResources().getColor(android.R.color.holo_blue_bright, null));
                    break;
                case PENJEMPUTAN_INTERNAL:
                    cvPaket.setCardBackgroundColor(itemView.getResources().getColor(android.R.color.holo_red_light, null));
                    break;
            }
        }
    }
}
