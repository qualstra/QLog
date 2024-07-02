package com.enoch.model.checklist;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "SEC_QUE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionQuestion {
	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	private Long id;
	@ManyToOne
	@JoinColumn(name="SEC_ID")
	private Section section;
	@ManyToOne
	@JoinColumn(name="QUE_ID")
	private Question question;
	
	@Column(name = "enabled")
	private boolean enabled;
	
}
