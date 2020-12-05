package id.ac.its.myits.courier.ui.main.fragment.home;

import android.content.Context;
import android.content.Intent;
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
import id.ac.its.myits.courier.ui.joblist.JobListActivity;
import id.ac.its.myits.courier.ui.main.MainActivity;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ListViewHolder> {

    public ArrayList<String> unitName;
    public ArrayList<Integer> numOfJobs;
    public ArrayList<Integer> unitId;

    public ListViewAdapter(ArrayList<String> unitName, ArrayList<Integer> numOfJobs, ArrayList<Integer> unitId) {
        this.unitName = unitName;
        this.numOfJobs = numOfJobs;
        this.unitId = unitId;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_home_unit_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.unit_name.setText(unitName.get(position));
        holder.number_of_jobs.setText(
                String.format(Locale.getDefault(), "%d Pekerjaan", numOfJobs.get(position)));

        holder.itemView.setOnClickListener(view -> {
            Context ctx = view.getContext();
            Intent jobListIntent = new Intent(ctx, JobListActivity.class);
            jobListIntent.putExtra("ID_UNIT", unitId.get(position));
            jobListIntent.putExtra("USERNAME", MainActivity.username);
            ctx.startActivity(jobListIntent);
        });
    }

    @Override
    public int getItemCount() {
        return unitName.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.unitNameText)
        TextView unit_name;

        @BindView(R.id.numOfJobsText)
        TextView number_of_jobs;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
