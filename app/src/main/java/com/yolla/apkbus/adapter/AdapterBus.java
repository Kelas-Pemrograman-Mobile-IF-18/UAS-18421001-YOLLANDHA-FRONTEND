package com.yolla.apkbus.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yolla.apkbus.R;
import com.yolla.apkbus.model.ModelBus;
import com.yolla.apkbus.server.BaseURL;

import java.util.List;

public class AdapterBus extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelBus> item;

    public AdapterBus(Activity activity, List<ModelBus> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_bus, null);


        TextView namaBus = (TextView) convertView.findViewById(R.id.txtnamaBus);
        TextView jurusanBus = (TextView) convertView.findViewById(R.id.txtjurusanBus);
        TextView jamOperasional = (TextView) convertView.findViewById(R.id.txtjamOperasional);
        TextView hargaTiket = (TextView) convertView.findViewById(R.id.txthargaTiket);

        ImageView gambarBus = (ImageView) convertView.findViewById(R.id.gambarBus);

        namaBus.setText(item.get(position).getNamaBus());
        jurusanBus.setText(item.get(position).getJurusanBus());
        jamOperasional.setText(item.get(position).getJamOperasional());
        hargaTiket.setText("Rp." + item.get(position).getHargaTiket());
        Picasso.get().load(BaseURL.baseURL + "gambar/" + item.get(position).getGambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambarBus);
        return convertView;
    }
}
