package net.sourceforge.external.aspect;

import net.sourceforge.commons.log.SWLog;
import net.sourceforge.external.eventbus.events.EventAction;
import net.sourceforge.manager.UserManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.greenrobot.eventbus.EventBus;

@Aspect
public class UserAuthAspect {

    @Around("execution(* *(..)) && @annotation(UserAuth)")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint, UserAuth userAuth) throws Throwable {
        SWLog.d("执行消息安全注解");
        if (UserManager.getInstance().isUserAuth()) {
            SWLog.d("向下执行");
            return joinPoint.proceed();// 执行
        }
        EventBus.getDefault().post(new EventAction(null, EventAction.EventKey.KEY_USER_NEED_LOGIN));
        return new Object();
    }
}
