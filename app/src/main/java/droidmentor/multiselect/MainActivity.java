package droidmentor.multiselect;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import droidmentor.multiselect.Model.SampleModel;
import droidmentor.multiselect.Utils.AlertDialogHelper;
import droidmentor.multiselect.Utils.RecyclerItemClickListener;

public class MainActivity extends AppCompatActivity implements AlertDialogHelper.AlertDialogListener{

    ActionMode mActionMode;
    Menu context_menu;

    FloatingActionButton fab;
    RecyclerView recyclerView;
    MultiSelectAdapter multiSelectAdapter;
    boolean isMultiSelect = false;

    ArrayList<SampleModel> user_list = new ArrayList<>();
    ArrayList<SampleModel> multiselect_list = new ArrayList<>();

    AlertDialogHelper alertDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alertDialogHelper =new AlertDialogHelper(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        data_load();

        multiSelectAdapter = new MultiSelectAdapter(this,user_list,multiselect_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(multiSelectAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect)
                    multi_select(position);
                else
                    Toast.makeText(getApplicationContext(), "Details Page", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect) {
                    multiselect_list = new ArrayList<SampleModel>();
                    isMultiSelect = true;

                    if (mActionMode == null) {
                        mActionMode = startActionMode(mActionModeCallback);
                    }
                }

                multi_select(position);

            }
        }));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                alertDialogHelper.showAlertDialog("","Do you want to add new contact","LATER","ADD",2,false);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_common_activity, menu);
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
                onBackPressed();
                return true;
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(),"Settings Click",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_exit:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void data_load() {
        String name[] = {"Gokul", "Rajesh", "Ranjith", "Madhu", "Ameer", "Sonaal"};
        String posting[] = {"Manager", "HR", "Android Developer", "iOS Developer", "Team Leader", "Designer"};

        for (int i = 0; i < name.length; i++) {
            SampleModel mSample = new SampleModel(name[i], posting[i]);
            user_list.add(mSample);
        }
    }


    public void multi_select(int position) {
        if (mActionMode != null) {
            if (multiselect_list.contains(user_list.get(position)))
                multiselect_list.remove(user_list.get(position));
            else
                multiselect_list.add(user_list.get(position));

            if (multiselect_list.size() > 0)
                mActionMode.setTitle("" + multiselect_list.size());
            else
                mActionMode.setTitle("");

            refreshAdapter();

        }
    }


    public void refreshAdapter()
    {
        multiSelectAdapter.selected_usersList=multiselect_list;
        multiSelectAdapter.usersList=user_list;
        multiSelectAdapter.notifyDataSetChanged();
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_multi_select, menu);
            context_menu = menu;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    alertDialogHelper.showAlertDialog("","Delete Contact","DELETE","CANCEL",1,false);
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            multiselect_list = new ArrayList<SampleModel>();
            refreshAdapter();
        }
    };

    // AlertDialog Callback Functions

    @Override
    public void onPositiveClick(int from) {
        if(from==1)
        {
            if(multiselect_list.size()>0)
            {
                for(int i=0;i<multiselect_list.size();i++)
                    user_list.remove(multiselect_list.get(i));

                multiSelectAdapter.notifyDataSetChanged();

                if (mActionMode != null) {
                    mActionMode.finish();
                }
                Toast.makeText(getApplicationContext(), "Delete Click", Toast.LENGTH_SHORT).show();
            }
        }
        else if(from==2)
        {
            if (mActionMode != null) {
                mActionMode.finish();
            }

            SampleModel mSample = new SampleModel("Name"+user_list.size(),"Designation"+user_list.size());
            user_list.add(mSample);
            multiSelectAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onNegativeClick(int from) {

    }

    @Override
    public void onNeutralClick(int from) {

    }
}
