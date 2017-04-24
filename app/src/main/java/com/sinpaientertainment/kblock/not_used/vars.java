package com.sinpaientertainment.kblock.not_used;
/*
import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.sinpaientertainment.heapstack.cpus.CPU;
import com.sinpaientertainment.heapstack.managers.ChatArrayAdapter;
import com.sinpaientertainment.heapstack.managers.ChatMessage;
import com.sinpaientertainment.heapstack.managers.DrawerArrayAdapter;
import com.sinpaientertainment.heapstack.managers.DrawerEntry;

import de.hdodenhof.circleimageview.CircleImageView;

public class vars {
    public static class drawer {
        private static DrawerArrayAdapter adp;
        private static ListView list;
        public static Object[] init(CPU cpu) {
            try
            {
                list = (ListView) cpu.findViewById(R.id.drawer_options);
                adp = new DrawerArrayAdapter(cpu.getApplicationContext(), R.layout.drawer_list_item);
                list.setAdapter(adp);
                list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                list.setAdapter(adp);
                adp.registerDataSetObserver(new DataSetObserver() {
                    public void OnChanged(){
                        super.onChanged();
                        list.setSelection(adp.getCount() -1);
                    }
                });
                adp.clear();
                adp.add(new DrawerEntry(null, "Home"));
                adp.add(new DrawerEntry(null, "Messaging"));
                adp.add(new DrawerEntry(null, "Maps"));
                adp.add(new DrawerEntry(null, "Tunes"));
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:

                                break;
                            default:
                                break;
                        }
                    }
                });
                return new Object[]{ "pass", true };
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return new Object[]{ "[Chat System Initialization]: " + e.getCause(), false};
            }
        }
    }
}
*/
