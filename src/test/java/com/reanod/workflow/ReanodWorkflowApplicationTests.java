package com.reanod.workflow;

import junit.framework.TestCase;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReanodWorkflowApplicationTests extends TestCase {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;

	@Autowired
	private RepositoryService repositoryService;

    /**启动流程实例分配任务给个人*/
    @Test
    public void start() {

        String userKey="PTM";//脑补一下这个是从前台传过来的数据
        String processDefinitionKey ="myProcess_1";//每一个流程有对应的一个key这个是某一个流程内固定的写在bpmn内的
        HashMap<String, Object> variables=new HashMap<>();
        variables.put("userKey", userKey);//userKey在上文的流程变量中指定了

        ProcessInstance instance = runtimeService
                .startProcessInstanceByKey(processDefinitionKey,variables);

        System.out.println("流程实例ID:"+instance.getId());
        System.out.println("流程定义ID:"+instance.getProcessDefinitionId());
    }

    /**查询流程实例*/
    @Test
    public void searchProcessInstance(){
        String processDefinitionKey = "oneTaskProcess";
        String processDefinitionId = "f3e28243-8285-11e9-84d6-408d5ccf513c";
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
//                .processDefinitionKey(processDefinitionKey)
                .processInstanceId(processDefinitionId)
                .singleResult();
        System.out.println("流程实例ID:"+pi.getId());
        System.out.println("流程定义ID:"+pi.getProcessDefinitionId());
        //验证是否启动成功
        //通过查询正在运行的流程实例来判断

//        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
//        //根据流程实例ID来查询
//        List<ProcessInstance> runningList = processInstanceQuery.processInstanceId(processDefinitionId).list();
//        System.out.println("根据流程ID查询条数:{}"+runningList.size());
    }

    /**查询当前人的个人任务*/
    @Test
    public void findTask(){
        String assignee = "PTM";
        List<Task> list = taskService.createTaskQuery()//创建任务查询对象
                .taskAssignee(assignee)//指定个人任务查询
                .list();
        if(list!=null && list.size()>0){
            for(Task task:list){
                System.out.println("任务ID:"+task.getId());
                System.out.println("任务名称:"+task.getName());
                System.out.println("任务的创建时间:"+task.getCreateTime());
                System.out.println("任务的办理人:"+task.getAssignee());
                System.out.println("流程实例ID："+task.getProcessInstanceId());
                System.out.println("执行对象ID:"+task.getExecutionId());
                System.out.println("流程定义ID:"+task.getProcessDefinitionId());
            }
        }
    }





	
	


	/**
	 * @description 处理任务 
	 * @Exception   
	 *
	 */
	
	
	@Test
	public void taskComplete() {
		// System.out.println("===============zhangsantaskComplete===============");
		// taskService.complete("f8ba5761-4eff-11ea-9c51-acde48001122");
		System.out.println("======lisi======");
		taskService.complete("0fa9be6d-4f03-11ea-8d7e-acde48001122");
	}

	@Test
	public void getBMPNPackage() {
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		processDefinitionQuery.processDefinitionKey("holiday");
		ProcessDefinition processDefinition = processDefinitionQuery.latestVersion().singleResult();
		String deploymentId = processDefinition.getDeploymentId();

		InputStream pngInput =
				repositoryService.getResourceAsStream(deploymentId, processDefinition.getDiagramResourceName());
		InputStream bpmnInput =
				repositoryService.getResourceAsStream(deploymentId, processDefinition.getResourceName());

		FileOutputStream pngOutput = null;
		FileOutputStream bpmnOutput = null;
		try {
			pngOutput = new FileOutputStream("/Users/daiwenting/Documents/TestCase/test11.png");
			bpmnOutput = new FileOutputStream("/Users/daiwenting/Documents/TestCase/test22.bpmn");

			IOUtils.copy(bpmnInput, bpmnOutput);
			IOUtils.copy(pngInput, pngOutput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				pngOutput.close();
				bpmnOutput.close();
				pngInput.close();
				bpmnInput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}


	}



}
