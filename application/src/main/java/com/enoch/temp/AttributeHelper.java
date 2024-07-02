package com.enoch.temp;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections4.map.HashedMap;

import com.enoch.attr.model.Attribute;

public class AttributeHelper {

	public static Collection<Attribute> init(UUID compId, UUID entId) {
		Map<String, Attribute> attributeMap = new HashedMap<String, Attribute>();

//		
		attributeMap.put("Dateg", getattribute(null, compId, "vessel", entId, null, "Date", "string",
				"6/11/2020", "", "General", 1, 1,"Dateg", "Date"));
//		
		attributeMap.put("Placeg", getattribute(null, compId, "vessel", entId, null,"Place", "string",
				"MbPT ID-12", "", "General", 2, 1, "Placeg", "Place"));
		attributeMap.put("Attn", getattribute(null, compId, "vessel", entId, null, "Attendees", "string",
				"Lokanath Tripathy,Rajesh Seth", "", "General", 3, 1, "Attn", "Attendees"));
		attributeMap.put("Vess_Name", getattribute(null, compId, "vessel", entId, null, "Vessel Name", "string",
				"GREATSHIP DIPTI", "", "General", 4, 1, "Vess_Name", "Vessel_Name"));
		attributeMap.put("IMO", getattribute(null, compId, "vessel", entId, null, "IMO number", "string", "9340532", "",
				"General", 5, 1, "IMO", "IMO_number"));
		attributeMap.put("Type", getattribute(null, compId, "vessel", entId, null, "Type", "string",
				"Platform Support Vessel/Other Cargo Ship", "", "General", 6, 1, "Type", "Type"));
		attributeMap.put("POG", getattribute(null, compId, "vessel", entId, null, "Port of Registry", "string", "Mumbai",
				"", "General", 7, 1, "POG", "Port of Registry"));
		attributeMap.put("Oprt", getattribute(null, compId, "vessel", entId, null, "Operator", "string",
				"Greatship(India)LTD", "", "General", 8, 1, "Oprt", "Operator"));
		
		attributeMap.put("YOB", getattribute(null, compId, "vessel", entId, null, "Year Of Built", "string", "2005", "",
				"General", 9, 1, "YOB", "Year Of Built"));
		attributeMap.put("TLSA", getattribute(null, compId, "vessel", entId, null, "Total LSA", "string", "30", "",
				"General", 10, 1, "TLSA", "Total LSA"));
		attributeMap.put("TACC", getattribute(null, compId, "vessel", entId, null, "Total Accomodation", "string", "30",
				"", "General", 11, 1, "TACC", "Total Accommodation"));
		attributeMap.put("LOA", getattribute(null, compId, "vessel", entId, null, "Length OA", "string", "73.6", "",
				"General", 12, 1, "LOA", "Length OA"));
		attributeMap.put("SD", getattribute(null, compId, "vessel", entId, null, "Summer Draft", "string", "5.82m", "",
				"General", 13, 1, "SD", "Summer Draft"));
		attributeMap.put("CDS", getattribute(null, compId, "vessel", entId, null, "Clear Desk Space", "string", "680sq.m",
				"", "General", 14, 1, "CDS", "Clear Desk Space"));
		attributeMap.put("LBP", getattribute(null, compId, "vessel", entId, null, "Length BP", "string", "68.3", "",
				"General", 15, 1, "LBP", "Length BP"));
		attributeMap.put("SDisp", getattribute(null, compId, "vessel", entId, null, "Summer Displt", "string", "4984.4 MT",
				"", "General", 16, 1, "SDisp", "Summer Displt"));
		attributeMap.put("GT", getattribute(null, compId, "vessel", entId, null, "GT", "string", "2263", "", "General", 17,
				1, "GT", "GT"));
		attributeMap.put("BMld", getattribute(null, compId, "vessel", entId, null, "Breadth Mld", "string", "16.0m", "",
				"General", 18, 1, "BMld", "Breadth Mld"));
		attributeMap.put("MDW", getattribute(null, compId, "vessel", entId, null, "Max DeadWeight", "string", "3228 MT",
				"", "General", 19, 1, "MDW", "Max DeadWeight"));
		attributeMap.put("NT", getattribute(null, compId, "vessel", entId, null, "NT", "string", "1120", "", "General", 20,
				1, "NT", "NT"));
		attributeMap.put("DMld", getattribute(null, compId, "vessel", entId, null, "Depth Mld", "string", "7.0m", "",
				"General", 21, 1, "DMld", "Depth Mld"));
		attributeMap.put("RFC", getattribute(null, compId, "vessel", entId, null, "Raised Forecast", "string", "Yes", "",
				"General", 22, 1, "RFC", "Raised Forecast"));
		attributeMap.put("BBy", getattribute(null, compId, "vessel", entId, null, "Built By", "string",
				"AKER YARDS,NORWAY", "", "General", 23, 1, "BBy", "Built By"));
		attributeMap.put("End", getattribute(null, compId, "vessel", entId, null, "Endurance", "string", "80 Days", "",
				"General", 24, 1, "End", "Endurance"));
		attributeMap.put("End1", getattribute(null, compId, "vessel", entId, null, "Endurance1", "string", "75 Days", "",
				"General", 25, 1, "End1", "Endurance"));
		attributeMap.put("MDC", getattribute(null, compId, "vessel", entId, null, "Diesel Capacity", "string", "1000", "",
				"General", 26, 1, "MDC", "Marine Diesel Capacity"));
		attributeMap.put("PWC", getattribute(null, compId, "vessel", entId, null, "Water Capacity", "string", "455", "",
				"General", 27, 1, "PWC", "Potable Water Capacity"));
		attributeMap.put("CF24hF",
				getattribute(null, compId, "vessel", entId, null, "Diesel Consumption/24hr Full", "string",
						"16-18 tons/day at 13.5knots", "", "General", 28, 1, "CF24hF", "DieselConsumption 24hr.Full"));
		attributeMap.put("CF24hE", getattribute(null, compId, "vessel", entId, null, "Diesel Consumption/24hr Eco",
				"string", "7-10 tons/day at 10knots", "", "General", 29, 1, "CF24hE", "Diesel Consumption 24hr.Eco"));
		attributeMap.put("DONB", getattribute(null, compId, "vessel", entId, null, "Diesel OnBoard", "string", "157", "",
				"General", 30, 1, "DONB", "Diesel OnBoard"));
		attributeMap.put("WONB", getattribute(null, compId, "vessel", entId, null, "Water OnBoard", "string", "128", "",
				"General", 31, 1, "WONB", "Water OnBoard"));
		attributeMap.put("WCF24hF", getattribute(null, compId, "vessel", entId, null, "Water Consumption/24hr Full",
				"string", "-", "", "General", 32, 1, "WCF24hF", "Water Consumption 24hr.Full"));
		attributeMap.put("WCF24hE", getattribute(null, compId, "vessel", entId, null, "Water Consumption/24hr Eco.",
				"string", "06", "", "General", 33, 1, "WCF24hE", "Water Consumption 24hr.Eco"));
		attributeMap.put("MName", getattribute(null, compId, "vessel", entId, null, "Master Name", "string",
				"CAPT PANKRAJ DUBEY", "", "General", 34, 1, "MName", "Master Name"));
		
		attributeMap.put("JNo", getattribute(null, compId, "vessel", entId, null, "Job No", "string",
				"--", "", "General", 35, 1, "JNo", "Job No"));
		
		attributeMap.put("Clnt", getattribute(null, compId, "vessel", entId, null, "Client", "string",
				"--", "", "General", 36, 1, "Clnt", "Client"));
		
		attributeMap.put("ClsBy", getattribute(null, compId, "vessel", entId, null, "Classes By/On", "string",
				"IRS / 15.05.2019", "", "Vessel_certification", 1, 2, "ClsBy", "Classed By / On"));
		attributeMap.put("Nota", getattribute(null, compId, "vessel", entId, null, "Notation", "string",
				"IY , SYJ,DP(2),AGNI 1(2400cum/hr),BWE(s)", "", "Vessel_certification", 2, 2, "Nota", "Notation"));
		attributeMap.put("val", getattribute(null, compId, "vessel", entId, null, "Validity", "string", "07/10/2020",
				"", "Vessel_certification", 3, 2, "val", "Validity"));
		attributeMap.put("Ldd_OnAt", getattribute(null, compId, "vessel", entId, null, "Last DryDock ON/At", "string",
				"10.03.2019,Kakinada", "", "Vessel_certification", 4, 2, "Ldd_OnAt", "Last Dry-dock On/At"));
		attributeMap.put("OS", getattribute(null, compId, "vessel", entId, null, "O/S List", "string", "--", "",
				"Vessel_certification", 5, 2, "OS", "O/S List"));
		attributeMap.put("RI_ByOn", getattribute(null, compId, "vessel", entId, null, "Registry Issued By/On", "string",
				"MMD/26.05.2017", "", "Vessel_certification", 6, 2, "RI_ByOn", "Registry Issued By/On"));
		attributeMap.put("RI_ByOn_Val", getattribute(null, compId, "vessel", entId, null, "Registry Validity",
				"string", "--", "", "Vessel_certification", 7, 2, "RI_ByOn_Val", "Registry Validity"));
		attributeMap.put("Ton_ByOn", getattribute(null, compId, "vessel", entId, null, "Tonnage Issued By/On", "string",
				"MMD/26.05.2017", "", "Vessel_certification", 8, 2, "Ton_ByOn", "Tonnage Issued By/On"));
		attributeMap.put("Ton_ByOn_Val",
				getattribute(null, compId, "vessel", entId, null, "Tonnage Validity", "string",
						"MMD/12.12.2008", "", "Vessel_certification", 9, 2, "Ton_ByOn_Val",
						"Tonnage Validity"));
		attributeMap.put("LL_ByOn", getattribute(null, compId, "vessel", entId, null, "Loadline Issued By/On", "string",
				"MMD/07.10.2020", "", "Vessel_certification", 10, 2, "LL_ByOn", "Loadline Issued By/On"));
		attributeMap.put("LL_ByOn_Val",
				getattribute(null, compId, "vessel", entId, null, "Loadline Validity", "string", "12.05.2025",
						"", "Vessel_certification", 11, 2, "LL_ByOn_Val", "Loadline Validity"));
		attributeMap.put("SC_ByOn",
				getattribute(null, compId, "vessel", entId, null, "Safety Construction By/On", "string", "IRS/07.10.2020",
						"", "Vessel_certification", 12, 2, "SC_ByOn", "Safety Construction By/On"));
		attributeMap.put("SC_ByOnVal",
				getattribute(null, compId, "vessel", entId, null, "Safety Construction Validity", "string",
						"12.05.2025", "", "Vessel_certification", 13, 2, "SC_ByOnVal",
						"Safety Construction Validity"));
		attributeMap.put("SE_ByOn", getattribute(null, compId, "vessel", entId, null, "Safety Equipment By/On", "string",
				"IRS/07.10.2020", "", "Vessel_certification", 14, 2, "SE_ByOn", "Safety Equipments By/On"));
		attributeMap.put("SE_ByOn_Val",
				getattribute(null, compId, "vessel", entId, null, "Safety Equipment Validity", "string",
						"12.05.2025", "", "Vessel_certification", 15, 2, "SE_ByOn_Val",
						"Safety Equipment Validity"));
		attributeMap.put("SR_ByOn", getattribute(null, compId, "vessel", entId, null, "Safety Radio By/On", "string",
				"IRS/01.04.2020", "", "Vessel_certification", 16, 2, "SR_ByOn", "Safety Radio By/On"));
		attributeMap.put("SR_ByOn_Val",
				getattribute(null, compId, "vessel", entId, null, "Safety Radio Validity", "string", "31.12.2021",
						"", "Vessel_certification", 17, 2, "SR_ByOn_Val", "Safety Radio Validity"));
		attributeMap.put("RL_ByOn", getattribute(null, compId, "vessel", entId, null, "Radio License By/On", "string",
				"GOI/23.05.2020", "", "Vessel_certification", 18, 2, "RL_ByOn", "Radio License By/On"));
		attributeMap.put("RL_ByOn_Val",
				getattribute(null, compId, "vessel", entId, null, "Radio License Validity", "string", "31.12.2021",
						"", "Vessel_certification", 19, 2, "RL_ByOn_Val", "Radio License Validity"));
		attributeMap.put("IOP_ByOn", getattribute(null, compId, "vessel", entId, null, "IOPP Issued By/On", "string",
				"IRS/07.10.2020", "", "Vessel_certification", 20, 2, "IOP_ByOn", "IOPP Issued By/On"));
		attributeMap.put("IOP_ByOn_Val", getattribute(null, compId, "vessel", entId, null, "IOPP Issued Validity", "string",
				"12.05.2025", "", "Vessel_certification", 21, 2, "IOP_ByOn_Val", "IOPP Issued Validity"));
		attributeMap.put("SSC_ByOn",
				getattribute(null, compId, "vessel", entId, null, "Ship Sanitation Control Certificate By/On", "string",
						"GOI/23.05.2020", "", "Vessel_certification", 22, 2, "SSC_ByOn",
						"Ship Sanitation Control Exemption Certificate By/On"));
		attributeMap.put("SSC_ByOn_Val",
				getattribute(null, compId, "vessel", entId, null, "Ship Sanitation Control Certificate Validity",
						"string", "20.11.2020", "", "Vessel_certification", 23, 2, "SSC_ByOn_Val",
						"Ship Sanitation Control Exemption Certificate Validity"));
		attributeMap.put("SMC_ByOn",
				getattribute(null, compId, "vessel", entId, null, "Ship Management Certificate By/On", "string",
						"DG SHipping/27.04.2018", "", "Vessel_certification", 24, 2, "SMC_ByOn",
						"Ship Management Certificate By/On"));
		attributeMap.put("SMC_ByOn_Val",
				getattribute(null, compId, "vessel", entId, null, "Ship Management Certificate Validity", "string",
						"11.03.2023", "", "Vessel_certification", 25, 2, "SMC_ByOn_Val",
						"Ship Management Certificate Validity"));
		attributeMap.put("SMan_ByOn", getattribute(null, compId, "vessel", entId, null, "Safe Manning By/On", "string",
				"MMD/23.04.2019", "", "Vessel_certification", 26, 2, "SMan_ByOn", "Safe Manning By/On"));
		attributeMap.put("SBA_ByOn",
				getattribute(null, compId, "vessel", entId, null, "Stability book approved By/On", "string",
						"IRS/17.03.2008", "", "Vessel_certification", 27, 2, "SBA_ByOn",
						"Stability Book Approved By/On"));

		attributeMap.put("MAT", getattribute(null, compId, "vessel", entId, null, "Make and Type", "string",
				"KRMB-9 Bergen Diesel A.S. Norway Bergen", "", "Main_Engine", 1, 3, "MAT", "Make and Type"));
		attributeMap.put("TNo", getattribute(null, compId, "vessel", entId, null, "Total no", "string", "Two", "",
				"Main_Engine", 2, 3, "TNo", "Total No."));
		attributeMap.put("TCKW", getattribute(null, compId, "vessel", entId, null, "Total Continuous KW", "string",
				"2500 KW", "", "Main_Engine", 3, 3, "TCKW", "Total Continuous KW"));
		attributeMap.put("TCKWAT", getattribute(null, compId, "vessel", entId, null, "Total Continuous KW @", "string",
				"825", "", "Main_Engine", 4, 3, "TCKWAT", "Total Continuous KW @"));
		attributeMap.put("MS", getattribute(null, compId, "vessel", entId, null, "Max Speed", "string", "14", "",
				"Main_Engine", 5, 3, "MS", "Max_Speed"));
		attributeMap.put("Thrus", getattribute(null, compId, "vessel", entId, null, "Thruster", "string",
				"Bow-Tow in No.,Tunnel Stern-Tow in No.,Tunnel", "", "Main_Engine", 6, 3, "Thrus", "Thruster(s)"));
		attributeMap.put("TThrus", getattribute(null, compId, "vessel", entId, null, "Total Thruster", "string",
				"BOW: 2 *590KW Stern: 2 *265KW", "", "Main_Engine", 7, 3, "TThrus", "Total Thrust"));
		attributeMap.put("MGs", getattribute(null, compId, "vessel", entId, null, "Main Generator", "string", "Two", "",
				"Main_Engine", 8, 3, "MGs", "Main Generator(s)"));
		attributeMap.put("MGsO", getattribute(null, compId, "vessel", entId, null, "Main Generator Output", "string",
				"2 * 265KW", "", "Main_Engine", 9, 3, "MGsO", "Main Generator(s) Output"));
		attributeMap.put("EGs", getattribute(null, compId, "vessel", entId, null, "Emergency Generator", "string", "One",
				"", "Main_Engine", 10, 3, "EGs", "Emergency Generator(s)"));
		attributeMap.put("EGsO", getattribute(null, compId, "vessel", entId, null, "Emergency Generator Output", "string",
				"48KW @1800RPM", "", "Main_Engine", 11, 3, "EGsO", "Emergency Generator(s) Output"));
		attributeMap.put("SGs", getattribute(null, compId, "vessel", entId, null, "Shaft Generator", "string", "Two", "",
				"Main_Engine", 12, 3, "SGs", "Shaft Generators"));
		attributeMap.put("SGsO", getattribute(null, compId, "vessel", entId, null, "Shaft Generator Output", "string",
				"2 * 1280KW", "", "Main_Engine", 13, 3, "SGsO", "Shaft Generator Output"));

		attributeMap.put("Rad1_mk", getattribute(null, compId, "vessel", entId, null, "Rador1 Make", "string", "Furuno",
				"", "NAV_EQP", 1, 4, "Rad1_mk", "Radar1_Make"));
		attributeMap.put("Rad2_mk", getattribute(null, compId, "vessel", entId, null, "Rador2 Make", "string", "Furuno",
				"", "NAV_EQP", 2, 4, "Rad2_mk", "Radar2_Make"));
		attributeMap.put("GPS_Make", getattribute(null, compId, "vessel", entId, null, "GPS Make", "string", "Furuno", "",
				"NAV_EQP", 3, 4, "GPS_Make", "GPS_Make"));
		attributeMap.put("Echo_mk", getattribute(null, compId, "vessel", entId, null, "Echo Sounder Make", "string",
				"Skipper", "", "NAV_EQP", 4, 4, "Echo_mk", "ECHO_sounder_Make"));
		attributeMap.put("Gyro_comp", getattribute(null, compId, "vessel", entId, null, "Gyro Compass Make", "string",
				"Anschutz & MT Meridian", "", "NAV_EQP", 5, 4, "Gyro_comp", "Gyro_Compass_Make"));
		attributeMap.put("AP_mk", getattribute(null, compId, "vessel", entId, null, "Auto pilot Make", "string",
				"Robertson", "", "NAV_EQP", 6, 4, "AP_mk", "Auto_pilot_Make"));
		attributeMap.put("MagComp_mk", getattribute(null, compId, "vessel", entId, null, "Magnetic Compass Make", "string",
				"cassens & path GmBH", "", "NAV_EQP", 7, 4, "MagComp_mk", "Magnetic_Compass_Make"));
		attributeMap.put("AIS_mk", getattribute(null, compId, "vessel", entId, null, "AIS Make", "string", "Furuno", "",
				"NAV_EQP", 8, 4, "AIS_mk", "AIS_Make"));
		attributeMap.put("Nav_mk", getattribute(null, compId, "vessel", entId, null, "Navtex Make", "string", "JRC", "",
				"NAV_EQP", 9, 4, "Nav_mk", "Navtex_Make"));
		attributeMap.put("ECDIS_mk", getattribute(null, compId, "vessel", entId, null, "ECDIS Make", "string", "Furuno",
				"", "NAV_EQP", 10, 4, "ECDIS_mk", "ECDIS_Make"));

		attributeMap.put("Rad1_Typ", getattribute(null, compId, "vessel", entId, null, "Rador1 Type", "string",
				"MU-201 CR", "", "NAV_EQP", 11, 4, "Rad1_Typ", "Radar1_Type"));
		attributeMap.put("Rad2_Typ", getattribute(null, compId, "vessel", entId, null, "Rador2 Type", "string",
				"MU-201 CR", "", "NAV_EQP", 12, 4, "Rad2_Typ", "Radar2_Type"));
		attributeMap.put("GPS_Typ", getattribute(null, compId, "vessel", entId, null, "GPS Type", "string", "GP-150", "",
				"NAV_EQP", 13, 4, "GPS_Typ", "GPS_Type"));
		attributeMap.put("Echo_Typ", getattribute(null, compId, "vessel", entId, null, "Echo Sounder Type", "string",
				"GDS-101", "", "NAV_EQP", 14, 4, "Echo_Typ", "ECHO_sounder_Type"));
		attributeMap.put("Gyro_comp_Typ", getattribute(null, compId, "vessel", entId, null, "Gyro Compass Type", "string",
				"", "", "NAV_EQP", 15, 4, "Gyro_comp_Typ", "Gyro Compass Type"));
		attributeMap.put("AP_Typ", getattribute(null, compId, "vessel", entId, null, "Auto pilot Type", "string",
				"AP9 MK3", "", "NAV_EQP", 16, 4, "AP_Typ", "Auto_pilot_Make"));
		attributeMap.put("MagComp_Typ", getattribute(null, compId, "vessel", entId, null, "Magnetic Compass Type",
				"string", "Type2", "", "NAV_EQP", 17, 4, "MagComp_Typ", "Magnetic_Compass_Type"));
		attributeMap.put("AIS_Typ", getattribute(null, compId, "vessel", entId, null, "AIS Make Type", "string", "FA-100",
				"", "NAV_EQP", 18, 4, "AIS_Typ", "AIS_Type"));
		attributeMap.put("Nav_Typ", getattribute(null, compId, "vessel", entId, null, "Navtex Make Type", "string",
				"NCR-330", "", "NAV_EQP", 19, 4, "Nav_Typ", "Navtex_Type"));
		attributeMap.put("ECDIS_Typ", getattribute(null, compId, "vessel", entId, null, "ECDIS Make Type", "string",
				"MU-201 CR", "", "NAV_EQP", 20, 4, "ECDIS_Typ", "ECDIS_Type"));

		attributeMap.put("CSign", getattribute(null, compId, "vessel", entId, null, "Call Sign", "string", "AUTI", "",
				"COM_EQP", 1, 5, "CSign", "Call Sign"));
		attributeMap.put("MMSI", getattribute(null, compId, "vessel", entId, null, "MMSI No", "string", "419692000", "",
				"COM_EQP", 2, 5, "MMSI", "MMSI No."));
		attributeMap.put("SSB_Make", getattribute(null, compId, "vessel", entId, null, "SSB/GMDSS Make", "string",
				"Furuno", "", "COM_EQP", 3, 5, "SSB_Make", "SSB/GMDSS Make"));
		attributeMap.put("InmarsatC_Make", getattribute(null, compId, "vessel", entId, null, "InmarsatC Make", "string",
				"Sailor", "", "COM_EQP", 4, 5, "InmarsatC_Make", "InmarsatC Make"));
		attributeMap.put("VHF_Make", getattribute(null, compId, "vessel", entId, null, "VHF Make", "string", "Sailor", "",
				"COM_EQP", 5, 5, "VHF_Make", "VHF Make"));
		attributeMap.put("SSB_Type", getattribute(null, compId, "vessel", entId, null, "SSB/GMDSS Type", "string",
				"FS 2570C", "", "COM_EQP", 6, 5, "SSB_Type", "SSB/GMDSS Type No."));
		attributeMap.put("InmarsatC_Type", getattribute(null, compId, "vessel", entId, null, "InmarsatC Type", "string",
				"RT 4822", "", "COM_EQP", 7, 5, "InmarsatC_Type", "InmarsatC Type No."));
		attributeMap.put("VHF_Type", getattribute(null, compId, "vessel", entId, null, "VHF Type", "string", "TT 3606E",
				"", "COM_EQP", 8, 5, "VHF_Type", "VHF Type"));
		attributeMap.put("PQty", getattribute(null, compId, "vessel", entId, null, "Portable Qty", "string", "05", "",
				"COM_EQP", 9, 5, "PQty", "Portable-Qty"));
		attributeMap.put("GMDSS_PQty", getattribute(null, compId, "vessel", entId, null, "GMDSS portable-Qty", "string",
				"03", "", "COM_EQP", 10, 5, "GMDSS_PQty", "GMDSS portable-Qty"));

		attributeMap.put("PSPQty", getattribute(null, compId, "vessel", entId, null, "Portable Salvage Pump Qty", "string",
				"1", "", "ADD_EQP", 1, 6, "PSPQty", "Portable Salvage Pump Qty"));
		attributeMap.put("PSPtype", getattribute(null, compId, "vessel", entId, null, "Portable Salvage Pump Type",
				"string", "Electric Motor driven", "", "ADD_EQP", 2, 6, "PSPtype", "Portable Salvage Pump Type"));
		attributeMap.put("PSPQ_Cap",
				getattribute(null, compId, "vessel", entId, null, "Portable Salvage Pump Total Capacity", "string",
						"40 MT", "", "ADD_EQP", 3, 6, "PSPQ_Cap", "Portable Salvage Pump Total Capacity"));
		attributeMap.put("FFT", getattribute(null, compId, "vessel", entId, null, "Fire Fighting Type", "string",
				"FIFI-Agni -1", "", "ADD_EQP", 4, 6, "FFT", "Fire Fighting Type"));
		attributeMap.put("FFM", getattribute(null, compId, "vessel", entId, null, "Fire Fighting Monitors", "string", "02",
				"", "ADD_EQP", 6, 6, "FFM", "Fire Fighting Monitors"));
		attributeMap.put("FFTC", getattribute(null, compId, "vessel", entId, null, "Fire Fighting Total Capacity",
				"string", "1200 * 2 M3/Hr", "", "ADD_EQP", 7, 6, "FFTC", "Fire Fighting Total Capacity"));
		attributeMap.put("GCGQ", getattribute(null, compId, "vessel", entId, null, "Gas Cutting Gear Qty", "string", "1",
				"", "ADD_EQP", 8, 6, "GCGQ", "Gas Cutting Gear Qty"));
		attributeMap.put("GCGM", getattribute(null, compId, "vessel", entId, null, "Gas Cutting Gear Type", "string",
				"UNITOR", "", "ADD_EQP", 9, 6, "GCGM", "Gas Cutting Gear Type"));
		attributeMap.put("GCGTC", getattribute(null, compId, "vessel", entId, null, "Gas Cutting Gear Total Capacity",
				"string", "Yes", "", "ADD_EQP", 10, 6, "GCGTC", "Gas Cutting Gear Total Capacity"));
		attributeMap.put("WEQ", getattribute(null, compId, "vessel", entId, null, "Welding Equipment Qty", "string", "1",
				"", "ADD_EQP", 11, 6, "WEQ", "Welding Equipment Qty"));
		attributeMap.put("WET", getattribute(null, compId, "vessel", entId, null, "Welding Equipment Type", "string",
				"UNITOR", "", "ADD_EQP", 12, 6, "WET", "Welding Equipment Type"));
		attributeMap.put("WEP", getattribute(null, compId, "vessel", entId, null, "Welding Equipment Portable", "string",
				"Yes", "", "ADD_EQP", 13, 6, "WEP", "Welding Equipment Portable"));
		attributeMap.put("RCQ", getattribute(null, compId, "vessel", entId, null, "Rescue Craft Qty", "string", "1", "",
				"ADD_EQP", 14, 6, "RCQ", "Rescue Craft Qty"));
		attributeMap.put("RCNP", getattribute(null, compId, "vessel", entId, null, "Rescue Craft No Of Persons", "string",
				"10", "", "ADD_EQP", 15, 6, "RCNP", "Rescue Craft No. Of Persons"));
		attributeMap.put("RCHP", getattribute(null, compId, "vessel", entId, null, "Rescue Craft Engine HP", "string",
				"209.1", "", "ADD_EQP", 16, 6, "RCHP", "Rescue Craft Engine HP"));
		attributeMap.put("MOBQty", getattribute(null, compId, "vessel", entId, null, "Man Overboard Boat Qty", "string",
				"1", "", "ADD_EQP", 17, 6, "MOBQty", "Man Overboard Boat Qty"));
		attributeMap.put("MOBNP", getattribute(null, compId, "vessel", entId, null, "Man Overboard Boat No Of Persons",
				"string", "9", "", "ADD_EQP", 18, 6, "MOBNP", "Man Overboard Boat No. Of Persons"));
		attributeMap.put("MOBHP", getattribute(null, compId, "vessel", entId, null, "Man Overboard Boat Engine HP",
				"string", "-", "", "ADD_EQP", 19, 6, "MOBHP", "Man Overboard Boat Engine HP"));
		attributeMap.put("PAty", getattribute(null, compId, "vessel", entId, null, "Port Anchor Type", "string", "Spek",
				"", "ADD_EQP", 20, 6, "PAty", "Port Anchor Type"));
		attributeMap.put("PAW", getattribute(null, compId, "vessel", entId, null, "Port Anchor Weight", "string", "1750Kg",
				"", "ADD_EQP", 21, 6, "PAW", "Port Anchor Weight"));
		attributeMap.put("PACab", getattribute(null, compId, "vessel", entId, null, "Port Anchor Cable", "string",
				"38mm x 8 shackles", "", "ADD_EQP", 22, 6, "PACab", "Port Anchor cable Dia * Length"));
		attributeMap.put("SATy", getattribute(null, compId, "vessel", entId, null, "Starboard Anchor Type", "string",
				"Spek", "", "ADD_EQP", 23, 6, "SATy", "Starboard Anchor Type"));
		attributeMap.put("SAW", getattribute(null, compId, "vessel", entId, null, "Starboard Anchor Weight", "string",
				"1750Kg", "", "ADD_EQP", 24, 6, "SAW", "Starboard Anchor Weight"));
		attributeMap.put("SACab", getattribute(null, compId, "vessel", entId, null, "Starboard Anchor Cable", "string",
				"38mm x 8 shackles", "", "ADD_EQP", 25, 6, "SACab", "Starboard Anchor Cable dia * Lenght"));
		attributeMap.put("SAT", getattribute(null, compId, "vessel", entId, null, "Spare Anchor Type", "string", "-", "",
				"ADD_EQP", 27, 6, "SAT", "Spare Anchor Type"));
		attributeMap.put("SAWt", getattribute(null, compId, "vessel", entId, null, "Spare Anchor Weight", "string", "-",
				"", "ADD_EQP", 28, 6, "SAWt", "Spare Anchor Weight"));
		attributeMap.put("SAC", getattribute(null, compId, "vessel", entId, null, "Spare Anchor cabley", "string", "-",
				"-", "ADD_EQP", 29, 6, "SAC", "Spare Anchor cable"));
		attributeMap.put("OWSQty", getattribute(null, compId, "vessel", entId, null, "Oil Water seperator Qty", "string",
				"1", "", "ADD_EQP", 30, 6, "OWSQty", "Oil Water seperator Qty"));
		attributeMap.put("OWST", getattribute(null, compId, "vessel", entId, null, "Oil Water seperator Type", "string",
				"RWO-SKIT/S-DEB", "", "ADD_EQP", 31, 6, "OWST", "Oil Water seperator Type"));
		attributeMap.put("OWSC", getattribute(null, compId, "vessel", entId, null, "Oil Water seperator Capacity",
				"string", "1 M3/HR", "", "ADD_EQP", 32, 6, "OWSC", "Oil Water seperator Capacity"));
		attributeMap.put("WMQ", getattribute(null, compId, "vessel", entId, null, "Water Maker Qty", "string", "1", "",
				"ADD_EQP", 33, 6, "WMQ", "Water maker Qty"));
		attributeMap.put("WMTy", getattribute(null, compId, "vessel", entId, null, "Water Maker Qty", "string",
				"RWO SWRO25", "", "ADD_EQP", 34, 6, "WMTy", "Water maker Type"));
		attributeMap.put("WMC", getattribute(null, compId, "vessel", entId, null, "Water Maker Capacity", "string",
				"25M3@24Hr", "", "ADD_EQP", 35, 6, "WMC", "Water maker capacity"));
		attributeMap.put("STQ", getattribute(null, compId, "vessel", entId, null, "Sewage Treatment Qty", "string", "1",
				"", "ADD_EQP", 36, 6, "STQ", "Sewage Treatment"));
		attributeMap.put("STT", getattribute(null, compId, "vessel", entId, null, "Sewage Treatment Type", "string",
				"GC-30IR", "", "ADD_EQP", 37, 6, "STT", "Sewage Treatment Type"));
		attributeMap.put("STTC", getattribute(null, compId, "vessel", entId, null, "Sewage treatment capacity", "string",
				"17M3", "", "ADD_EQP", 38, 6, "STTC", "Sewage treatment capacity"));
		return attributeMap.values();
	}

	
	public static Attribute getattribute(String id, UUID cid, String eTyp, UUID eid, UUID sid, String name, String typ,
			String value, String defVal, String groupNm, int ord, int grpOrd, String code, String string8) {
		return new Attribute(id, cid, eTyp, eid, sid, name, code, name, typ, value, defVal, groupNm, ord, grpOrd);
	}

}
