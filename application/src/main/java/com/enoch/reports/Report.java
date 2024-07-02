package com.enoch.reports;

import org.thymeleaf.context.Context;

import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.vo.CheckListInstVO;
import com.google.common.base.Function;

public interface Report {
	public Function<Context, String> getReport(CheckListInstVO checkListInstVO);

	public Function<Context, String> getReport(CheckListInstDTO checkListInst);
	public String getReportName();
}
