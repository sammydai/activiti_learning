package com.reanod.workflow.controller;

import com.reanod.workflow.config.SecurityUtil;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package: com.reanod.workflow.controller
 * @Description:
 * @Author: Sammy
 * @Date: 2020/2/17 01:11
 */

@RestController
public class TestController {

	@Autowired
	private ProcessRuntime processRuntime;

	@Autowired
	private TaskRuntime taskRuntime;

	@Autowired
	private SecurityUtil securityUtil;

	@RequestMapping("/hello")
	public void hello() {
		Page<Task> taskPage = taskRuntime.tasks(Pageable.of(0, 10)); //分页查询任务列表
		if (taskPage.getTotalItems()>0) {
			//有任务
			for (Task task : taskPage.getContent()) {
				//遍历所有任务
				System.out.println("任务1："+task);
				//claim 任务 candidate_group:
				taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
				//执行任务
				taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
			}
		}

		//再次查询新的任务
		taskPage = taskRuntime.tasks(Pageable.of(0, 10)); //分页查询任务列表
		if (taskPage.getTotalItems()>0) {
			//有任务
			for (Task task : taskPage.getContent()) {
				//遍历所有任务
				System.out.println("任务2："+task);
			}
		}

	}
}

