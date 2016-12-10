package com.littlegiants.android.edutree;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> implements Filterable{

    private List<NotesDataModel> dataModelArrayList;
    private List<NotesDataModel> mFilteredList;

    public RecyclerAdapter(List<NotesDataModel> dataModelArrayList) {
        this.dataModelArrayList = dataModelArrayList;
        this.mFilteredList = dataModelArrayList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recnotes,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        NotesDataModel dataModel=dataModelArrayList.get(position);
        holder.name.setText(dataModel.getTittle());
        holder.city.setText(dataModel.getNotes());
        //holder.country.setText(dataModel.getDate());
        holder.usern.setText("By "+username);
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name,city,country,usern;

        public MyHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.from);
            city = itemView.findViewById(R.id.txt_secondary);
            //country = itemView.findViewById(R.id.timestamp);
            usern = itemView.findViewById(R.id.txt_primary);
        }
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = dataModelArrayList;

                } else {

                    List<NotesDataModel> filteredList = new ArrayList<>();

                    for (NotesDataModel notesDataModel : dataModelArrayList) {

                        if (notesDataModel.getTittle().toLowerCase().contains(charString) || notesDataModel.getTittle().toLowerCase().contains(charString) || notesDataModel.getTittle().toLowerCase().contains(charString)) {

                            filteredList.add(notesDataModel);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (List<NotesDataModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
