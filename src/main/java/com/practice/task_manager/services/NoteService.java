package com.practice.task_manager.services;

import org.springframework.stereotype.Service;

import com.practice.task_manager.entities.NoteEntity;
import com.practice.task_manager.entities.TaskEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoteService {
	private TaskService taskService;
	private Map<Integer, TaskNotesHolder> taskNotesHolder = new HashMap<Integer, TaskNotesHolder>();
	
	public NoteService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	class TaskNotesHolder{
		protected int notesId = 1;
		protected ArrayList<NoteEntity> notes = new ArrayList<>();
	}
	
	public List<NoteEntity> getNotesForTask(int taskId){
		TaskEntity task = taskService.getTaskById(taskId);
		
		if(task == null) {
			return null;
		}
		
		if(taskNotesHolder.get(taskId) == null) {
			taskNotesHolder.put(taskId, new TaskNotesHolder());
		}
		
		return taskNotesHolder.get(taskId).notes;
	}
	
	public NoteEntity addNoteForTask(int taskId, String noteTitle, String noteBody) {
		TaskEntity task = taskService.getTaskById(taskId);
		
		if(task == null) {
			return null;
		}
		
		if(taskNotesHolder.get(taskId) == null) {
			taskNotesHolder.put(taskId, new TaskNotesHolder());
		}
		
		TaskNotesHolder taskNoteHolder = taskNotesHolder.get(taskId);
		NoteEntity note = new NoteEntity();
		note.setId(taskNoteHolder.notesId);
		note.setTitle(noteTitle);
		note.setBody(noteBody);
		taskNoteHolder.notes.add(note);
		taskNoteHolder.notesId++;
		
		return note;
		
	}
}
