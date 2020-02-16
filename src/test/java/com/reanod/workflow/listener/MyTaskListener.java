package com.reanod.workflow.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @Package: com.reanod.workflow.listener
 * @Description:
 * @Author: Sammy
 * @Date: 2020/2/15 19:44
 */

public class MyTaskListener implements TaskListener {
	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println("=====delegateProcess==========");
	}
}
