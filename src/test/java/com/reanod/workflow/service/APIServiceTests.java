package com.reanod.workflow.service;

import com.reanod.workflow.config.SecurityUtil;
import junit.framework.TestCase;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class APIServiceTests extends TestCase {

	@Autowired
	private ProcessRuntime processRuntime;

	@Autowired
	private TaskRuntime taskRuntime;

	@Autowired
	private SecurityUtil securityUtil;

	//流程部署工作会自动实现部署
	//前提是必须放在resources/processes/*.bpmn
	@Test
	public void testDefinition() {
		securityUtil.logInAs("salaboy"); //springsecurity 的认证工作
		Page processDefinitionPage = processRuntime
				.processDefinitions(Pageable.of(0, 10));
		System.out.println(processDefinitionPage.getTotalItems());
		for (Object pd : processDefinitionPage.getContent()) {
			System.out.println("\t > Process definition: " + pd);
		}
	}

	@Test
	public void startProcess() {
		securityUtil.logInAs("salaboy"); //springsecurity 的认证工作
		ProcessInstance processInstance = processRuntime
				.start(ProcessPayloadBuilder
						.start()
						.withProcessDefinitionKey("sampleProcess_1")
						.build()); //启动流程实例　
		System.out.println("processInstance ID: " + processInstance.getId());

	}

	@Test
	public void takeTask() {

		// securityUtil.logInAs("ryandawsonuk"); //制定用户信息 完成task1
		securityUtil.logInAs("erdemedeiros"); //制定用户信息 完成task2
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
