package com.example.firebase;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.annotations.Nullable;

import androidx.annotation.NonNull;

public class CustomListview extends ArrayAdapter<String> {

    private String[] nombre;
    private String[] email;
    private String[] mensaje;
    private Activity context;
    Bitmap bitmap;

    public CustomListview(Activity context, String[] nombre, String[] email, String[] mensaje) {
        super(context, R.layout.layout, nombre);
        this.context = context;
        this.nombre = nombre;
        this.email = email;
        this.mensaje = mensaje;
    }

    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.layout, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();

        }

        viewHolder.tvw1.setText(nombre[position]);
        viewHolder.tvw2.setText(email[position]);
        viewHolder.tvw3.setText(mensaje[position]);

        return r;
    }

    class ViewHolder {

        TextView tvw1;
        TextView tvw2;
        TextView tvw3;
        ImageView ivw;

        ViewHolder(View v) {
            tvw1 = (TextView) v.findViewById(R.id.textnombre);
            tvw2 = (TextView) v.findViewById(R.id.textemail);
            tvw3 = (TextView) v.findViewById(R.id.textmensaje);
            ivw = (ImageView) v.findViewById(R.id.imalista);
        }

    }


}
