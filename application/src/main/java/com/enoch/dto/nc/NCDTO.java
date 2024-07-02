package com.enoch.dto.nc;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.enoch.Attachable;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.nc.NC;
import com.enoch.model.nc.Status;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NCDTO implements Transform<NC> , Attachable{

	private Long id;
	private UUID UUID;
	private UUID checkId;
	private QuestionInstDTO question;
	private ShipDTO ship;
	private String remarks;
	private String responsible;
	private Date creationTime;
	private Date closureTime;
	private Status status = Status.OPEN;
	private String level;
	private AuditTrail auditTrail;

	@Override
	public NC transform() {
		NC target = new NC();
		BeanUtils.copyProperties(this, target);
		if (question != null) {
			target.setQuestion(question.transform());
		}
		if (ship != null) {
			target.setShip(ship.transform());
		}
		return target;
	}
}