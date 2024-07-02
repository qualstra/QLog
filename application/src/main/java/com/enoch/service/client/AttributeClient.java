package com.enoch.service.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.enoch.attr.model.Attribute;

@FeignClient("attrservice")
public interface AttributeClient{
	@RequestMapping("/attr/{compid}")
	public Iterable<Attribute> getCompanyAttributes(@PathVariable UUID compid) ;
	
	default public String getStringAttribute(@PathVariable UUID compid, @PathVariable String attrcode) {
		return "Fleet Manager";
	}
	
	@RequestMapping("/attr/{compid}/{typ}")
	public Iterable<Attribute> getCompanyAttributesFor(@PathVariable UUID compid, @PathVariable String typ) ;


	@RequestMapping("/attr/{compid}/{typ}/{vid}")
	public Iterable<Attribute> getCompanyAttributesFor(@PathVariable UUID compid,  @PathVariable String typ, @PathVariable UUID vid);


	@RequestMapping(value = "/attr/{compid}/insert", method = RequestMethod.POST,consumes = "application/json")
	public Attribute insertAttrib(@PathVariable UUID compid, Attribute attr);
	
	@RequestMapping(value = "/attr/{compid}/delete", method = RequestMethod.POST,consumes = "application/json")
	public Attribute removeAttrib(@PathVariable UUID compid, Attribute attr);

}