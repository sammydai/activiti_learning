package com.reanod.workflow.inclusive;

import junit.framework.TestCase;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InclusiveGatewayTests extends TestCase {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ProcessEngineConfiguration processEngineConfiguration;

	@Autowired
	private RepositoryService repositoryService;

	/**
	 * @description 部署流程
	 * 通过bpmn 和 png方式部署
	 */

	@Test
	public void deploymentTest() {
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("diagram/examine.bpmn")
				.addClasspathResource("diagram/examine.png")
				.name("体检流程")
				.deploy();
		System.out.println("deploymentName:" + deployment.getName());
		System.out.println("deploymentId:" + deployment.getId());
		System.out.println("========================================");
		System.out.println("========================================");
	}

	@Test
	public void processInstanceTest() {
		String key = "examine";
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
		System.out.println("processInstance ID: " + processInstance.getId());
		System.out.println("processInstance NAME:" + processInstance.getName());
	}

	@Test
	public void taskQueryWithAssignee() {
		String key = "examine";
		String assignee_user = "zhangsan";
		Integer user_type = 2;
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("userType", user_type);
		Task task = taskService.createTaskQuery()
				.processDefinitionKey(key)
				.taskAssignee(assignee_user)
				.singleResult();
		if (task != null) {
			System.out.println("流程实例ID:" + task.getProcessInstanceId());
			System.out.println("任务ID:" + task.getId());
			System.out.println("任务负责人:" + task.getAssignee());
			System.out.println("任务名称:" + task.getName());
			System.out.println("===============complete task begin inclusive===============");
			taskService.complete(task.getId(),hashMap); //完成任务时，设置流程的变量
			System.out.println("===============complete task end inclusive===============");
		}
	}

	@Test
	public void taskQueryWithAssignee1() {
		String key = "examine";
		String assignee_user = "xiaowang";
		// Integer user_type = 2;
		// Map<String, Object> hashMap = new HashMap<>();
		// hashMap.put("userType", user_type);
		Task task = taskService.createTaskQuery()
				.processDefinitionKey(key)
				.taskAssignee(assignee_user)
				.singleResult();
		if (task != null) {
			System.out.println("流程实例ID:" + task.getProcessInstanceId());
			System.out.println("任务ID:" + task.getId());
			System.out.println("任务负责人:" + task.getAssignee());
			System.out.println("任务名称:" + task.getName());
			System.out.println("===============complete task begin inclusive===============");
			taskService.complete(task.getId()); //完成任务时，设置流程的变量
			System.out.println("===============complete task end inclusive===============");
		}
	}

}
