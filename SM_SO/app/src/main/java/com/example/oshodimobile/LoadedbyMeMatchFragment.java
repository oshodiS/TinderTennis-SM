package com.example.oshodimobile;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oshodimobile.Entities.MatchWithUser;
import com.example.oshodimobile.Entities.MatchWithUserOpponent;
import com.example.oshodimobile.RecyclerView.LoadedByMeAdapter;
import com.example.oshodimobile.RecyclerView.PrivateAdapter;
import com.example.oshodimobile.RecyclerView.onItemListener;
import com.example.oshodimobile.ViewModel.ListViewModel;

import java.util.ArrayList;
import java.util.List;


public class LoadedbyMeMatchFragment extends Fragment implements  onItemListener {
    RecyclerView rc;
    LoadedByMeAdapter adapter;
    private ListViewModel listViewModel;
    List<MatchWithUser> mUsers = new ArrayList<>();


    private static final String LOG = "Dashboard Fragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {
        return inflater.inflate(R.layout.user_private_dashboard, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final  Activity activity = getActivity();
        if(activity != null){

            final onItemListener listener = this;

            adapter = new LoadedByMeAdapter(activity,listener);

            Utilities.setRecyclerView(activity, view, adapter, R.id.recycler_view2,rc);
            Utilities.addFab(activity,view);

            listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
            listViewModel.getMatchWithUserbyUserId(Utilities.getCurrUser()).observe((LifecycleOwner) activity, new Observer<List<MatchWithUser>>() {
                @Override
                public void onChanged(List<MatchWithUser> matchWithUsers) {
                    mUsers=matchWithUsers;
                    adapter.setData(matchWithUsers);
                    adapter.notifyDataSetChanged();

                }
            });


        }else{

            Log.e(LOG,"Activity is null");
        }
    }

    @Override
    public void onItemClick(int pos) {
        Activity activity = getActivity();
        if(activity != null){
            Utilities.setMatch(mUsers.get(pos).getMatch().getId());
            Utilities.insertFragment((AppCompatActivity) activity, new DetailUserMatchFragment(),DetailMatchLoadedFragment.class.getSimpleName());

        }
    }

    //add data to the recyclerView




}
