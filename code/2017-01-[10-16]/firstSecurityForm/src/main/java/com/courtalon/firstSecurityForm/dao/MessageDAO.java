package com.courtalon.firstSecurityForm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.courtalon.firstSecurityForm.metier.Message;

@Component
public class MessageDAO implements RowMapper<Message>
{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {return jdbcTemplate;}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

	/*
	 * exemple
	 * select * from message where titre like '%jar%' and published=true
	 * 
	 */
	public List<Message> findByTitre(String searchTerm) {
		String  requette = "select * from message where titre like '%" + searchTerm + "%' and published=true";
		return getJdbcTemplate().query(requette, this);
	}
	
	@Override
	public Message mapRow(ResultSet rs, int pos) throws SQLException {
		return new Message(rs.getInt("id"),
							rs.getString("titre"),
							rs.getString("corps"),
							rs.getBoolean("published"));
	}
	
	
	
}
