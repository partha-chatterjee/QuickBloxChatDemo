package partha.qbchatdemo.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import partha.qbchatdemo.R;
import partha.qbchatdemo.chat.model.ChatData;


public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //private static final int MY_VIEW = 0;
    //private static final int OPPONENT_VIEW = 1;
    //private static String TAG = MyRecyclerAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<ChatData> arrayList;

    public MyRecyclerAdapter(Context mContext, ArrayList<ChatData> messageArrayList) {
        this.mContext = mContext;
        this.arrayList = messageArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        //if (viewType == MY_VIEW) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_my_view, parent, false);
/*        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_opponent_view, parent, false);
        }*/
        return new ViewHolder(itemView);
    }

/*    @Override
    public int getItemViewType(int position) {
        ChatData chatData = arrayList.get(position);
        if (chatData.isMe) {
            return MY_VIEW;
        } else {
            return OPPONENT_VIEW;
        }
    }*/



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ChatData chatData = arrayList.get(position);
        ((ViewHolder) holder).message.setText(chatData.message);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView message;

        public ViewHolder(View view) {
            super(view);
            message = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}

