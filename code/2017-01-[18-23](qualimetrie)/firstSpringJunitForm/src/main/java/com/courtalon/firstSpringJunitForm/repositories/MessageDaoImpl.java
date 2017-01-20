package com.courtalon.firstSpringJunitForm.repositories;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.courtalon.firstSpringJunitForm.beans.Message;

public class MessageDaoImpl implements RowMapper<Message>, MessageDao
{
	private static final String SELECT_ALL = "select * from message";
	private static final String SELECT_BY_ID = "select * from message where id=?";
	private static final String INSERT_ONE = "insert into message (titre, corps) VALUES(?,?)";
	private static final String UPDATE_ONE = "update message set titre=?, corps=? where id=?";
	
	
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {return jdbcTemplate;}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}
	
	/* (non-Javadoc)
	 * @see com.courtalon.firstSpringJunitForm.repositories.MessageDao#findAll()
	 */
	@Override
	public List<Message> findAll() {
		return getJdbcTemplate().query(SELECT_ALL, this);
	}
	
	/* (non-Javadoc)
	 * @see com.courtalon.firstSpringJunitForm.repositories.MessageDao#findById(int)
	 */
	@Override
	public Message findById(int id) {
		return getJdbcTemplate().queryForObject(SELECT_BY_ID, this, id);
	}
	
	/* (non-Javadoc)
	 * @see com.courtalon.firstSpringJunitForm.repositories.MessageDao#save(com.courtalon.firstSpringJunitForm.beans.Message)
	 */
	@Override
	public int save(Message m) {
		if (m.getId() <= 0) {
			return getJdbcTemplate().update(INSERT_ONE, m.getTitre(), m.getCorps());
		}
		else {
			return getJdbcTemplate().update(UPDATE_ONE, m.getTitre(), m.getCorps(), m.getId());
		}
	}
	
	@Override
	public Message mapRow(ResultSet rs, int pos) throws SQLException {
		return new Message(rs.getInt("id"), rs.getString("titre"), rs.getString("corps"));
	}
	
	

}
