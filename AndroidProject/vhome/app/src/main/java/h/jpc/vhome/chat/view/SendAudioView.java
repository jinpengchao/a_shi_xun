package h.jpc.vhome.chat.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

import h.jpc.vhome.R;
import h.jpc.vhome.chat.adapter.AudioAdapter;


public class SendAudioView extends LinearLayout {

    private ListView mListView;

    public SendAudioView(Context context) {
        super(context);
    }

    public SendAudioView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initModule() {
        mListView = (ListView) findViewById(R.id.audio_list_view);
    }

    public void setAdapter(AudioAdapter adapter) {
        mListView.setAdapter(adapter);
    }
}
