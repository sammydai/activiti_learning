package com.reanod.workflow.task;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Package: com.reanod.workflow.process
 * @Description:
 * @Author: Sammy
 * @Date: 2020/2/14 23:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskTests {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ProcessEngineConfiguration processEngineConfiguration;

	@Autowired
	private RepositoryService repositoryService;

	@Test
	public void taskQuery() {

		List<Task> taskList = taskService.createTaskQuery()
				.processDefinitionKey("holiday")
				.taskAssignee("zhangsan")
				.list();

		for (Task task : taskList) {
			System.out.println("流程实例ID:"+task.getProcessInstanceId());
			System.out.println("任务ID:"+task.getId());
			System.out.println("任务负责人:"+task.getAssignee());
			System.out.println("任务名称:"+task.getName());

		}
	}

	/**
	 * @description 任务查询
	 * @Exception
	 *
	 */
	@Test
	public void taskQuerySingle() {

		Task task = taskService.createTaskQuery()
				.processDefinitionKey("holiday")
				.taskAssignee("zhangsan")
				.processInstanceBusinessKey("1001")
				.singleResult();

		System.out.println("流程实例ID:"+task.getProcessInstanceId());
		System.out.println("任务ID:"+task.getId());
		System.out.println("任务负责人:"+task.getAssignee());
		System.out.println("任务名称:"+task.getName());
		System.out.println("===============complete===============");
		taskService.complete(task.getId());

	}

	/**
	 * @description 处理任务
	 * @Exception
	 *
	 */


	@Test
	public void taskComplete() {
		Task task = taskService.createTaskQuery()
				.processDefinitionKey("holiday3")
				// .taskAssignee("zhangsan0")
				.singleResult();

		System.out.println("流程实例ID:"+task.getProcessInstanceId());
		System.out.println("任务ID:"+task.getId());
		System.out.println("任务负责人:"+task.getAssignee());
		System.out.println("任务名称:"+task.getName());
		System.out.println("===============complete===============");
		taskService.complete(task.getId());


	}


}
