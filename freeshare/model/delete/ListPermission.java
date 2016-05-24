package com.free4lab.freeshare.model.delete;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the list_permission database table.
 * 
 */
@Deprecated
@Entity
@Table(name="list_permission")
public class ListPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="list_id")
	private Integer listId;

	@Column(name="team_id")
	private Integer teamId;
	
	@Column(name="type")
	private String type;
	public static final String TYPE_READONLY = "readonly";
	public static final String TYPE_READ_WRITE = "read-write";
	
	// Constructors
    public ListPermission() {
    }

    public ListPermission(Integer listId, Integer teamId, String type) {
		this.listId = listId;
		this.teamId = teamId;
		this.type = type;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getListId() {
		return this.listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	public Integer getTeamId() {
		return this.teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}