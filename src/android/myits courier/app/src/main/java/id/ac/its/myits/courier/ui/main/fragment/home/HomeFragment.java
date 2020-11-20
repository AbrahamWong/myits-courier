package id.ac.its.myits.courier.ui.main.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

import id.ac.its.myits.courier.R;

public class HomeFragment extends Fragment {

    private ArrayList<String> unitName = new ArrayList<>(
            Arrays.asList(
                    "Unit Teknik Informatika",
                    "Unit Sistem Informasi",
                    "Unit Gedung Riset"
            )
    );

    private ArrayList<Integer> numOfJobs = new ArrayList<>(Arrays.asList(3, 1, 2));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert container != null;
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home, container, false);
        ListView homeListView = v.findViewById(R.id.homeListview);
        homeListView.setAdapter(new ListViewAdapter(unitName, numOfJobs));

        return v;
    }

}
