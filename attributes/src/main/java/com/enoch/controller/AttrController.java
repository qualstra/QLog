package com.enoch.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.enoch.model.Attribute;
import com.enoch.model.QAttribute;
import com.enoch.repository.AttributeRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@RestController
@RequestMapping("/attr/{compid}")
public class AttrController {
	@RequestMapping
	public Iterable<Attribute> compAttr(@PathVariable UUID compid) {
		System.out.println("Comp id" + compid);
		BooleanExpression some = QAttribute.attribute.uid.eq(compid);
		System.out.println("/");
		//attrRepo.find
		return attrRepo.findAll(some);
	}

	@RequestMapping("/{typ}")
	public Iterable<Attribute> compVesselAttr(@PathVariable UUID compid,@PathVariable String typ) {
		System.out.println("Comp id" + compid);
		System.out.println("/vessel");
		BooleanExpression some = QAttribute.attribute.uid.eq(compid);
		return attrRepo.findAll(some.and(QAttribute.attribute.entity.eq(typ).and(QAttribute.attribute.entityId.isNull())));
	}


	@RequestMapping("/{typ}/{vid}")
	public Iterable<Attribute> compVesselAttr(@PathVariable UUID compid, @PathVariable String typ, @PathVariable UUID vid) {
		System.out.println("Comp id" + compid);
		System.out.println("/vessel");
		System.out.println("vessel ID" + vid);
		BooleanExpression some = QAttribute.attribute.uid.eq(compid).and(QAttribute.attribute.entity.eq(typ)).and(QAttribute.attribute.entityId.eq(vid));
		return attrRepo.findAll(some);
	}

	@Autowired
	AttributeRepository attrRepo;

	
	@PostMapping("/insert")
	public Attribute insertAttrib(@PathVariable UUID compid,@RequestBody Attribute attr) {
		if(attr.getId() != null) {
			return attrRepo.save(attr);
		} else {
			return attrRepo.insert(attr);	
		}
	}
	@PostMapping("/delete")
	public void deleteAttrib(@PathVariable UUID compid,@RequestBody Attribute attr) {
		if(attr.getId() != null) {
			attrRepo.delete(attr);
		} else {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);	
		}
	}

}
