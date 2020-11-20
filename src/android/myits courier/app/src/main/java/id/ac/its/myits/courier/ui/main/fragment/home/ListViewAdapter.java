package id.ac.its.myits.courier.ui.main.fragment.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.its.myits.courier.R;

public class ListViewAdapter extends BaseAdapter {

    @BindView(R.id.unitName)
    TextView unit_name;

    @BindView(R.id.numOfJobs)
    TextView number_of_jobs;

    public ArrayList<String> unitName;
    public ArrayList<Integer> numOfJobs;

    public ListViewAdapter(ArrayList<String> unitName, ArrayList<Integer> numOfJobs) {
        this.unitName = unitName;
        this.numOfJobs = numOfJobs;
    }

    @Override
    public int getCount() {
        return unitName.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.unit_list, viewGroup, false);

        ButterKnife.bind(this, v);
        unit_name.setText(unitName.get(i));
        number_of_jobs.setText(String.format(Locale.getDefault(), "%d Pekerjaan", numOfJobs.get(i)));
        return v;
    }
}
