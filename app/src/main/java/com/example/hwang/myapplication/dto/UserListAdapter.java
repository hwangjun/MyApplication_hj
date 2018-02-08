package com.example.hwang.myapplication.dto;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hwang.myapplication.R;
import com.example.hwang.myapplication.db.UserDB;

import java.util.List;

import static com.example.hwang.myapplication.R.layout.user;

/**
 * Created by hwang on 2018-02-03.
 */

public class UserListAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;
    private List<User> saveList;
    private UserDB db;
    private TextView id, userId, userName;
    private int position;
    private Activity parnent;
    private ListView listView;

    public UserListAdapter(Context context, List<User> userList, List<User> saveList, Activity parentActivity, ListView listView) {
        this.context = context;
        this.userList = userList;
        this.saveList = saveList;
        this.parnent = parentActivity;
        this.listView = listView;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        position = i;
        View v = View.inflate(context, R.layout.user, null);
        id = (TextView) v.findViewById(R.id.user_id);
        userId = (TextView) v.findViewById(R.id.userID);
        userName = (TextView) v.findViewById(R.id.userName);
        Button deleteBtn = (Button) v.findViewById(R.id.deleteBtn);

        id.setText(userList.get(i).getId() + "");
        userId.setText(userList.get(i).getUserId());
        userName.setText(userList.get(i).getUserName());

        v.setTag(userList.get(i).getUserId());



        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("확인필요!!!!","position : "+ (position));
                Log.d("확인필요!!!!","userID : "+ userList.get(position).getUserId());

                db = new UserDB(v.getContext());
                db.open();
                db.deleteUser(userList.get(position).getUserId());
                userList.remove(position);
                saveList.remove(position);
                notifyDataSetChanged();

            }
        });

        return v;
    }
}
