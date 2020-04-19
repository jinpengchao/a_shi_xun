package com.vhome.chat.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.vhome.chat.DemoHelper;
import com.vhome.chat.R;
import com.vhome.chat.conference.ConferenceActivity;
import com.vhome.vhome.MainActivity;
import com.vhome.vhome.children.ChildrenMain;
import com.vhome.vhome.parents.ParentMain;
import com.hyphenate.util.EasyUtils;

/**
 * 开屏页
 *
 */
public class SplashActivity extends BaseActivity {

	private static final int sleepTime = 2000;
    private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.em_activity_splash);
		super.onCreate(arg0);

		DemoHelper.getInstance().initHandler(this.getMainLooper());

		RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.splash_root);
		AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(1500);
		rootLayout.startAnimation(animation);
	}

	@Override
	protected void onStart() {
		super.onStart();
		new Thread(new Runnable() {
			public void run() {
				if (DemoHelper.getInstance().isLoggedIn()) {
					// auto login mode, make sure all group and conversation is loaed before enter the main screen
					long start = System.currentTimeMillis();
					EMClient.getInstance().chatManager().loadAllConversations();
					EMClient.getInstance().groupManager().loadAllGroups();
					long costTime = System.currentTimeMillis() - start;
					//wait
					if (sleepTime - costTime > 0) {
						try {
							Thread.sleep(sleepTime - costTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					String topActivityName = EasyUtils.getTopActivityName(EMClient.getInstance().getContext());
					if (topActivityName != null && (topActivityName.equals(VideoCallActivity.class.getName()) || topActivityName.equals(VoiceCallActivity.class.getName()) || topActivityName.equals(ConferenceActivity.class.getName()))) {
						// nop
						// avoid main screen overlap Calling Activity
					} else {
                        sp = getSharedPreferences("user", MODE_PRIVATE);

                        if(sp.getString("phone","")!=""){
                            if(sp.getInt("type",0)==0){
                                startActivity(new Intent(SplashActivity.this, ParentMain.class));
                            }else if(sp.getInt("type",0)==1){
                                startActivity(new Intent(SplashActivity.this, ChildrenMain.class));
                            }
                        }
					}
					finish();
				}else {
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
					}
					startActivity(new Intent(SplashActivity.this, MainActivity.class));
					finish();
				}
			}
		}).start();

	}
}
