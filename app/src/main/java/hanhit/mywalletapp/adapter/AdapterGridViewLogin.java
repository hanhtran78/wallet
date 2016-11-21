package hanhit.mywalletapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import hanhit.mywalletapp.R;

/**
 * Created by hanh.tran on 6/16/2016.
 */
public class AdapterGridViewLogin extends BaseAdapter {

    private Activity mContext;
    private String[] strView;
    private View.OnClickListener listener;

    public AdapterGridViewLogin(Activity mContext, String[] strings, View.OnClickListener listener) {
        super();
        this.mContext = mContext;
        this.strView = strings;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_gridview_passcode, null);
        }
        Button button = (Button) view.findViewById(R.id.button);
        button.setText(strView[position]);
        button.setOnClickListener(listener);
        if (position == 9 || position == 11) {
            button.setVisibility(Button.GONE);
        }

        return view;
    }


    @Override
    public int getCount() {
        return strView.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
