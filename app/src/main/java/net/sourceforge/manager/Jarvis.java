package net.sourceforge.manager;


/**
 * 
 * Just a Rather Very Intelligent Class
 * 
 * @author terry.c May 26, 2016
 * 
 */
public class Jarvis {

	private static final Jarvis instance = new Jarvis();

//	/**
//	 * login engine class
//	 */
//	private LoginEngine loginEngine;
//
//	/**
//	 * notice engine class
//	 */
//	private NoticeEngine noticeEngine;
//
//	/**
//	 * request engine
//	 */
//	private JoyGoRequest requestImp;

	private Jarvis() {
	}

	public static Jarvis getInstance() {
		return instance;
	}

//	public ILoginEngine createLoginLogicEngine() {
//		if (loginEngine == null) {
//			loginEngine = new LoginEngine();
//		}
//		return loginEngine;
//	}
//
//	public INoticeEngine createNoticeEngine() {
//		if (noticeEngine == null) {
//			noticeEngine = new NoticeEngine();
//		}
//		return noticeEngine;
//	}
//
//	public JoyGoRequest createRequestEngine() {
//		if (requestImp == null) {
//			requestImp = new JoyGoRequestImp();
//		}
//		return requestImp;
//	}

	public void initApplication() {
	}
	
}
