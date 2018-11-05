package net.sourceforge.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;
import java.util.Stack;


public class AppActivityManager {

	private static final String TAG = AppActivityManager.class.getSimpleName();
	private static Stack<Activity> mActivityStack;
	private static AppActivityManager instance;

	private AppActivityManager() {
	}

	public static AppActivityManager getInstance() {
		if (instance == null) {
			instance = new AppActivityManager();
		}
		return instance;
	}

	public void popActivityWithFinish(Activity activity) {
			if (activity != null) {
				activity.finish();
				mActivityStack.remove(activity);
				// mActivityStack.pop();
				activity = null;
			}
		}
	
	// 退出栈顶Activity
	public void popActivity(Activity activity) {
		if (activity != null) {
			mActivityStack.remove(activity);
			// mActivityStack.pop();
			activity = null;
		}
	}

	// 获得当前栈顶Activity
	public Activity currentActivity() {
		Activity activity = null;
		if(mActivityStack != null && mActivityStack.size()>0) {
			activity = mActivityStack.lastElement();
		}
		// Activity activity = mActivityStack.pop();
		return activity;
	}

	// 将当前Activity推入栈中
	public void pushActivity(Activity activity) {
		if (mActivityStack == null) {
			mActivityStack = new Stack<Activity>();
		}
		Log.d(TAG, "push activity:" + activity.getLocalClassName());
		mActivityStack.add(activity);
		// mActivityStack.push(activity);
	}

	// 退出栈中所有Activity
	public void popAllActivityExceptOne(Class<Activity> cls) {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				continue;
			}
			if (activity.getClass().equals(cls)) {
				break;
			}
			popActivityWithFinish(activity);
		}
	}

	// 退出栈中所有Activity
	public void popAllActivity() {
		while(true) {
			Activity activity = currentActivity();
			if (activity == null) {
				Log.d(TAG, "popActivity is null:");
				if(mActivityStack!=null && mActivityStack.size()>0) {
					mActivityStack.remove(activity);
				}else {
					break;
				}
				continue;
			}
			Log.d(TAG, "popActivity:" + activity.getLocalClassName());
			popActivityWithFinish(activity);
		}
	}
	
	
	public void startInitialActivity(Context context) {
		if(needStartApp(context)) {
			Intent intent = new Intent();
//			intent.setClass(context, SplashActivity.class);
			context.startActivity(intent);
		}
	}
	
	private boolean needStartApp(Context context) {
		final ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		final List<RunningTaskInfo> tasksInfo = am.getRunningTasks(1024);
		if (!tasksInfo.isEmpty()) {
			final String ourAppPackageName = context.getPackageName();
			RunningTaskInfo taskInfo;
			final int size = tasksInfo.size();
			for (int i = 0; i < size; i++) {
				taskInfo = tasksInfo.get(i);
				if (ourAppPackageName.equals(taskInfo.baseActivity
						.getPackageName())) {
					return taskInfo.numActivities == 1;
				}
			}
		}
		return true;
	}
}
