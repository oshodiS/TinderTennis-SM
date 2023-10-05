package com.example.oshodimobile.RecyclerView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;


import com.example.oshodimobile.Entities.Match;
import com.example.oshodimobile.Entities.MatchWithUser;

import java.util.List;


public class MatchUserDiffCall extends DiffUtil.Callback {

    private final List<MatchWithUser> oldCardList;
    private final List<MatchWithUser> newCardList;


    public MatchUserDiffCall(List<MatchWithUser> oldCardList, List<MatchWithUser> newCardList) {
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
        final MatchWithUser oldItem = oldCardList.get(oldItemPosition);
        final MatchWithUser newItem = newCardList.get(newItemPosition);

        return  oldItem.getMatchList().get(0).getUserCreatorId()== newItem.getMatchList().get(0).getUserCreatorId() &&
                oldItem.getUserList().get(0).getName()== newItem.getUserList().get(0).getName() &&
                oldItem.getUserList().get(0).getUsername()== newItem.getUserList().get(0).getUsername() &&
                oldItem.getUserList().get(0).getLvl()== newItem.getUserList().get(0).getLvl() &&
                oldItem.getUserList().get(0).getLast_name()== newItem.getUserList().get(0).getLast_name() &&
                oldItem.getMatchList().get(0).getCourt().equals(newItem.getMatchList().get(0).getCourt()) &&
                oldItem.getMatchList().get(0).getDate().equals(newItem.getMatchList().get(0).getDate()) &&
                oldItem.getMatchList().get(0).getHour().equals(newItem.getMatchList().get(0).getHour()) &&
                oldItem.getMatchList().get(0).getImgPath().equals(newItem.getMatchList().get(0).getImgPath()) &&
                oldItem.getMatchList().get(0).getPlace().equals(newItem.getMatchList().get(0).getPlace()) &&
                oldItem.getMatchList().get(0).getUserOpponentId()==(newItem.getMatchList().get(0).getUserOpponentId()) &&
                oldItem.getMatchList().get(0).getLongDesc().equals(newItem.getMatchList().get(0).getLongDesc()) &&
                oldItem.getMatchList().get(0).getStatus().equals(newItem.getMatchList().get(0).getStatus()) &&
                oldItem.getMatchList().get(0).getCost() == newItem.getMatchList().get(0).getCost();

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
