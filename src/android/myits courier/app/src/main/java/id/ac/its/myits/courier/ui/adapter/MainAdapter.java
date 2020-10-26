package id.ac.its.myits.courier.ui.adapter;

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
import id.ac.its.myits.courier.data.db.model.Paket;
import id.ac.its.myits.courier.data.db.model.Unit;
import id.ac.its.myits.courier.ui.main.MainMvpPresenter;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    // Biasanya list, tapi setelah menelusuri internet, demi menerapkan MVP,
    // panggil presenter untuk mendapatkan data yang akan digunakan.
    MainMvpPresenter presenter;

    public MainAdapter(MainMvpPresenter presenter) {
        this.presenter = presenter;
    }

    // Buat interface callback untuk mengecek saat view ditekan.
    public interface MainAdapterCallback {
        void onItemClicked(Unit unit);
    }

    private MainAdapterCallback callback;
    public void setOnItemClicked(MainAdapterCallback adapterCallback) {
        this.callback = adapterCallback;
    }

    @NonNull
    @Override
    public MainAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_unit, parent, false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MainViewHolder holder, final int position) {
        // Lanjutin nanti dong, bingung moment
        holder.bind((Unit) presenter.getUnit().get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onItemClicked((Unit) presenter.getUnit().get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return presenter.getUnit().size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_nama_tata_usaha)
        TextView tvTataUsaha;

        @BindView(R.id.tv_jumlah_eksternal)
        TextView tvEksternal;

        @BindView(R.id.tv_jumlah_internal)
        TextView tvInternal;

        @BindView(R.id.tv_jumlah_penjemputan)
        TextView tvPenjemputanInternal;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Unit unit) {
            tvTataUsaha.setText(unit.getNamaUnit());

            ArrayList<Paket> daftarPaket = unit.getDaftarPaket();
            int eksternal = 0, pengirimanInternal = 0, penjemputanInternal = 0;
            for (Paket paket : daftarPaket) {
                switch (paket.getTipePaket()) {
                    case PENGIRIMAN_EKSTERNAL:
                        eksternal++;
                        break;
                    case PENGIRIMAN_INTERNAL:
                        pengirimanInternal++;
                        break;
                    case PENJEMPUTAN_INTERNAL:
                        penjemputanInternal++;
                        break;
                }
            }

            tvEksternal.setText(itemView.getResources().getString(R.string.external_package_qty, eksternal));
            tvInternal.setText(itemView.getResources().getString(R.string.internal_package_qty, pengirimanInternal));
            tvPenjemputanInternal.setText(itemView.getResources().getString(R.string.pickup_internal_qty, penjemputanInternal));
        }
    }
}
