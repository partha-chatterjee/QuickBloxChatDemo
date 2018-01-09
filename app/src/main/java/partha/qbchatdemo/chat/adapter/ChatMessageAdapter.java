package partha.qbchatdemo.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quickblox.chat.model.QBAttachment;
import com.quickblox.chat.model.QBChatMessage;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import partha.qbchatdemo.R;
import partha.qbchatdemo.chat.interfaces.OnImageDownloadedListener;
import partha.qbchatdemo.chat.utils.Constant;
import partha.qbchatdemo.chat.utils.Utils;

/**
 * Created by Partha Chatterjee on 18-08-2017.
 */

/*
*
* call by
* ImageDownloaderQueue.getInstance().initGoogleApiClient().setArrayList(arrayListDrive).setOnImageDownloadSuccessListener(this).startDownloading();
*
* receive
*
* @Override
    public void onImageDownloadSuccess(String driveId) {
        int position = driveList.indexOf(driveId);
        adapter.notifyItemChanged(position);
    }
*
* */

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {

    private List<QBChatMessage> mDataArray;
    private int myQbId;
    private Context mContext;
    private View itemView;
    private OnImageDownloadedListener onImageDownloadedListener;

    /*public ChatMessageAdapter(List<ChatData> mDataArray, Context mContext){
        this.mDataArray = mDataArray;
        this.mContext = mContext;
    }*/
    public ChatMessageAdapter(List<QBChatMessage> mDataArray, Context mContext, int myQbId) {
        this.mDataArray = mDataArray;
        this.mContext = mContext;
        this.myQbId = myQbId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        ViewHolder vh = new ChatMessageAdapter.ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        /*String id = message.getId();
                            String dialogID = message.getDialogId();
                            String msg = message.getBody();
                            int senderId = message.getSenderId();
                            int receiverId = message.getRecipientId();
                            int isRead = Integer.parseInt(message.getProperties().get("read"));    // 1/0
                            boolean hasFile = Boolean.parseBoolean(message.getProperties().get("has_file"));    // no*/

        Boolean isMe = false;
        if (mDataArray.get(position).getSenderId() == myQbId) {
            isMe = true;
        } else {
            isMe = false;
        }

        // First Item
        if (holder.getAdapterPosition() == 0) {
            holder.rl_new_date.setVisibility(View.VISIBLE);
            holder.txt_new_date.setText(convertDateFromMillis(mDataArray.get(holder.getAdapterPosition()).getDateSent() * 1000));
        } else {
            long date1 = mDataArray.get(holder.getAdapterPosition() - 1).getDateSent() * 1000;
            long date2 = mDataArray.get(holder.getAdapterPosition()).getDateSent() * 1000;
            if (isDateChanged(date1, date2)) {
                holder.rl_new_date.setVisibility(View.VISIBLE);
                holder.txt_new_date.setText(convertDateFromMillis(mDataArray.get(holder.getAdapterPosition()).getDateSent() * 1000));
            } else {
                holder.rl_new_date.setVisibility(View.GONE);
            }
        }

        if (isMe) {
            holder.rl_other_user.setVisibility(View.GONE);
            holder.rl_me.setVisibility(View.VISIBLE);
            boolean hasFile = mDataArray.get(position).getAttachments().size() > 0;

            if (hasFile) {
                holder.img_other_me_file.setVisibility(View.VISIBLE);
                holder.txt_me_msg.setVisibility(View.GONE);
                ArrayList<QBAttachment> fileList = new ArrayList<>(mDataArray.get(holder.getAdapterPosition()).getAttachments());
                Utils utils = new Utils(mContext);
                /*QBAttachment attachment = fileList.iterator().next();
                String url = attachment.getUrl();
                if (url!=null){
                    Log.d("partha_url ", url);
                }
                Utils utils = new Utils(mContext);
                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), mContext.getResources().getString(R.string.app_name)+"/sent");
                dir.mkdirs();
                File file = new File(dir, fileList.get(0).getId()+".jpg");
                if (file.exists()) {*/
//                    holder.img_other_me_file.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
//                utils.setSquareImage(holder.img_other_me_file, fileList.get(0).getUrl());

                Picasso.with(mContext)
                        .load(fileList.get(0).getUrl())
                        .placeholder(R.drawable.no_image_placeholder_gray)
                        .resize(250,250)
                        .into(holder.img_other_me_file);

            } else {
                holder.img_other_me_file.setVisibility(View.GONE);
                holder.txt_other_msg.setVisibility(View.VISIBLE);
                holder.txt_me_msg.setText(mDataArray.get(position).getBody());
            }

            holder.txt_me_time.setText(convertTimeFromMillis(mDataArray.get(position).getDateSent() * 1000));
        } else {
            holder.rl_me.setVisibility(View.GONE);
            holder.rl_other_user.setVisibility(View.VISIBLE);
            holder.img_other_me_file.setVisibility(View.GONE);
            holder.txt_other_msg.setVisibility(View.VISIBLE);
            boolean hasFile = mDataArray.get(position).getAttachments().size() > 0;

            if (hasFile) {
                holder.img_other_user_file.setVisibility(View.VISIBLE);
                holder.txt_other_msg.setVisibility(View.GONE);
                ArrayList<QBAttachment> fileList = new ArrayList<>(mDataArray.get(holder.getAdapterPosition()).getAttachments());
                Utils utils = new Utils(mContext);
                /*QBAttachment attachment = fileList.iterator().next();
                String url = attachment.getUrl();
                if (url != null) {
                    Log.d("partha_url ", url);
                }
                Utils utils = new Utils(mContext);
                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), mContext.getResources().getString(R.string.app_name) + "/received");
                dir.mkdirs();
                File file = new File(dir, fileList.get(0).getId() + ".jpg");
                if (file.exists()) {*/
//                    utils.setSquareImage(holder.img_other_me_file, fileList.get(0).getUrl());

                Picasso.with(mContext)
                        .load(fileList.get(0).getUrl())
                        .placeholder(R.drawable.no_image_placeholder_gray)
                        .resize(250,250)
                        .into(holder.img_other_me_file);
            } else {
                holder.img_other_user_file.setVisibility(View.GONE);
                holder.txt_other_msg.setVisibility(View.VISIBLE);
                holder.txt_other_msg.setText(mDataArray.get(position).getBody());
            }

            holder.txt_other_time.setText(convertTimeFromMillis(mDataArray.get(position).getDateSent() * 1000));

        }

        holder.img_other_me_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onImageDownloadedListener.inImageDownloaded(true, holder.getAdapterPosition(), mDataArray.get(holder.getAdapterPosition()));
//                Toast.makeText(mContext, "Image will be downloaded soon.", Toast.LENGTH_SHORT).show();
            }
        });
        holder.img_other_user_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onImageDownloadedListener.inImageDownloaded(false, holder.getAdapterPosition(), mDataArray.get(holder.getAdapterPosition()));
            }
        });
    }

    public void setOnImageDownloadedListener(OnImageDownloadedListener listener) {
        this.onImageDownloadedListener = listener;
    }

    @Override
    public int getItemCount() {
        if (mDataArray != null) {
            return mDataArray.size();
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
//        Log.d("position : ","get item id "+position);
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rl_other_user)
        RelativeLayout rl_other_user;
        @Bind(R.id.rl_me)
        RelativeLayout rl_me;
        @Bind(R.id.img_other_user)
        ImageView img_other_user;
        @Bind(R.id.img_me_user)
        ImageView img_me_user;
        @Bind(R.id.txt_other_time)
        TextView txt_other_time;
        @Bind(R.id.txt_me_time)
        TextView txt_me_time;
        @Bind(R.id.txt_other_msg)
        TextView txt_other_msg;
        @Bind(R.id.txt_me_msg)
        TextView txt_me_msg;
        @Bind(R.id.img_other_user_file)
        ImageView img_other_user_file;
        @Bind(R.id.img_other_me_file)
        ImageView img_other_me_file;
        @Bind(R.id.rl_new_date)
        RelativeLayout rl_new_date;
        @Bind(R.id.txt_new_date)
        TextView txt_new_date;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private String convertTimeFromMillis(long millis) {

        //long val = 1346524199000l;

        Date date = new Date(millis);
        SimpleDateFormat df2 = new SimpleDateFormat(/*Constant.app_display_date_format + ", " +*/ Constant.app_display_time_format);
        String dateText = df2.format(date);
        System.out.println(dateText);
        return dateText;
    }

    private String convertDateFromMillis(long millis) {

        //long val = 1346524199000l;

        Date date = new Date(millis);
        SimpleDateFormat df2 = new SimpleDateFormat(Constant.app_display_date_format);
        String dateText = df2.format(date);
        System.out.println(dateText);
        return dateText;
    }

    private boolean isDateChanged(long date1, long date2) {
        Boolean isNewDate = false;
        SimpleDateFormat df = new SimpleDateFormat(Constant.app_display_date_format);
        String dt1 = df.format(date1);
        String dt2 = df.format(date2);
        if (dt1.equalsIgnoreCase(dt2)) {
            isNewDate = false;
        } else {
            isNewDate = true;
        }
        return isNewDate;
    }

    public static String getTime(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(new Date(milliseconds));
    }

    public static String getDate(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd", Locale.getDefault());
        return dateFormat.format(new Date(milliseconds));
    }

    public static long getDateAsHeaderId(long milliseconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        return Long.parseLong(dateFormat.format(new Date(milliseconds)));
    }
}
