package com.telran.a20_01_20_cw;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.telran.a20_01_20_cw.dto.ContactDto;

import java.util.List;

public class ContactAdapter extends BaseAdapter {
    List<ContactDto> list;

    public ContactAdapter(List<ContactDto> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_contact,parent,false);
        }

        ContactDto contact = list.get(position);
        TextView nameTxt = convertView.findViewById(R.id.nameTxt);
        TextView phoneTxt = convertView.findViewById(R.id.phoneTxt);
        nameTxt.setText(contact.getName() + " " + contact.getLastName());
        phoneTxt.setText(contact.getPhone());
        return convertView;
    }
}
