package com.reanod.workflow.process;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
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
public class ProcessTests {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ProcessEngineConfiguration processEngineConfiguration;

	@Autowired
	private RepositoryService repositoryService;

	@Test
	public void queryProcess() {
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		List<ProcessDefinition> list = processDefinitionQuery.processDefinitionKey("holiday")
				.orderByProcessDefinitionVersion()
				.desc().list();

		for (ProcessDefinition processDefinition : list) {
			System.out.println("流程定义ID: "+processDefinition.getId());
			System.out.println("流程定义Name: "+processDefinition.getName());
			System.out.println("流程定义Key: "+processDefinition.getKey());
			System.out.println("流程定义Version: " + processDefinition.getVersion());

		}
	}

	/**
	 * @description 启动流程实例
	 * @Exception
	 *
	 */

	@Test
	public void startProcessTest() {
		//key值在bpmn图中的id 或者存在数据库act-re-deployment key
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday3");

		System.out.println("流程部署ID:" + processInstance.getDeploymentId());
		System.out.println("流程定义ID:"+processInstance.getProcessDefinitionId());
		System.out.println("流程实例ID:" + processInstance.getId());
		System.out.println("活动ID:" + processInstance.getActivityId());

	}

	@Test
	public void startProcessWithBusinessKeyTest() {
		/**
		 * key值在bpmn图中的id 或者存在数据库act-re-deployment key
		 * 第二个参数就是businessKey 在ru-execution表中可以看到businessKey值
		 * 该key值可以关联业务系统的数据库表字段
		 * 本质是ru-execution表中的businessKey字段存入业务标识
		 */
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday","1001");
		System.out.println("流程部署ID:" + processInstance.getDeploymentId());
		System.out.println("流程定义ID:"+processInstance.getProcessDefinitionId());
		System.out.println("流程实例ID:" + processInstance.getId());
		System.out.println("活动ID:" + processInstance.getActivityId());
		System.out.println("流程的busineessKey"+processInstance.getBusinessKey());

	}
}
