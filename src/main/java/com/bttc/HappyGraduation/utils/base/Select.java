package com.bttc.HappyGraduation.utils.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.ArrayList;
import java.util.List;

public class Select<T> extends QueryParams<T> {

	protected static final List<Column> selectCol = new ArrayList<>();

	public Select(Class c) {
		super(c);
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public  Select  column (String name,String ... alias) {
		if(alias.length>0) {
			this.selectCol.add(new Column(name, alias[0]));
		}else {
			this.selectCol.add(new Column(name, null));
		}
		
		return this;
	}
	
	public Select from (T t) {
		return this;
	}
	
	public List <Selection<?>> toSelection(Root<T> root, CriteriaBuilder criteriaBuilder) {
		List<Selection<?>> selectionList = new ArrayList<>();
		
		if (root == null || CollectionUtils.isEmpty(selectCol)) {
			return selectionList;
		}
		for(Column col:selectCol ) {
			Selection<?> selection=null;
			if(col.getName().contains("count")) {
				selection =criteriaBuilder.count(root);
			}else {
				selection =root.get(col.getName());
			}

			if(StringUtils.isNotEmpty(col.getAlias())){
				selection =selection.alias(col.getAlias());
			}
	        selectionList.add(selection);
		}
		
		return selectionList;
	}
	
	public static class Column{
		String name;
		String alias;
		
		public Column(String name,String alias){
			this.name=name;
			this.alias=alias;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAlias() {
			return alias;
		}

		public void setAlias(String alias) {
			this.alias = alias;
		}
		
	}

}
