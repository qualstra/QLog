package com.enoch.model.nc;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.dto.nc.NCDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.Ship;
import com.enoch.model.checklist.inst.QuestionInst;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "non_conf",
uniqueConstraints=
@UniqueConstraint(columnNames={"che_inst_id", "que_inst_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NC implements Transform<NCDTO> {
	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Column(name = "uuid", updatable = false, nullable = false)
	private UUID UUID;
	@Column(name = "che_inst_id", updatable = false, nullable = false)
	private UUID checkId;
	@ManyToOne
	@JoinColumn(name = "que_inst_id")
	private QuestionInst question;
	@ManyToOne
	@JoinColumn(name = "vessel_id")
	private Ship ship;
	@Column(name = "closure_rem", updatable = true, nullable = true, length = 500)
	private String remarks;
	@Column(name = "nc_resp", updatable = false, nullable = false, length = 50 , insertable = true)
	private String responsible;
	@Column(name = "nc_creation_time", updatable = false, nullable = false, insertable = true)
	private Date creationTime;
	@Column(name = "nc_closed_time", updatable = true, nullable = true, insertable = false)
	private Date closureTime;
	@Column(name = "status", updatable = true, nullable = false)
	private Status status;
	@Column(name = "level", updatable = true, nullable = false)
	private String level;
	@Embedded
	private AuditTrail auditTrail;

	@Override
	public NCDTO transform() {
		NCDTO target = new NCDTO();
		BeanUtils.copyProperties(this, target);
		if(question != null) {
			target.setQuestion(question.transform());
		}
		if(ship != null) {
			target.setShip(ship.transform());
		}
		return target;
	}
}