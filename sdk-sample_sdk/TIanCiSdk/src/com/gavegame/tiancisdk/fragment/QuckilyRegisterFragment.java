package com.gavegame.tiancisdk.fragment;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.R;
import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.ResponseMsg;
import com.gavegame.tiancisdk.utils.NormalUtils;
import com.gavegame.tiancisdk.utils.TCLogUtils;
import com.gavegame.tiancisdk.widget.PolicyDialog;

public class QuckilyRegisterFragment extends TCBaseFragment {

	// private View view;
	// private FragmentChangedCallback callback;
	private EditText et_username;
	private EditText et_psw;
	// private Bundle bundle;
	// private final String ACCOUNT = "username";
	private TextView tv_phone_num_reg;
	private boolean policyIsRead = true;

	@Override
	void initID() {

		final CheckBox checkBox = (CheckBox) view.findViewById(R.id.check_box);
		checkBox.setChecked(policyIsRead);
		checkBox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				policyIsRead = !policyIsRead;
				checkBox.setChecked(policyIsRead);
			}
		});
		view.findViewById(R.id.tv_user_protocol).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						popPolicyDialog();
						policyIsRead = true;
						checkBox.setChecked(policyIsRead);
					}
				});
		et_username = (EditText) view
				.findViewById(R.id.et_quckily_reg_username);
		et_psw = (EditText) view.findViewById(R.id.et_quckily_reg_psw);
		view.findViewById(R.id.bt_register_finish).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (!dataCheck(et_username.getText() + "",
								et_psw.getText() + "")) {
							return;
						}
						//普通账号注册
						TianCi.getInstance().register(
								et_username.getText() + "",
								et_psw.getText() + "", new RequestCallBack() {

									@Override
									public void onSuccessed(int code) {
										if (code != 1) {
											callback.jumpNextPage(Config.DIALOG_BIND_FRAGMENT);
										} else {
											TCLogUtils.toastShort(
													getActivity(), TianCi
															.getInstance()
															.getTcsso());
										}
									}

									@Override
									public void onFailure(ResponseMsg msg) {
										TCLogUtils.toastShort(getActivity(),
												msg.getRetMsg());
									}
								});
					}
				});
		;
		tv_phone_num_reg = (TextView) view
				.findViewById(R.id.tv_phone_num_register);
		tv_phone_num_reg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.jumpNextPage(Config.PHONE_NUM_REGISTER_FRAGMENT);
			}
		});
	}

	@Override
	int getLayoutId() {
		return R.layout.quckily_register_fragment;
	}

	void popPolicyDialog() {
		PolicyDialog policyDialog = new PolicyDialog(getActivity());
		policyDialog.show();
	}

	// 检测用户名，密码是否合法
	private boolean dataCheck(String... params) {
		if (TextUtils.isEmpty(params[0]) || TextUtils.isEmpty(params[1])) {
//			TCSdkToast.show("不能为空", getActivity());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			TCSdkToast.hide();
			return false;
		}

		if (NormalUtils.isAllNum(params[0])) {
//			TCSdkToast.show("用户名不能为纯数字", getActivity());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			TCSdkToast.hide();
			return false;
		}
		return true;
	}

}
