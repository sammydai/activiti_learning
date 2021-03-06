package com.reanod.workflow.variable;

import com.reanod.workflow.domain.Holiday;
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
public class VariableTests extends TestCase {

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
				.addClasspathResource("diagram/holiday4.bpmn")
				.addClasspathResource("diagram/holiday4.png")
				.name("请假流程-流程变量")
				.deploy();
		System.out.println("deploymentName:" + deployment.getName());
		System.out.println("deploymentId:" + deployment.getId());
		System.out.println("========================================");
		System.out.println("========================================");
	}

	@Test
	public void processInstanceWithVariablesTest() {
		String key = "holiday4";
		// Map<String, Object> hashMap = new HashMap<>();
		// Holiday holiday = new Holiday();
		// holiday.setNum(5F);
		// hashMap.put("holiday", holiday);
		// ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, hashMap);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
		System.out.println("processInstance ID: " + processInstance.getId());
		System.out.println("processInstance NAME:" + processInstance.getName());
	}

	@Test
	public void setVariablesWithProcessInstance() {
		String processId = "247f1b55-5010-11ea-a347-acde48001122";
		Holiday holiday = new Holiday();
		holiday.setNum(5F);
		runtimeService.setVariable(processId,"holiday",holiday);
	}


	@Test
	public void taskComplete() {
		String key = "holiday4";
		Task task = taskService.createTaskQuery()
				.processDefinitionKey(key)
				.taskAssignee("zhangsan4")
				// .taskAssignee("lisi4")
				// .taskAssignee("zhaoliu4")
				// .taskAssignee("wangwu4")
				.singleResult();
		if (task != null) {
			System.out.println("流程实例ID:" + task.getProcessInstanceId());
			System.out.println("任务ID:" + task.getId());
			System.out.println("任务负责人:" + task.getAssignee());
			System.out.println("任务名称:" + task.getName());
			System.out.println("===============complete task begin===============");
			Map<String, Object> hashMap = new HashMap<>();
			Holiday holiday = new Holiday();
			holiday.setNum(5F);
			hashMap.put("holiday", holiday);
			// taskService.setVariablesLocal(task.getId(),hashMap);
			taskService.complete(task.getId(),hashMap); //完成任务时，设置流程的变量
			System.out.println("===============complete task end===============");

		}
	}
}
