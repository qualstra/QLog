package com.enoch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "COMP_RANK",
uniqueConstraints=
@UniqueConstraint(columnNames={"COMPANYID", "RANK_CODE"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRank {

	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "COMPANYID", nullable = false)
	private Company company;
	@ManyToOne
	@JoinColumn(name = "RANK_CODE", nullable = false)
	private Rank rank;
}
