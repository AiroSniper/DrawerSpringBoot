package com.example.market;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.market.beans.Machine;
import com.example.market.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.market.databinding.FragmentMachinesBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> implements Filterable {

    private  List<Machine> machines;
    private  List<Machine> machinesAll;


    public MyItemRecyclerViewAdapter(List<Machine> items) {
        machines = items;
        machinesAll = new ArrayList<>(machines);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentMachinesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.machine = machines.get(position);
        holder.ref.setText(machines.get(position).getRef());
        holder.dateAchat.setText(machines.get(position).getDateAchat()+"");
        holder.marque.setText(machines.get(position).getMaqrque());
        holder.prix.setText(machines.get(position).getPrix()+"DH");
    }

    @Override
    public int getItemCount() {
        return machines.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Machine> filteredList = new ArrayList<Machine>();
            if(charSequence.toString().isEmpty()){
                filteredList.addAll(machinesAll);
            }
            else {
                for(Machine machine : machinesAll){
                    if(machine.getMaqrque().toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(Locale.ROOT))){
                        filteredList.add(machine);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            machines.clear();
            machines.addAll((Collection<? extends Machine>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView ref;
        public final TextView dateAchat;
        public final TextView prix;
        public final TextView marque;
        public Machine machine;

        public ViewHolder(FragmentMachinesBinding binding) {
            super(binding.getRoot());
            ref = binding.ref;
            dateAchat = binding.dateAchat;
            prix = binding.prix;
            marque = binding.marque;

        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}