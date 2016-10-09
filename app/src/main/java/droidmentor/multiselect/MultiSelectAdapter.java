package droidmentor.multiselect;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import droidmentor.multiselect.Model.SampleModel;

/**
 * Created by Jaison on 08/10/16.
 */

public class MultiSelectAdapter extends RecyclerView.Adapter<MultiSelectAdapter.MyViewHolder> {

    public ArrayList<SampleModel> usersList=new ArrayList<>();
    public ArrayList<SampleModel> selected_usersList=new ArrayList<>();
    Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView posting, name;
        public LinearLayout ll_listitem;

        public MyViewHolder(View view) {
            super(view);
            posting = (TextView) view.findViewById(R.id.tv_posting);
            name = (TextView) view.findViewById(R.id.tv_user_name);
            ll_listitem=(LinearLayout)view.findViewById(R.id.ll_listitem);

        }
    }


    public MultiSelectAdapter(Context context,ArrayList<SampleModel> userList,ArrayList<SampleModel> selectedList) {
        this.mContext=context;
        this.usersList = userList;
        this.selected_usersList = selectedList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_userlist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SampleModel movie = usersList.get(position);
        holder.name.setText(movie.getName());
        holder.posting.setText(movie.getPosting());

        if(selected_usersList.contains(usersList.get(position)))
            holder.ll_listitem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_selected_state));
        else
            holder.ll_listitem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_normal_state));

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }
}

