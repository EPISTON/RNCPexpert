#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import ${package}.metier.Message;

public interface MessageRepository 
		extends PagingAndSortingRepository<Message, Integer> {

	Page<Message> findByTitreContaining(String titre, Pageable p);
}
