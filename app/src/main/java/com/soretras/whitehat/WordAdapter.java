package com.soretras.whitehat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

    private ArrayList<Word> words = new ArrayList<>() ;
    private WordViewModel wordViewModel ;
    private Context context ;

    public void setWordViewModel(WordViewModel wordViewModel) {
        this.wordViewModel = wordViewModel;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WordViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.firstWord.setText(words.get(position).getFirstWord());
        holder.secondWord.setText(words.get(position).getSecondWord());

        if(position % 2 == 0){
            holder.itemBackground.setBackground(new ColorDrawable(Color.WHITE));
        }
        holder.deleteWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wordViewModel.deleteWord(words.get(position),context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public void setWords(List<Word> words){
        this.words = (ArrayList<Word>) words ;
        notifyDataSetChanged();
    }

    public static class WordViewHolder extends RecyclerView.ViewHolder {
        private TextView firstWord ;
        private TextView secondWord ;
        private View itemBackground ;
        private ImageView deleteWord ;
        public WordViewHolder(@NonNull View itemView) {
            super(itemView);

            firstWord = itemView.findViewById(R.id.firstWord);
            secondWord = itemView.findViewById(R.id.secondWord);
            itemBackground = itemView.findViewById(R.id.itemBackground);
            deleteWord = itemView.findViewById(R.id.delete);

        }
    }
}
