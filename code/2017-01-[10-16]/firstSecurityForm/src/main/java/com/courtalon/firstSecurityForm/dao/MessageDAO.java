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
		// vulnerable -> "%' OR 1=1 -- "
		/*String  requette = "select * from message where titre like '%" + searchTerm + "%' and published=true";
		return getJdbcTemplate().query(requette, this);*/
		
		String requette = "select * from message where titre like ? and published=true";
		
		String param = "%" + searchTerm + "%";
		return getJdbcTemplate().query(requette, new Object[]{param}, this);
	}
	
	public Message findByID(int id) {
		return getJdbcTemplate().queryForObject("select * from message where id=?", this, id);
	}
	
	/*
	 * exemple
	 * update message set titre='toto', corps='toto est la' where id=2
	 *  
	 */
	public int saveMessage(Message msg) {
		
		/*
		 * vulnerable
		 * 1)
		 * "pawned', corps='leet' where id=2 -- " --> edite un autre message
		 * 2)
		 * "', corps=(select password from utilisateurs where login='admin') where id=3 -- "
		 * --> recupere le mot de passe d'admin dans le corps du message
		 * 
		 */
		String requette = "update message set titre='" +msg.getTitre() 
										+ "', corps='" + msg.getCorps()
										+ "' where id=" + msg.getId();
		return getJdbcTemplate().update(requette);
	}
	
	
	
	@Override
	public Message mapRow(ResultSet rs, int pos) throws SQLException {
		return new Message(rs.getInt("id"),
							rs.getString("titre"),
							rs.getString("corps"),
							rs.getBoolean("published"));
	}
	
	
	
}
