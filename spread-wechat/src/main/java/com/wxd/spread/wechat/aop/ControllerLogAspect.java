package com.wxd.spread.wechat.aop;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 
 * TODO 未起作用，回头查看
 *
 */
@Aspect
@Component
public class ControllerLogAspect {
	private Logger logger = Logger.getLogger(getClass());

	@Pointcut("within(com.wxd.spread.wechat..*.controller..*)")
	public void inController() {
	}

	@Pointcut("execution(public * com.wxd.spread.wechat..*.controller..*.*(..))")
	public void controller() {
	}

	@Before("inController()")
	public void writeBeforeLog(JoinPoint jp) {
		this.debugInController(jp, "Start");
	}

	@After("inController()")
	public void writeAfterLog(JoinPoint jp) {
		this.debugInController(jp, "End");
	}

	private void debugInController(JoinPoint jp, String msg) {
		String userName = getLoginUserName();

		this.logger.info("\n【" + userName + "】" + jp.getTarget().getClass().getSimpleName() + "."
				+ jp.getSignature().getName() + "() " + msg + " ");
	}

	private static String getLoginUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			return authentication.getName();
		}

		return "Anonymous";
	}

	@Before("controller()")
	public void writeParams(JoinPoint jp) {
		String[] names = ((CodeSignature) jp.getSignature()).getParameterNames();
		Object[] args = jp.getArgs();

		if (ArrayUtils.isEmpty(names)) {
			return;
		}

		StringBuilder sb = new StringBuilder("Arguments: ");
		for (int i = 0; i < names.length; i++) {
			sb.append(names[i] + " = " + args[i] + ",");
		}

		debugInController(jp, sb.toString());
	}

}
