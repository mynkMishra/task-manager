package com.practice.task_manager.entities;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskEntity {
	private int id;
	private String title;
	private String description;
	private Date deadline;
	private boolean completed;
}
