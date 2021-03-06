package com.lyh.fieldofview.provider.related;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyh.fieldofview.R;
import com.lyh.fieldofview.model.ItemList;

import java.util.List;

import me.drakeet.multitype.ItemViewProvider;

/**
 * @author zsj
 */

public class CardViewProvider extends ItemViewProvider<Card, CardViewProvider.CardHolder> {

    private Activity context;

    public CardViewProvider(@NonNull Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    protected CardHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_horizontal_list, parent, false);
        return new CardHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull CardHolder holder, @NonNull Card card) {
        holder.setCards(card.item.data.itemList);
    }

    class CardHolder extends RecyclerView.ViewHolder {

        private RecyclerView cardList;
        private CardAdapter cardAdapter;
        private LinearSnapHelper snapHelper = new LinearSnapHelper();


        public CardHolder(View itemView) {
            super(itemView);
            cardList = (RecyclerView) itemView.findViewById(R.id.card_list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            cardList.setLayoutManager(layoutManager);
            cardAdapter = new CardAdapter(context);
            cardList.setAdapter(cardAdapter);
            snapHelper.attachToRecyclerView(cardList);
        }

        private void setCards(List<ItemList> items) {
            cardAdapter.setItems(items);
            cardAdapter.notifyDataSetChanged();
        }
    }


}
