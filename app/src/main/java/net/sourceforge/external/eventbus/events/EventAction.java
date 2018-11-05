package net.sourceforge.external.eventbus.events;

import android.content.Intent;

public class EventAction {

	private Intent intent;
	
	private String messageTag;
	
	private int position;

	
	public EventAction() {
		super();
	}
	
	public EventAction(Intent intent, String messageTag) {
		super();
		this.intent = intent;
		this.messageTag = messageTag;
	}
	
	public EventAction(int position, String messageTag) {
		super();
		this.position = position;
		this.messageTag = messageTag;
	}

	public EventAction(int position, Intent intent) {
		super();
		this.position = position;
		this.intent = intent;
	}
	
	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	public String getMessageTag() {
		return messageTag;
	}

	public void setMessageTag(String messageTag) {
		this.messageTag = messageTag;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public class EventKey {

		public static final String KEY_USER_LOGOUT = "KEY_USER_LOGOUT";

		public static final String KEY_USER_LOGIN_SUCCESS = "KEY_USER_LOGIN_SUCCESS";

		public static final String KEY_USER_INFO_MODIFY = "KEY_USER_INFO_MODIFY";

		public static final String KEY_USER_INFO_ADD_CUSTOM_TAG = "KEY_USER_INFO_ADD_CUSTOM_TAG";

		public static final String KEY_USER_NEED_LOGIN = "KEY_USER_NEED_LOGIN";

		public static final String KEY_USER_CANCEL_ATTENTION_FORUM = "KEY_USER_CANCEL_ATTENTION_FORUM";

		public EventKey() {
		}
	}
	
}
