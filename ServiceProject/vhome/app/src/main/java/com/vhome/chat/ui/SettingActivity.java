/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vhome.chat.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.vhome.chat.Constant;
import com.vhome.chat.DemoHelper;
import com.vhome.chat.DemoModel;
import com.vhome.chat.R;
import com.vhome.vhome.MainActivity;
import com.vhome.vhome.parents.ParentMain;
import com.vhome.chat.utils.PreferenceManager;
import com.hyphenate.easeui.model.EaseCompat;
import com.hyphenate.easeui.widget.EaseSwitchButton;
import com.hyphenate.util.EMLog;

import java.io.File;
import java.util.ArrayList;

/**
 * settings screen
 * 
 * 
 */
@SuppressWarnings({"FieldCanBeLocal"})
public class SettingActivity extends Activity implements OnClickListener {

	/**
	 * new message notification
	 */
	private RelativeLayout rl_switch_notification;
	/**
	 * sound
	 */
	private RelativeLayout rl_switch_sound;
	/**
	 * vibration
	 */
	private RelativeLayout rl_switch_vibrate;
	/**
	 * speaker
	 */
	private RelativeLayout rl_switch_speaker;


	/**
	 * line between sound and vibration
	 */
	private TextView textview1, textview2;

	private LinearLayout blacklistContainer;
	
	private LinearLayout userProfileContainer;

	private RelativeLayout rl_switch_chatroom_leave;
	
    private RelativeLayout rl_switch_delete_msg_when_exit_group;
	private RelativeLayout rl_set_transfer_file_by_userself;
	private RelativeLayout rl_set_autodownload_thumbnail;
	private RelativeLayout rl_switch_auto_accept_group_invitation;
    private RelativeLayout rl_switch_adaptive_video_encode;
	private RelativeLayout rl_msg_roaming;
	private RelativeLayout rl_custom_appkey;
    private RelativeLayout rl_custom_server;
	RelativeLayout rl_push_settings;
	private LinearLayout   ll_call_option;
	private LinearLayout   ll_multi_device;
	private RelativeLayout rl_mail_log;
	// show "during typing" layout
	private RelativeLayout rl_msg_typing;

	/**
	 * Diagnose
	 */
	private LinearLayout llDiagnose;
	/**
	 * display name for APNs
	 */
	private LinearLayout pushNick;
	
