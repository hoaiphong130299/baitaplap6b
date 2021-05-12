package com.example.lab6_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class AddressAdapter extends BaseAdapter {
    private List<Address> addressList;
    private Context context;
    private DatabaseHandler databaseHandler;

    public AddressAdapter(List<Address> addressList, Context context, DatabaseHandler databaseHandler) {
        this.addressList = addressList;
        this.context = context;
        this.databaseHandler = databaseHandler;
    }

    @Override
    public int getCount() {
        return addressList.size();
    }

    @Override
    public Object getItem(int position) {
        return addressList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return addressList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Address address = addressList.get(position);

        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        TextView tvName = view.findViewById(R.id.tvName);

        ImageButton btnEdit = view.findViewById(R.id.imgbtnEdit);
        ImageButton btnDelete = view.findViewById(R.id.imgbtnDelete);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler.updateAddress(address.getId()," lấy dữ liệu ");
                notifyDataSetChanged();

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler.deleteAddress(address.getId());
                notifyDataSetChanged();
            }
        });
        tvName.setText(position + 1 + ". " + address.getName());
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        addressList = databaseHandler.getAllAddress();
        super.notifyDataSetChanged();
    }
}
