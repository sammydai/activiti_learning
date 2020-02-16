package com.reanod.workflow.candidate;

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

@RunWith(SpringRunner.class)
@SpringBootTest
public class CandidateTests extends TestCase {

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
				.addClasspathResource("diagram/holiday5.bpmn")
				.addClasspathResource("diagram/holiday5.png")
				.name("请假流程-候选人")
				.deploy();
		System.out.println("deploymentName:" + deployment.getName());
		System.out.println("deploymentId:" + deployment.getId());
		System.out.println("========================================");
		System.out.println("========================================");
	}

	@Test
	public void processInstanceTest() {
		String key = "holiday5";
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
		System.out.println("processInstance ID: " + processInstance.getId());
		System.out.println("processInstance NAME:" + processInstance.getName());
	}

	@Test
	public void taskQueryWithCandidate() {
		String key = "holiday5";
		String candidate_user = "zhangsan5";
		Task task = taskService.createTaskQuery()
				.processDefinitionKey(key)
				.taskCandidateUser(candidate_user) //设置候选用户
				.singleResult();
		if (task != null) {
			System.out.println("流程实例ID:" + task.getProcessInstanceId());
			System.out.println("任务ID:" + task.getId());
			System.out.println("任务负责人:" + task.getAssignee());
			System.out.println("任务名称:" + task.getName());

			// System.out.println("===============complete task begin===============");
			// // taskService.complete(task.getId()); //完成任务时，设置流程的变量
			// System.out.println("===============complete task end===============");

			System.out.println("===============task claim begin===============");
			taskService.claim(task.getId(),candidate_user);
			System.out.println("===============task claim end===============");

		}


	}

	@Test
	public void taskQueryWithAssignee() {
		String key = "holiday5";
		String assignee_user = "zhangsan5";
		Task task = taskService.createTaskQuery()
				.processDefinitionKey(key)
				.taskAssignee(assignee_user)
				.singleResult();
		if (task != null) {
			System.out.println("流程实例ID:" + task.getProcessInstanceId());
			System.out.println("任务ID:" + task.getId());
			System.out.println("任务负责人:" + task.getAssignee());
			System.out.println("任务名称:" + task.getName());

			// System.out.println("===============complete task begin===============");
			// // taskService.complete(task.getId()); //完成任务时，设置流程的变量
			// System.out.println("===============complete task end===============");

			// System.out.println("===============task claim begin===============");
			// taskService.claim(task.getId(),candidate_user);
			// System.out.println("===============task claim end===============");

		}

	}


	@Test
	public void taskQueryReclaim() {
		String key = "holiday5";
		String assignee_user = "zhangsan5";
		Task task = taskService.createTaskQuery()
				.processDefinitionKey(key)
				.taskAssignee(assignee_user)
				.singleResult();
		if (task != null) {
			System.out.println("流程实例ID:" + task.getProcessInstanceId());
			System.out.println("任务ID:" + task.getId());
			System.out.println("任务负责人:" + task.getAssignee());
			System.out.println("任务名称:" + task.getName());
			// System.out.println("===============complete task begin===============");
			// // taskService.complete(task.getId()); //完成任务时，设置流程的变量
			// System.out.println("===============complete task end===============");

			// System.out.println("===============task claim begin===============");
			// taskService.claim(task.getId(),candidate_user);
			// System.out.println("===============task claim end===============");

			System.out.println("===============task reclaim begin===============");
			taskService.setAssignee(task.getId(),null);
			System.out.println("===============task reclaim end===============");

		}

	}

	@Test
	public void taskQueryClaimToSomeone() {
		String key = "holiday5";
		String assignee_user = "zhangsan5";
		Task task = taskService.createTaskQuery()
				.processDefinitionKey(key)
				.taskAssignee(assignee_user)
				.singleResult();
		if (task != null) {
			System.out.println("流程实例ID:" + task.getProcessInstanceId());
			System.out.println("任务ID:" + task.getId());
			System.out.println("任务负责人:" + task.getAssignee());
			System.out.println("任务名称:" + task.getName());
			// System.out.println("===============complete task begin===============");
			// // taskService.complete(task.getId()); //完成任务时，设置流程的变量
			// System.out.println("===============complete task end===============");

			// System.out.println("===============task claim begin===============");
			// taskService.claim(task.getId(),candidate_user);
			// System.out.println("===============task claim end===============");

			System.out.println("===============task reclaim begin===============");
			taskService.setAssignee(task.getId(),"lisi5");
			System.out.println("===============task reclaim end===============");

		}

	}


	@Test
	public void taskComplete() {
		String key = "holiday5";
		Task task = taskService.createTaskQuery()
				.processDefinitionKey(key)
				.taskAssignee("xiaowang")
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
			taskService.complete(task.getId()); //完成任务时，设置流程的变量
			System.out.println("===============complete task end===============");

		}
	}
}
