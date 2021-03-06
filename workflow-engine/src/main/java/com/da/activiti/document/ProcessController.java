package com.da.activiti.document;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.da.activiti.model.Response;
import com.da.activiti.model.document.ProcessInfo;
import com.da.activiti.web.BaseController;

@Controller
@RequestMapping("/process")
public class ProcessController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(ProcessController.class);

	@Autowired
	ProcessService processService;

	@RequestMapping(value = "/saveProcess", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Response> post(@ModelAttribute ProcessInfo processInfo, BindingResult result,
			final RedirectAttributes redirectAttributes, HttpServletRequest request, ModelMap model) {
		
		String processId = processService.createProcess(processInfo);
		String msg = "Process id : "+ processId +" -- Process Created Successfully ";
		Response<String> res = new Response<String>(true, msg);
		res.setData(processId);
		return new ResponseEntity<Response>(res, HttpStatus.OK);

	}

	@Override
	@ModelAttribute
	public void addModelInfo(ModelMap model, HttpServletRequest request) {
		super.addModelInfo(model, request);
		List<ProcessInfo> processList = processService.listAllProcesses();
		/*
		 * ObjectMapper mapper = new ObjectMapper(); model.addAttribute("json",
		 * mapper.writeValueAsString(files));
		 */
		model.addAttribute("processList", processList);
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@RequestMapping(value = "/Processlist.htm", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Response> getPeocessList() {
		List<ProcessInfo> processList = processService.listAllProcesses();
		Response<List<ProcessInfo>> res = new Response<List<ProcessInfo>>(true, "Alert acknowledged");
		res.setData(processList);
		return new ResponseEntity<Response>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/createProcess.htm", method = RequestMethod.GET)
	public String addDocumentForm(ModelMap model) {
		model.addAttribute("processInfo", new ProcessInfo());
		return "document/createProcessView";
	}

	@RequestMapping(value = "/DynProcesslist.htm", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Response> getDynProcessList() {
		List<Map<String, Object>> processList = processService.allProcesses();
		Response<List<Map<String, Object>>> res = new Response<List<Map<String, Object>>>(true, "Alert acknowledged");
		res.setData(processList);
		return new ResponseEntity<Response>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/ProcesslistByTaskMappingId.htm", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Response> getDynProcessList(@RequestParam int mappingId) {
		List<Map<String, Object>> processList = processService.processesListByTraskMappingId(mappingId);
		Response<List<Map<String, Object>>> res = new Response<List<Map<String, Object>>>(true, "Alert acknowledged");
		res.setData(processList);
		return new ResponseEntity<Response>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/TaskMappingList.htm", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Response> getTaskMapingList() {
		List<Map<String, Object>> processList = processService.processTaskMappingList();
		Response<List<Map<String, Object>>> res = new Response<List<Map<String, Object>>>(true, "Alert acknowledged");
		res.setData(processList);
		return new ResponseEntity<Response>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/TaskMappingListByTaskId.htm", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Response> getTaskMapingList(@RequestParam int mappingId) {
		List<Map<String, Object>> processList = processService.processesTraskMappingListByTaskId(mappingId);
		Response<List<Map<String, Object>>> res = new Response<List<Map<String, Object>>>(true, "Alert acknowledged");
		res.setData(processList);
		return new ResponseEntity<Response>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/TaskList.htm", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Response> getTaskList() {
		List<Map<String, Object>> processList = processService.taskList();
		Response<List<Map<String, Object>>> res = new Response<List<Map<String, Object>>>(true, "Alert acknowledged");
		res.setData(processList);
		return new ResponseEntity<Response>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/TaskListById.htm", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Response> getTaskList(@RequestParam int taskId) {
		List<Map<String, Object>> processList = processService.taskListById(taskId);
		Response<List<Map<String, Object>>> res = new Response<List<Map<String, Object>>>(true, "Alert acknowledged");
		res.setData(processList);
		return new ResponseEntity<Response>(res, HttpStatus.OK);

	}
}
