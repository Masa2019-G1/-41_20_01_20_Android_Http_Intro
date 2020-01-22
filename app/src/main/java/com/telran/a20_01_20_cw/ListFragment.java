package com.telran.a20_01_20_cw;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.telran.a20_01_20_cw.dto.ContactDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    AlertDialog progressDialog;
    ListView contactList;
    FloatingActionButton addBtn;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        progressDialog = new AlertDialog.Builder(requireContext())
                .setView(R.layout.progress_view)
                .setCancelable(false)
                .create();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_list, container, false);
        contactList = view.findViewById(R.id.contactList);
        addBtn = view.findViewById(R.id.addBtn);
        return view;
    }

    private void showProgress(boolean isShow){
        if(isShow){
            progressDialog.show();
        }else{
            progressDialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        new LoadContactsTask().execute();
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logoutItem){
            StoreProvider.getInstance().removeToken();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.root,new LoginFragment())
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    class LoadContactsTask extends AsyncTask<Void,Void, List<ContactDto>>{

        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected List<ContactDto> doInBackground(Void... voids) {
            List<ContactDto> list = new ArrayList<>();
            String token = StoreProvider.getInstance().getToken();
            try {
                list = HttpProvider.getInstance().getAllContacts(token);
            } catch (IOException e) {
                e.printStackTrace();
            }catch (RuntimeException e){
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<ContactDto> list) {
            showProgress(false);
            ContactAdapter adapter = new ContactAdapter(list);
            contactList.setAdapter(adapter);
        }
    }
}
