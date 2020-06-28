package com.example.techfellow_todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adapter is responsible for taking data from a position and putting it at a specific view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnLongClickListerner{
        void onItemLongClicked(int position);

    }
    public interface OnClickListerner{
        void onItemClicked(int position);
    }


    List<String> items;
    OnLongClickListerner longClickListerner;
    OnClickListerner clickListerner;

    public ItemsAdapter(List<String> items, OnLongClickListerner longClickListerner, OnClickListerner clickListerner) {
        this.items= items;
        this.longClickListerner=longClickListerner;
        this.clickListerner= clickListerner;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use layout inflater to inflate a view
        View todoView=LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        // wrap it inside a view holder and return it
        return new ViewHolder(todoView);

    }
// binds data to a view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // grab item at position
        String item= items.get(position);
        //bind item into specified view holder
        holder.bind(item);


    }
    // counts items in lis
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Container to provider easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem= itemView.findViewById(android.R.id.text1);
        }

        //update view inside viewholder with data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListerner.onItemClicked(getAdapterPosition());


                }
            });
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    // Getting position that is long pressed
                    longClickListerner.onItemLongClicked(getAdapterPosition());
                    return true;


                }
            });

        }
    }

}
