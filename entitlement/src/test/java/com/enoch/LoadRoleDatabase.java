package com.enoch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.enoch.dao.RoleDAO;
import com.enoch.role.dto.PrivillegeDTO;
import com.enoch.role.dto.RoleDTO;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadRoleDatabase {


	@Bean
	CommandLineRunner initRole(RoleDAO repository) {
		return args -> {

			/*
			 * log.info("Preloading " + repository .createPrivillege(new
			 * PrivillegeDTO(null,"","TEST PRIV",true,false))); log.info("Preloading " +
			 * repository .createPrivillege(new
			 * PrivillegeDTO(null,"","TEST PRIV2",true,true)));
			 */
		};
	}

	@Bean
	CommandLineRunner initPriv(RoleDAO repository) {
		return args -> {
			/*
			 * log.info("Preloading " + repository .createRole(new
			 * RoleDTO(null,"cmp","TEST Role",true,false))); log.info("Preloading " +
			 * repository .createRole(new RoleDTO(null,"cmp","TEST Role",true,false)));
			 */		};
	}

}