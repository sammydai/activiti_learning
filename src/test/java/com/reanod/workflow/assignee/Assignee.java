package com.reanod.workflow.assignee;

import junit.framework.TestCase;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Assignee extends TestCase {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ProcessEngineConfiguration processEngineConfiguration;

	@Autowired
	private RepositoryService repositoryService;

	/**
	 * @description 设置assignee的值 传入map参数
	 * @Exception   
	 *
	 */
	
	
	@Test
	public void assigneeTest() {
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("assignee0", "zhangsan0");
		hashMap.put("assignee1", "lisi1");
		hashMap.put("assignee2", "wangwu2");

		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday2", hashMap);
		System.out.println(processInstance.getName());

	}


}
