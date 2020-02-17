package com.reanod.workflow.deploy;

import junit.framework.TestCase;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
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
import java.util.zip.ZipInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeployTests extends TestCase {

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
				.addClasspathResource("processes/sampleProcess.bpmn")
				// .addClasspathResource("diagram/holiday2.png")
				.name("sample流程")
				.deploy();
		System.out.println("deploymentName:" + deployment.getName());
		System.out.println("deploymentId:" + deployment.getId());
		System.out.println("========================================");
		System.out.println("========================================");
	}


	@Test
	public void deploymentWithListenerTest() {
		Deployment deployment = repositoryService.createDeployment()
				.addClasspathResource("diagram/holiday3.bpmn")
				.addClasspathResource("diagram/holiday3.png")
				.name("贷款流程")
				.deploy();
		System.out.println("deploymentName:" + deployment.getName());
		System.out.println("deploymentId:" + deployment.getId());
		System.out.println("========================================");
		System.out.println("========================================");
	}

	/**
	 * @description 通过ZIP包部署流程  zip：bpmn+png
	 * @Exception
	 */


	@Test
	public void deploymentZIPTest() {
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("diagram/holidayBPMN.zip");
		ZipInputStream zipInputStream = new ZipInputStream(stream);
		Deployment deployment = repositoryService.createDeployment()
				.addZipInputStream(zipInputStream)
				.deploy();

		System.out.println("============ZIP部署====================");
		System.out.println("deploymentName:" + deployment.getName());
		System.out.println("deploymentId:" + deployment.getId());
		System.out.println("========================================");

	}

	/**
	 * @description 获取部署的png和bpmn内容 存储成文件
	 * @Exception
	 */

	@Test
	public void getBMPNPackageFile() {
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
