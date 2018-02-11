package com.example.hwang.myapplication;

import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hwang.myapplication.db.UserDB;
import com.example.hwang.myapplication.dto.User;
import com.example.hwang.myapplication.dto.UserListAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private UserDB db;
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";

    private ListView listView;
    private UserListAdapter adapter;
    private List<User> userList;
    private List<User> saveList;
    private User user;
    private EditText search;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        search = (EditText) findViewById(R.id.search);

        db = new UserDB(this);
        db.open();

        listView = (ListView) findViewById(R.id.list_view);

        /*
        ListView 헤더부분
        final View header = getLayoutInflater().inflate(R.layout.listview_header, null, false) ;
        listView.addHeaderView(header);
        */

        doWhileCursorToArray();

        adapter = new UserListAdapter(getApplicationContext(), userList, saveList, this, listView);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        setListViewHeightBasedOnChildren(listView);






        //검색 이벤트
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Log.i("검색값 : ", s.toString());

                searchUser(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        //네비게이션 메뉴 이벤트
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_alluser:  //전체회원보기
                        //Toast.makeText(AdminActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();

                     /*   doWhileCursorToArray();


                        for (User user : userList) {
                            Log.i("전체회원조회", "_id = " + user.getId());
                            Log.i("전체회원조회", "id = " + user.getUserId());
                            Log.i("전체회원조회", "name = " + user.getUserName());
                        }


                        adapter = new UserListAdapter(getApplicationContext(), userList, saveList,);
                        listView.setAdapter(adapter);*/

                        break;

                    case R.id.navigation_item_images:
                        Toast.makeText(AdminActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_sub_menu_item01:
                        Toast.makeText(AdminActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_sub_menu_item02:
                        Toast.makeText(AdminActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        break;

                }

                return true;
            }
        });

        //onItemClickListener를 추가
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(AdminActivity.this, userList.get(position).getUserName(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void searchUser(String search) {
        userList.clear();

        for (int i =0; i< saveList.size(); i++) {
            if(saveList.get(i).getUserId().contains(search)) {
                userList.add(saveList.get(i));
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void doWhileCursorToArray() {
        Cursor cursor = db.selectAllUsers();
        if (cursor != null) {
            userList = new ArrayList<>();
            saveList = new ArrayList<>();

            int id = 0;
            while (cursor.moveToNext()) {
                id += 1;
                String userId = cursor.getString(cursor.getColumnIndex("id"));
                String userName = cursor.getString(cursor.getColumnIndex("name"));
                String userPwd = cursor.getString(cursor.getColumnIndex("pwd"));
                user = new User(id, userId, userPwd, userName);
                //user = new User(id, userId, userPwd, userName);
                userList.add(user);
                saveList.add(user);
            }
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }



}
