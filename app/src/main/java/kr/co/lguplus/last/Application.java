package kr.co.lguplus.last;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.lguplus.last.talk.Message;

public final class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "DEFAULT", "1234.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "1234.ttf");
    }

	/**
     * AwesomeAdapter is a Custom class to implement custom row in ListView
     *
     * @author Adil Soomro
     *
     */
    public static class AwesomeAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<Message> mMessages;



        public AwesomeAdapter(Context context, ArrayList<Message> messages) {
            super();
            this.mContext = context;
            this.mMessages = messages;
        }
        @Override
        public int getCount() {
            return mMessages.size();
        }
        @Override
        public Object getItem(int position) {
            return mMessages.get(position);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Message message = (Message) this.getItem(position);

            ViewHolder holder;
            if(convertView == null)
            {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.sms_row, parent, false);
                holder.message = (TextView) convertView.findViewById(R.id.message_text);
                convertView.setTag(holder);
            }
            else
                holder = (ViewHolder) convertView.getTag();

            holder.message.setText(message.getMessage());

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.message.getLayoutParams();
            //check if it is a status message then remove background, and change text color.
            if(message.isStatusMessage())
            {
                holder.message.setBackgroundDrawable(null);
                lp.gravity = Gravity.LEFT;
                holder.message.setTextColor(R.color.textFieldColor);
            }
            else
            {
                //Check whether message is mine to show green background and align to right
                if(message.isMine())
                {
                    holder.message.setBackgroundResource(R.drawable.speech_bubble_green);
                    lp.gravity = Gravity.RIGHT;
                }
                //If not mine then it is from sender to show orange background and align to left
                else
                {
                    holder.message.setBackgroundResource(R.drawable.speech_bubble_orange);
                    lp.gravity = Gravity.LEFT;
                }
                holder.message.setLayoutParams(lp);
                holder.message.setTextColor(R.color.textColor);
            }
            return convertView;
        }
        private static class ViewHolder
        {
            TextView message;
        }

        @Override
        public long getItemId(int position) {
            //Unimplemented, because we aren't using Sqlite.
            return position;
        }

    }
}