package com.vhome.chat.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.vhome.chat.R;
import com.hyphenate.util.EMLog;

/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
/**
 * Diagnose activity；user can upload log for debug purpose
 * 
 * @author lyuzhao
 * 
 */
public class DiagnoseActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.em_activity_diagnose);

		Button uploadLog = (Button) findViewById(R.id.button_uploadlog);
		uploadLog.setOnClickListener(this);
	}

	public void back(View view) {
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_uploadlog:
			uploadlog();
			break;

		default:
			break;
		}

	}

	private ProgressDialog progressDialog;

	public void uploadlog() {

		if (progressDialog == null)
			progressDialog = new ProgressDialog(this);
		String stri = getResources().getString(R.string.Upload_the_log);
		progressDialog.setMessage(stri);
		progressDialog.setCancelable(false);
		progressDialog.show();
		final String st = getResources().getString(R.string.Log_uploaded_successfully);
		EMClient.getInstance().uploadLog(new EMCallBack() {

			@Override
			public void onSuccess() {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progressDialog.dismiss();
						Toast.makeText(DiagnoseActivity.this, st,
								Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public void onProgress(final int progress, String status) {
				// getActivity().runOnUiThread(new Runnable() {
				//
				// @Override
				// public void run() {
				// progressDialog.setMessage("上传中 "+progress+"%");
				//
				// }
				// });

			}
			String st3 = getResources().getString(R.string.Log_Upload_failed);
			@Override
			public void onError(int code, String message) {
				EMLog.e("###", message);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progressDialog.dismiss();
						Toast.makeText(DiagnoseActivity.this, st3,
								Toast.LENGTH_SHORT).show();
					}
				});

			}
		});

	}

}
