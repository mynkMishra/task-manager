package com.practice.task_manager.services;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.practice.task_manager.entities.TaskEntity;

@Service
public class TaskService {
	
	private int taskId = 1;
	private ArrayList<TaskEntity> tasks = new ArrayList<>();
	private SimpleDateFormat deadlineFormatter = new SimpleDateFormat("yyyy-MM-dd"); 
	
	public TaskEntity addTask(String title, String desc, String deadline) throws ParseException {
		TaskEntity newTask = new TaskEntity();
		newTask.setId(taskId);
		newTask.setTitle(title);
		newTask.setDescription(desc);
		newTask.setDeadline(deadlineFormatter.parse(deadline));
		newTask.setCompleted(false);
		tasks.add(newTask);
		taskId++;
		return newTask;
	}
	
	public TaskEntity getTaskById(int id) {
		var task = tasks.stream().filter((t) -> t.getId() == id).findFirst();
		
		if(task.isEmpty()) {
			return null;
		}
		
		return task.get();
	}
	
	public ArrayList<TaskEntity> getTaks(){
		return tasks;
	}
	
	public TaskEntity updateTasks(int id, String description, String deadline, Boolean completed) throws ParseException {
		
		TaskEntity task = getTaskById(id);
		if(task == null) {
			return null;
		}
		
		if(description != null) {
			task.setDescription(description);
		}
		
		if(deadline != null) {
			task.setDeadline(deadlineFormatter.parse(deadline));
		}
		
		if(completed != null) {
			task.setCompleted(completed);
		}
		
		return task;  
	}
}
