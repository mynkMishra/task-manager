package com.practice.task_manager.controller;

import java.text.ParseException;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.task_manager.dto.CreateTaskDTO;
import com.practice.task_manager.dto.ErrorResponseDTO;
import com.practice.task_manager.dto.TaskResponseDTO;
import com.practice.task_manager.dto.UpdateTaskDTO;
import com.practice.task_manager.entities.TaskEntity;
import com.practice.task_manager.services.NoteService;
import com.practice.task_manager.services.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	private final TaskService taskService;
	private final NoteService noteService;
	private final ModelMapper modelMapper;

	public TaskController(TaskService taskService, NoteService noteService, ModelMapper modelMapper) {
		this.taskService = taskService;
		this.noteService = noteService;
		this.modelMapper = modelMapper;
	}

	@GetMapping("")
	public ResponseEntity<ArrayList<TaskEntity>> getTasks() {
		var tasks = this.taskService.getTaks();
		return ResponseEntity.ok(tasks);
	}

	@GetMapping("/{taskId}")
	public ResponseEntity<TaskResponseDTO> getTask(@PathVariable("taskId") int taskId) {
		var task = this.taskService.getTaskById(taskId);
		var notes = this.noteService.getNotesForTask(taskId);

		if (task == null) {
			return ResponseEntity.notFound().build();
		}

		var taskReponse = modelMapper.map(task, TaskResponseDTO.class);
		taskReponse.setNotes(notes);
		return ResponseEntity.ok(taskReponse);
	}

	@PatchMapping("/{taskId}")
	public ResponseEntity<TaskEntity> updateTask(@PathVariable("taskId") int taskId, @RequestBody UpdateTaskDTO taskDTO)
			throws ParseException {
		var task = taskService.updateTasks(taskId, taskDTO.getDescription(), taskDTO.getDeadline(),
				taskDTO.getCompleted());
		if (task == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(task);
	}

	@PostMapping("")
	public ResponseEntity<TaskEntity> addTask(@RequestBody CreateTaskDTO body) throws ParseException {
		var task = taskService.addTask(body.getTitle(), body.getDescription(), body.getDeadline());

		return ResponseEntity.ok(task);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleErrors(Exception e) {
		if (e instanceof ParseException) {
			return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid Date Format"));
		}

		return ResponseEntity.internalServerError().body(new ErrorResponseDTO("Internal Server Error"));
	}
}
