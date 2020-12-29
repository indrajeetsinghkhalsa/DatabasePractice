package com.example.databasePractice.dao;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.databasePractice.model.Department;
import com.example.databasePractice.model.Employee;
import com.example.databasePractice.repository.EmployeeRepository;


@Repository

public class EmployeeDao implements EmployeeRepository{

	//@Autowired
	DepartmentDao dept = new DepartmentDao();;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Employee> getEmployeeList() {
		String sql="select * from Employee";
		List<Employee> list=jdbcTemplate
				.query(sql, 
						new BeanPropertyRowMapper(Employee.class));
		
		return list;
	}

	@Override
	public String insertEmployee(Employee employee) {
		String sql="insert into Employee(name,city,dept_id) values(?,?,?)";
		jdbcTemplate.update(sql, new Object[] {
				employee.getName(),
				employee.getCity(),
				employee.getDepartment().getId()},new int[] {Types.VARCHAR,
						Types.VARCHAR,
						Types.INTEGER});
		return "Employee saved";
	}

	@Override
	public Employee getEmployeeById(Integer id) {
		 String sql="select * from Employee where id=?";
		 Employee emp= (Employee) jdbcTemplate.queryForObject(sql, 
				 new Object[] {id},new int[] {Types.INTEGER},new BeanPropertyRowMapper(Employee.class));
		return emp;
	}

	@Override
	public String updateEmployee(Employee employee) {
		String sql="update Employee set name=?, city=? where id=?";
		jdbcTemplate.update(sql, new Object[] {
				employee.getName(),
				employee.getCity(),
				employee.getId()});
		return "Employee Updated";
	}

	@Override
	public List<Map<String, Object>> getCombinedDatas() {
		String sql="select e.id,e.name,e.city,d.name as deptName from employee e,department d where e.dept_id=d.id";
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
		return list;
	}
	
	public String insertWithOtherOption(Employee emp)
	{
		System.out.println("Other Option Selected");
		
		if(emp.getDepartment().getId()!=null)
		{
			insertEmployee(emp);
		}
		else
		{
			if(dept.getDepartmentByName(emp.getDepartment().getName()) == false)
			{
				System.err.println("-------------------------bhai");
				Integer key = dept.insertAndGetKey(emp.getDepartment().getName());
				
				emp.getDepartment().setId(key);
				insertEmployee(emp);
				return "added successfull";
			}
			else
			{
				return "plz provide department from select box list";
			}
		}
		return "Data Add sucessfully";
	}
	
	//Stored procedure
	public List<Map<String,Object>> getData() throws SQLException
	{
		String sql="{call SelectStoredProcedureFirstExample()}";
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
		 return list;
	}

}