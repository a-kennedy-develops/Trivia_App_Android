package com.example.alexk.group3_hw02;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AlexK on 3/7/2018.
 */

public class QuestionAdapter extends ArrayAdapter<String>{

    public Context context;

    public QuestionAdapter(@NonNull Context context, int resource, @NonNull List<String> strings) {
        super(context, resource, strings);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String choices = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.question_option_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.setTextView_option((TextView) convertView.findViewById(R.id.textView_questionOption));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.getTextView_option().setText(choices);

        return convertView;
    }

    private static class ViewHolder {
        TextView textView_option;

        public TextView getTextView_option() {
            return textView_option;
        }

        public void setTextView_option(TextView textView_option) {
            this.textView_option = textView_option;
        }
    }
}
