package com.courtalon.gigaMvcGalerie.utils;

import java.util.Iterator;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.annotation.JsonView;

public class JsonPageable<T> implements Page<T> {

	public static class PaginatedResult {}
	
	public static <T2> JsonPageable<T2> fromPage(Page<T2> page) {
		return new JsonPageable<T2>(page);
	}
	
	public JsonPageable(Page<T> originalPage) {
		this.originalPage = originalPage;
	}
	
	private Page<T> originalPage;
	
	@JsonView(PaginatedResult.class)
	@Override
	public List<T> getContent() {return originalPage.getContent();}

	@JsonView(PaginatedResult.class)
	@Override
	public int getNumber() {return originalPage.getNumber();}

	@JsonView(PaginatedResult.class)
	@Override
	public int getNumberOfElements() {return originalPage.getNumberOfElements();}

	@JsonView(PaginatedResult.class)
	@Override
	public int getSize() {return originalPage.getSize();}

	@Override
	public Sort getSort() {return originalPage.getSort();}

	@Override
	public boolean hasContent() {return originalPage.hasContent();}

	@Override
	public boolean hasNext() {return originalPage.hasNext();}

	@Override
	public boolean hasPrevious() {return originalPage.hasPrevious();}

	@JsonView(PaginatedResult.class)
	@Override
	public boolean isFirst() {return originalPage.isFirst();}

	@JsonView(PaginatedResult.class)
	@Override
	public boolean isLast() { return originalPage.isLast();}

	@Override
	public Pageable nextPageable() {return originalPage.nextPageable();}

	@Override
	public Pageable previousPageable() {return originalPage.previousPageable();}

	@Override
	public Iterator<T> iterator() {return originalPage.iterator();}

	@JsonView(PaginatedResult.class)
	@Override
	public long getTotalElements() {return originalPage.getTotalElements();}

	@JsonView(PaginatedResult.class)
	@Override
	public int getTotalPages() { return originalPage.getTotalPages();}

	@Override
	public <S> Page<S> map(Converter<? super T, ? extends S> arg0) {return originalPage.map(arg0);}
	

}
