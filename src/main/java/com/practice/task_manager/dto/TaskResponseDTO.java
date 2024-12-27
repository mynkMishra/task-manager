package com.practice.task_manager.dto;

import java.util.Date;
import java.util.List;

import com.practice.task_manager.entities.NoteEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {
	
	private int id;
	private String title;
	private String description;
	private Date deadline;
	private boolean completed;
	private List<NoteEntity> notes;
}
