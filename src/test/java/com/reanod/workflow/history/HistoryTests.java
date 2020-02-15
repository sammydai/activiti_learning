package com.reanod.workflow.history;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
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
public class HistoryTests {

	@Autowired
	private HistoryService historyService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ProcessEngineConfiguration processEngineConfiguration;

	@Autowired
	private RepositoryService repositoryService;


	@Test
	public void historyQuery() {
		HistoricActivityInstanceQuery historicActivityInstanceQuery =
				historyService.createHistoricActivityInstanceQuery();
		historicActivityInstanceQuery.processInstanceId("f8b415cd-4eff-11ea-9c51-acde48001122");
		List<HistoricActivityInstance> list = historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime().asc().list();
		for (HistoricActivityInstance instance : list) {
			System.out.println(instance.getActivityId());
			System.out.println(instance.getActivityName());
			System.out.println(instance.getProcessDefinitionId());
			System.out.println(instance.getProcessInstanceId());
			System.out.println(instance.getAssignee());
			System.out.println("=====================================");
		}
	}

}
