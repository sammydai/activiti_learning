package com.reanod.workflow.process;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Package: com.reanod.workflow.process
 * @Description:
 * @Author: Sammy
 * @Date: 2020/2/14 23:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessSuspendTests {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ProcessEngineConfiguration processEngineConfiguration;

	@Autowired
	private RepositoryService repositoryService;

	/**
	 * @description 流程实例挂起与激活
	 * @Exception
	 *
	 */

	@Test
	public void suspendProcessTest() {
		ProcessDefinition processDefinition =
				repositoryService.createProcessDefinitionQuery()
						.processDefinitionKey("holiday")
						.latestVersion().singleResult();
		boolean isSuspend = processDefinition.isSuspended();
		String definitionId = processDefinition.getId();
		if (isSuspend) {
			repositoryService.activateProcessDefinitionById(definitionId, true, null);
			System.out.println("流程定义" + definitionId + "激活");
		} else {
			repositoryService.suspendProcessDefinitionById(definitionId, true, null);
			System.out.println("流程定义" + definitionId + "挂起");

		}


	}

	@Test
	public void suspendProcessSingleTest() {

		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceBusinessKey("1001").singleResult();

		String id = processInstance.getId();

		boolean suspended = processInstance.isSuspended();
		if (suspended) {
			runtimeService.activateProcessInstanceById(id);
			System.out.println("流程实例" + id + "激活");
		} else {
			runtimeService.suspendProcessInstanceById(id);
			System.out.println("流程实例" + id + "挂起");

		}
	}
}
