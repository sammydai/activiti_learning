package com.reanod.workflow.variable;

import junit.framework.TestCase;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

}
