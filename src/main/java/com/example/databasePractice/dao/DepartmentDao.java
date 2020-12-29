package com.example.databasePractice.dao;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.databasePractice.model.Department;
import com.example.databasePractice.repository.DepartmentRepository;

import lombok.extern.apachecommons.CommonsLog;

@Repository
//@Component
//@Service
public class DepartmentDao implements DepartmentRepository{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public String insertDepartment(Department dept) {
		String sql="insert into Department(name) values(?)";
		jdbcTemplate.update(sql, new Object[] {
				dept.getName()
				},new int[] {
						Types.VARCHAR
						});
		return "Department insert";
		
	}
	
	@Override
	public Integer insertAndGetKey(String other) {
		System.err.println("------------------------------"+other);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		//Integer key;
		String sql="insert into Department(name) values(?)";
		 jdbcTemplate.update(
	              connection -> {
	                  PreparedStatement ps = connection.prepareStatement(sql,new String[] {"id"});
	                  ps.setString(1,other);				//other  is my name of depratment and 1 is index
	                  return ps;
	              }, keyHolder);
		 
		return (Integer) keyHolder.getKey().intValue();
	}
	
	@Override
	public Boolean getDepartmentByName( String other) {
		System.out.println("Hiiiiiiiiiiii");
		String sql = "select count(*) from Department where name=?";
		Integer intCount = jdbcTemplate.queryForObject(
                sql, new Object[]{other},new int[] {Types.VARCHAR},Integer.class);
		System.out.println(intCount);
		
		if(intCount<=0) {
			System.err.println("0");
			return false;
		}
		else
		{
			System.err.println("1");
			return true;
	}
	}

	
}