    private EaseSwitchButton notifySwitch;
    private EaseSwitchButton soundSwitch;
    private EaseSwitchButton vibrateSwitch;
    private EaseSwitchButton speakerSwitch;
    private EaseSwitchButton ownerLeaveSwitch;
    private EaseSwitchButton switch_delete_msg_when_exit_group;
	private EaseSwitchButton set_transfer_file_by_userself;
	private EaseSwitchButton set_autodownload_thumbnail;
	private EaseSwitchButton switch_auto_accept_group_invitation;
    private EaseSwitchButton switch_adaptive_video_encode;
	private EaseSwitchButton customServerSwitch;
	private EaseSwitchButton customAppkeySwitch;
	private EaseSwitchButton switch_msg_Roaming;
    // show "during typing" switcher
    private EaseSwitchButton switch_msg_typing;
    private DemoModel settingsModel;
    private EMOptions chatOptions;
	private EditText edit_custom_appkey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.em_conversation_settings);
		if(savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
		rl_switch_notification = (RelativeLayout) findViewById(R.id.rl_switch_notification);
		rl_switch_sound = (RelativeLayout) findViewById(R.id.rl_switch_sound);
		rl_switch_vibrate = (RelativeLayout) findViewById(R.id.rl_switch_vibrate);
		rl_switch_speaker = (RelativeLayout) findViewById(R.id.rl_switch_speaker);
		rl_switch_chatroom_leave = (RelativeLayout) findViewById(R.id.rl_switch_chatroom_owner_leave);
		rl_switch_delete_msg_when_exit_group = (RelativeLayout) findViewById(R.id.rl_switch_delete_msg_when_exit_group);
		rl_set_transfer_file_by_userself = (RelativeLayout) findViewById(R.id.rl_set_transfer_file_by_userself);
		rl_set_autodownload_thumbnail = (RelativeLayout) findViewById(R.id.rl_set_autodownload_thumbnail);
		rl_switch_auto_accept_group_invitation = (RelativeLayout) findViewById(R.id.rl_switch_auto_accept_group_invitation);
		rl_switch_adaptive_video_encode = (RelativeLayout) findViewById(R.id.rl_switch_adaptive_video_encode);
		rl_custom_appkey = (RelativeLayout) findViewById(R.id.rl_custom_appkey);
		rl_custom_server = (RelativeLayout) findViewById(R.id.rl_custom_server);
//		rl_switch_offline_call_push =  (RelativeLayout) findViewById(rl_switch_offline_call_push);
		rl_push_settings = (RelativeLayout) findViewById(R.id.rl_push_settings);
		rl_msg_roaming = (RelativeLayout) findViewById(R.id.rl_msg_roaming);

		ll_call_option = (LinearLayout) findViewById(R.id.ll_call_option);
		ll_multi_device = (LinearLayout) findViewById(R.id.ll_multi_device_management);

		rl_mail_log = (RelativeLayout) findViewById(R.id.rl_mail_log);

		rl_msg_typing = findViewById(R.id.rl_msg_typing);
		
		notifySwitch = (EaseSwitchButton) findViewById(R.id.switch_notification);
		soundSwitch = (EaseSwitchButton) findViewById(R.id.switch_sound);
		vibrateSwitch = (EaseSwitchButton) findViewById(R.id.switch_vibrate);
		speakerSwitch = (EaseSwitchButton) findViewById(R.id.switch_speaker);
		ownerLeaveSwitch = (EaseSwitchButton) findViewById(R.id.switch_owner_leave);
		switch_delete_msg_when_exit_group = (EaseSwitchButton) findViewById(R.id.switch_delete_msg_when_exit_group);
		set_transfer_file_by_userself = (EaseSwitchButton) findViewById(R.id.set_transfer_file_by_userself);
		set_autodownload_thumbnail = (EaseSwitchButton) findViewById(R.id.set_autodownload_thumbnail);
		switch_auto_accept_group_invitation = (EaseSwitchButton) findViewById(R.id.switch_auto_accept_group_invitation);
		switch_adaptive_video_encode = (EaseSwitchButton) findViewById(R.id.switch_adaptive_video_encode);
		switch_msg_Roaming = (EaseSwitchButton) findViewById(R.id.switch_msg_roaming);
		switch_msg_typing = findViewById(R.id.switch_msg_typing);


		customServerSwitch = (EaseSwitchButton) findViewById(R.id.switch_custom_server);
		customAppkeySwitch = (EaseSwitchButton) findViewById(R.id.switch_custom_appkey);

		textview1 = (TextView) findViewById(R.id.textview1);
		textview2 = (TextView) findViewById(R.id.textview2);
		
		blacklistContainer = (LinearLayout) findViewById(R.id.ll_black_list);
		userProfileContainer = (LinearLayout) findViewById(R.id.ll_user_profile);
		llDiagnose=(LinearLayout) findViewById(R.id.ll_diagnose);
		pushNick=(LinearLayout) findViewById(R.id.ll_set_push_nick);
		edit_custom_appkey = (EditText) findViewById(R.id.edit_custom_appkey);

		settingsModel = DemoHelper.getInstance().getModel();
		chatOptions = EMClient.getInstance().getOptions();
		
		blacklistContainer.setOnClickListener(this);
		userProfileContainer.setOnClickListener(this);
		rl_switch_notification.setOnClickListener(this);
		rl_switch_sound.setOnClickListener(this);
		rl_switch_vibrate.setOnClickListener(this);
		rl_switch_speaker.setOnClickListener(this);
		customAppkeySwitch.setOnClickListener(this);
		customServerSwitch.setOnClickListener(this);
		rl_custom_server.setOnClickListener(this);
		llDiagnose.setOnClickListener(this);
		pushNick.setOnClickListener(this);
		rl_switch_chatroom_leave.setOnClickListener(this);
		rl_switch_delete_msg_when_exit_group.setOnClickListener(this);
		rl_set_transfer_file_by_userself.setOnClickListener(this);
		rl_set_autodownload_thumbnail.setOnClickListener(this);
		rl_switch_auto_accept_group_invitation.setOnClickListener(this);
		rl_switch_adaptive_video_encode.setOnClickListener(this);
//		rl_switch_offline_call_push.setOnClickListener(this);
		rl_push_settings.setOnClickListener(this);
		ll_call_option.setOnClickListener(this);
		ll_multi_device.setOnClickListener(this);
		rl_mail_log.setOnClickListener(this);
		rl_msg_roaming.setOnClickListener(this);
		rl_msg_typing.setOnClickListener(this);

		// Add by zhangsong for service check.
		findViewById(R.id.ll_service_check).setOnClickListener(this);

		// the vibrate and sound notification are allowed or not?
		if (settingsModel.getSettingMsgNotification()) {
			notifySwitch.openSwitch();
		} else {
		    notifySwitch.closeSwitch();
		}

		// sound notification is switched on or not?
		if (settingsModel.getSettingMsgSound()) {
		    soundSwitch.openSwitch();
		} else {
		    soundSwitch.closeSwitch();
		}

		// vibrate notification is switched on or not?
		if (settingsModel.getSettingMsgVibrate()) {
		    vibrateSwitch.openSwitch();
		} else {
		    vibrateSwitch.closeSwitch();
		}

		// the speaker is switched on or not?
		if (settingsModel.getSettingMsgSpeaker()) {
		    speakerSwitch.openSwitch();
		} else {
		    speakerSwitch.closeSwitch();
		}

		// if allow owner leave
		if(settingsModel.isChatroomOwnerLeaveAllowed()){
		    ownerLeaveSwitch.openSwitch();
		}else{
		    ownerLeaveSwitch.closeSwitch();
		}
		
		// delete messages when exit group?
		if(settingsModel.isDeleteMessagesAsExitGroup()){
		    switch_delete_msg_when_exit_group.openSwitch();
		} else {
		    switch_delete_msg_when_exit_group.closeSwitch();
		}

		if(settingsModel.isSetTransferFileByUser()){
			set_transfer_file_by_userself.openSwitch();
		} else {
			set_transfer_file_by_userself.closeSwitch();
		}

		if(settingsModel.isSetAutodownloadThumbnail()){
			set_autodownload_thumbnail.openSwitch();
		} else {
			set_autodownload_thumbnail.closeSwitch();
		}

		if (settingsModel.isAutoAcceptGroupInvitation()) {
		    switch_auto_accept_group_invitation.openSwitch();
		} else {
		    switch_auto_accept_group_invitation.closeSwitch();
		}
		
		if (settingsModel.isAdaptiveVideoEncode()) {
            switch_adaptive_video_encode.openSwitch();
			EMClient.getInstance().callManager().getCallOptions().enableFixedVideoResolution(false);
        } else {
            switch_adaptive_video_encode.closeSwitch();
			EMClient.getInstance().callManager().getCallOptions().enableFixedVideoResolution(true);
        }

		if(settingsModel.isCustomServerEnable()){
			customServerSwitch.openSwitch();
		}else{
			customServerSwitch.closeSwitch();
        }

		if (settingsModel.isCustomAppkeyEnabled()) {
			customAppkeySwitch.openSwitch();
		} else {
			customAppkeySwitch.closeSwitch();
		}

		if (settingsModel.isMsgRoaming()) {
			switch_msg_Roaming.openSwitch();
		} else {
			switch_msg_Roaming.closeSwitch();
		}

		if (settingsModel.isShowMsgTyping()) {
			switch_msg_typing.openSwitch();
		} else {
			switch_msg_typing.closeSwitch();
		}

		edit_custom_appkey.setEnabled(settingsModel.isCustomAppkeyEnabled());

		edit_custom_appkey.setText(settingsModel.getCutomAppkey());
		edit_custom_appkey.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void afterTextChanged(Editable s) {
				PreferenceManager.getInstance().setCustomAppkey(s.toString());
			}
		});
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rl_switch_notification:
				if (notifySwitch.isSwitchOpen()) {
					notifySwitch.closeSwitch();
					rl_switch_sound.setVisibility(View.GONE);
					rl_switch_vibrate.setVisibility(View.GONE);
					textview1.setVisibility(View.GONE);
					textview2.setVisibility(View.GONE);
					settingsModel.setSettingMsgNotification(false);
				} else {
					notifySwitch.openSwitch();
					rl_switch_sound.setVisibility(View.VISIBLE);
					rl_switch_vibrate.setVisibility(View.VISIBLE);
					textview1.setVisibility(View.VISIBLE);
					textview2.setVisibility(View.VISIBLE);
					settingsModel.setSettingMsgNotification(true);
				}
				break;
			case R.id.rl_switch_sound:
				if (soundSwitch.isSwitchOpen()) {
					soundSwitch.closeSwitch();
					settingsModel.setSettingMsgSound(false);
				} else {
					soundSwitch.openSwitch();
					settingsModel.setSettingMsgSound(true);
				}
				break;
			case R.id.rl_switch_vibrate:
				if (vibrateSwitch.isSwitchOpen()) {
					vibrateSwitch.closeSwitch();
					settingsModel.setSettingMsgVibrate(false);
				} else {
					vibrateSwitch.openSwitch();
					settingsModel.setSettingMsgVibrate(true);
				}
				break;
			case R.id.rl_switch_speaker:
				if (speakerSwitch.isSwitchOpen()) {
					speakerSwitch.closeSwitch();
					settingsModel.setSettingMsgSpeaker(false);
				} else {
					speakerSwitch.openSwitch();
					settingsModel.setSettingMsgVibrate(true);
				}
				break;
			case R.id.rl_switch_chatroom_owner_leave:
				if(ownerLeaveSwitch.isSwitchOpen()){
					ownerLeaveSwitch.closeSwitch();
					settingsModel.allowChatroomOwnerLeave(false);
					chatOptions.allowChatroomOwnerLeave(false);
				}else{
					ownerLeaveSwitch.openSwitch();
					settingsModel.allowChatroomOwnerLeave(true);
					chatOptions.allowChatroomOwnerLeave(true);
				}
				break;
			case R.id.rl_switch_delete_msg_when_exit_group:
				if(switch_delete_msg_when_exit_group.isSwitchOpen()){
					switch_delete_msg_when_exit_group.closeSwitch();
					settingsModel.setDeleteMessagesAsExitGroup(false);
					chatOptions.setDeleteMessagesAsExitGroup(false);
				}else{
					switch_delete_msg_when_exit_group.openSwitch();
					settingsModel.setDeleteMessagesAsExitGroup(true);
					chatOptions.setDeleteMessagesAsExitGroup(true);
				}
				break;
			case R.id.rl_set_transfer_file_by_userself:
				if(set_transfer_file_by_userself.isSwitchOpen()){
					set_transfer_file_by_userself.closeSwitch();
					settingsModel.setTransfeFileByUser(false);
					chatOptions.setAutoTransferMessageAttachments(false);
				}else{
					set_transfer_file_by_userself.openSwitch();
					settingsModel.setTransfeFileByUser(true);
					chatOptions.setAutoTransferMessageAttachments(true);
				}
				break;
			case R.id.rl_set_autodownload_thumbnail:
				if(set_autodownload_thumbnail.isSwitchOpen()){
					set_autodownload_thumbnail.closeSwitch();
					settingsModel.setAutodownloadThumbnail(false);
					chatOptions.setAutoDownloadThumbnail(false);
				}else{
					set_autodownload_thumbnail.openSwitch();
					settingsModel.setAutodownloadThumbnail(true);
					chatOptions.setAutoDownloadThumbnail(true);
				}
				break;
			case R.id.rl_switch_auto_accept_group_invitation:
				if(switch_auto_accept_group_invitation.isSwitchOpen()){
					switch_auto_accept_group_invitation.closeSwitch();
					settingsModel.setAutoAcceptGroupInvitation(false);
					chatOptions.setAutoAcceptGroupInvitation(false);
				}else{
					switch_auto_accept_group_invitation.openSwitch();
					settingsModel.setAutoAcceptGroupInvitation(true);
					chatOptions.setAutoAcceptGroupInvitation(true);
				}
				break;
			case R.id.rl_switch_adaptive_video_encode:
				EMLog.d("switch", "" + !switch_adaptive_video_encode.isSwitchOpen());
				if (switch_adaptive_video_encode.isSwitchOpen()){
					switch_adaptive_video_encode.closeSwitch();
					settingsModel.setAdaptiveVideoEncode(false);
					EMClient.getInstance().callManager().getCallOptions().enableFixedVideoResolution(true);

				}else{
					switch_adaptive_video_encode.openSwitch();
					settingsModel.setAdaptiveVideoEncode(true);
					EMClient.getInstance().callManager().getCallOptions().enableFixedVideoResolution(false);
				}
				break;
			case R.id.ll_black_list:
				startActivity(new Intent(this, BlacklistActivity.class));
				break;
			case R.id.ll_diagnose:
				startActivity(new Intent(this, DiagnoseActivity.class));
				break;
			case R.id.ll_set_push_nick:
				startActivity(new Intent(this, OfflinePushNickActivity.class));
				break;
			case R.id.ll_call_option:
				startActivity(new Intent(this, CallOptionActivity.class));
				break;
			case R.id.ll_multi_device_management:
				startActivity(new Intent(this, MultiDeviceActivity.class));
				break;
			case R.id.ll_user_profile:
				startActivity(new Intent(this, UserProfileActivity.class).putExtra("setting", true)
						.putExtra("username", EMClient.getInstance().getCurrentUser()));
				break;
			case R.id.switch_custom_server:
				if(customServerSwitch.isSwitchOpen()){
					customServerSwitch.closeSwitch();
					settingsModel.enableCustomServer(false);
				}else{
					customServerSwitch.openSwitch();
					settingsModel.enableCustomServer(true);
				}
				break;
			case R.id.switch_custom_appkey:
				if(customAppkeySwitch.isSwitchOpen()){
					customAppkeySwitch.closeSwitch();
					settingsModel.enableCustomAppkey(false);
				}else{
					customAppkeySwitch.openSwitch();
					settingsModel.enableCustomAppkey(true);
				}
				edit_custom_appkey.setEnabled(customAppkeySwitch.isSwitchOpen());
				break;
			case R.id.rl_msg_roaming:
				if (switch_msg_Roaming.isSwitchOpen()) {
					switch_msg_Roaming.closeSwitch();
					settingsModel.setMsgRoaming(false);
				} else {
					switch_msg_Roaming.openSwitch();
					settingsModel.setMsgRoaming(true);
				}
				break;
			case R.id.rl_custom_server:
				startActivity(new Intent(this, SetServersActivity.class));
				break;
			case R.id.rl_push_settings:
				startActivity(new Intent(this, OfflinePushSettingsActivity.class));
				break;
			case R.id.rl_mail_log:
				sendLogThroughMail();
				break;
			case R.id.ll_service_check:
				startActivity(new Intent(this, ServiceCheckActivity.class));
				break;
			case R.id.rl_msg_typing:
				if (switch_msg_typing.isSwitchOpen()) {
					switch_msg_typing.closeSwitch();
					settingsModel.showMsgTyping(false);
				} else {
					switch_msg_typing.openSwitch();
					settingsModel.showMsgTyping(true);
				}
				break;
			default:
				break;
		}
	}


    @Override
    public void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
        if(ParentMain.isConflict){
        	outState.putBoolean("isConflict", true);
        }else if(ParentMain.getCurrentAccountRemoved()){
        	outState.putBoolean(Constant.ACCOUNT_REMOVED, true);
        }
    }

	void sendLogThroughMail() {
		String logPath = "";
		try {
			logPath = EMClient.getInstance().compressLogs();
		} catch (Exception e) {
			e.printStackTrace();
			this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(SettingActivity.this, "compress logs failed", Toast.LENGTH_LONG).show();
				}
			});
			return;
		}
		File f = new File(logPath);
		File storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		if (f.exists() && f.canRead()) {
			try {
				storage.mkdirs();
				File temp = File.createTempFile("hyphenate", ".log.gz", storage);
				if (!temp.canWrite()) {
					return;
				}
				boolean result = f.renameTo(temp);
				if (result == false) {
					return;
				}
				Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
				intent.setData(Uri.parse("mailto:"));
				intent.putExtra(Intent.EXTRA_SUBJECT, "log");
				intent.putExtra(Intent.EXTRA_TEXT, "log in attachment: " + temp.getAbsolutePath());

				intent.setType("application/octet-stream");
				ArrayList<Uri> uris = new ArrayList<>();
				uris.add(EaseCompat.getUriForFile(SettingActivity.this, temp));
				intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,uris);
				startActivity(intent);
			} catch (final Exception e) {
				e.printStackTrace();
				this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(SettingActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
					}
				});
			}
		}
	}
}
