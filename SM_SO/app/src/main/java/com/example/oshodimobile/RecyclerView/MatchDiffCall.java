package com.example.oshodimobile.RecyclerView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.oshodimobile.Entities.Match;
import com.example.oshodimobile.Entities.MatchWithUser;
import com.example.oshodimobile.Entities.MatchWithUserOpponent;

import java.util.List;

public class MatchDiffCall extends DiffUtil.Callback {

    private final List<MatchWithUserOpponent> oldCardList;
    private final List<MatchWithUserOpponent> newCardList;


    public MatchDiffCall(List<MatchWithUserOpponent> oldCardList, List<MatchWithUserOpponent> newCardList) {
        this.oldCardList = oldCardList;
        this.newCardList = newCardList;
    }

    @Override
    public int getOldListSize() {
        return oldCardList.size();
    }

    @Override
    public int getNewListSize() {
        return newCardList.size();
    }

    /**
     *
     * @param oldItemPosition position of the old item
     * @param newItemPosition position of the new item
     * @return true if the two items are the same
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldCardList.get(oldItemPosition) == newCardList.get(newItemPosition);
    }

    /**
     *
     * @param oldItemPosition position of the old item
     * @param newItemPosition position of the new item
     * @return true if the two item have the same content
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Match oldItem = oldCardList.get(oldItemPosition).getMatchList().get(0);
        final Match newItem = newCardList.get(newItemPosition).getMatchList().get(0);

        return oldItem.getUserCreatorId()== newItem.getUserCreatorId() &&
                oldItem.getCourt().equals(newItem.getCourt()) &&
                oldItem.getDate().equals(newItem.getDate()) &&
                oldItem.getHour().equals(newItem.getHour()) &&
                oldItem.getPlace().equals(newItem.getPlace()) &&
                oldItem.getUserOpponentId()==(newItem.getUserOpponentId()) &&
                oldItem.getLongDesc().equals(newItem.getLongDesc()) &&
                oldItem.getStatus().equals(newItem.getStatus()) &&
                oldItem.getCost() == newItem.getCost();

    }

    /**
     * When areItemsTheSame(int, int) returns true for two items and
     * areContentsTheSame(int, int) returns false for the two items,
     * this method is called to get a payload about the change.
     * @param oldItemPosition position of the old item
     * @param newItemPosition position of the new item
     * @return an Object (it could be a bundle) that contains the changes (the only change in this case
     * id the content of the item to handle in the Adapter in the overridden method
     * onBindViewHolder(CryptoViewHolder holder, int position, List<Object> payloads)).
     *
     * It returns null by default.
     */
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
