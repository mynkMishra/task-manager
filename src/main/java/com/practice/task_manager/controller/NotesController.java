package com.practice.task_manager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.practice.task_manager.dto.CreateNoteDTO;
import com.practice.task_manager.dto.CreateNoteResponseDTO;
import com.practice.task_manager.entities.NoteEntity;
import com.practice.task_manager.services.NoteService;

@RestController
@RequestMapping("/tasks/{taskId}/notes")
public class NotesController {
	private final NoteService noteService; 
	
	public NotesController(NoteService noteService) {
		this.noteService = noteService;
	}
	
	@GetMapping("")
	public ResponseEntity<List<NoteEntity>> getNotes(@PathVariable("taskId") int taskId) {
		var notes = noteService.getNotesForTask(taskId);
		
		return ResponseEntity.ok(notes);
	}
	
	@PostMapping("")
	public ResponseEntity<CreateNoteResponseDTO> addNote(
			@PathVariable("taskId") Integer taskId, 
			@RequestBody CreateNoteDTO noteDto){
		var note = noteService.addNoteForTask(taskId, noteDto.getTitle(), noteDto.getBody());
		
		return ResponseEntity.ok(new CreateNoteResponseDTO(taskId, note));
	}
}
