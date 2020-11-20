package id.ac.its.myits.courier.ui.main.fragment.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.ac.its.myits.courier.ui.main.fragment.history.dummy.Today;
import id.ac.its.myits.courier.R;

public class TodayFragment extends Fragment {

    static String ARG_COLUMN_COUNT = "column-count";
    private static int columnCount = 1;

//    Masih belum paham caranya pakai Bundle dan newInstance.
//    public static TodayFragment newInstance() {
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//
//        TodayFragment fragment = new TodayFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Masih belum paham caranya pakai Bundle dan newInstance.
//        assert getArguments() != null;
//        columnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        columnCount = 1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_history_today, container, false);

        if (v instanceof RecyclerView) {
            if (columnCount <= 1) {
                ((RecyclerView) v).setLayoutManager(new LinearLayoutManager(getContext()));
            } else {
                ((RecyclerView) v).setLayoutManager(new GridLayoutManager(getContext(), columnCount));
            }

            ((RecyclerView) v).setAdapter(new TodayRecyclerViewAdapter(Today.ITEMS));
        }

        return v;
    }
}
