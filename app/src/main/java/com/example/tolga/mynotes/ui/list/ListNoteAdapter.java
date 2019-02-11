package com.example.tolga.mynotes.ui.list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import com.example.tolga.mynotes.Constant;
import com.example.tolga.mynotes.R;
import com.example.tolga.mynotes.database.Note;
import com.example.tolga.mynotes.ui.detail.DetailNoteActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tolga on 28.11.2017.
 */

public class ListNoteAdapter extends RecyclerView.Adapter<ListNoteAdapter.ViewHolder>{

    private final OnNoteClickListener onNoteClickListener;
    protected Context context;
    private List<Note> notes;

    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    public ListNoteAdapter(Context prContext, OnNoteClickListener listener) {
        this.context = prContext;
        this.onNoteClickListener = listener;
    }

    public void setNotes (List<Note> notes) {
        // TODO: Use DiffUtil
        this.notes = notes;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(notes.get(position).getTitle()==null && notes.get(position).getValue() == null){
            holder.titleTextView.setText("No Title");
        }else{
            holder.titleTextView.setText(notes.get(position).getTitle());
            holder.valueTextView.setText(notes.get(position).getValue());
        }

    }

    @Override
    public int getItemCount() {
        return notes != null ? notes.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

        @BindView(R.id.note_title)
        TextView titleTextView;
        @BindView(R.id.note_value)
        TextView valueTextView;
        @BindView(R.id.twoLine)
        TwoLineListItem twoLine;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            twoLine.setOnClickListener(this);
            twoLine.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context,DetailNoteActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constant.ITEM_TITLE,notes.get(getAdapterPosition()).getTitle());
            intent.putExtra(Constant.ITEM_DESC,notes.get(getAdapterPosition()).getValue());
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            onNoteClickListener.onNoteClick(notes.get(getAdapterPosition()));
            return true;
        }
    }
}
