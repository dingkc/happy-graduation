package com.bttc.HappyGraduation.utils.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class SimpleBaseRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleBaseRepository.class);

	private Class<T> domainClass;
	protected final EntityManager entityManager;



	public SimpleBaseRepository(JpaEntityInformation entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		domainClass = entityInformation.getJavaType();
		this.entityManager = entityManager;
	}

	public SimpleBaseRepository(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);

		this.domainClass = domainClass;
		this.entityManager = entityManager;
	}

	@Override
	public List <T> getBeans(T element) {
		QueryParams<T> queryParams = new QueryParams<>(domainClass);
		queryParams.and(new Filter());
		this.reflexClazz(element,  queryParams);
		return findAll(queryParams);
	}

	@Override
	public Page<T> getBeans(T element, int pageNumber, int pageSize) {
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = 10;
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		QueryParams<T> queryParams = new QueryParams<>(domainClass);
		queryParams.and(new Filter());
		this.reflexClazz(element, queryParams);
		return findAll(queryParams, pageable);
	}

	@Override
	public Page<T> getBeans(T element, List<Order> orderList, int pageNumber, int pageSize) {
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = 10;
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		QueryParams<T> queryParams = new QueryParams<>(domainClass);
		queryParams.and(new Filter());
		this.reflexClazz(element, queryParams);
		for(Order order : orderList) {
			if(Order.Direction.asc.equals(order.getDirection())){
				queryParams.orderASC(order.getProperty());
			} else if(Order.Direction.desc.equals(order.getDirection())){
				queryParams.orderDESC(order.getProperty());
			}
		}
		return findAll(queryParams, pageable);
	}

	@Override
	public Page<T> getBeans(QueryParams<T> queryParams, int pageNumber, int pageSize) {
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = 10;
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		if(queryParams == null){
			queryParams = new QueryParams<>(domainClass);
		}
		queryParams.and(new Filter());
		return findAll(queryParams, pageable);
	}

	@Override
	public List<T> getBeans(T element, QueryParams<T> queryParams) {
		if(queryParams == null){
			queryParams = new QueryParams<>(domainClass);
		}
		queryParams.and(new Filter());
		this.reflexClazz(element, queryParams);
		return findAll(queryParams);
	}

	@Override
	public Page<T> getBeans(T element, QueryParams<T> queryParams, int pageNumber, int pageSize) {
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = 10;
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		if(queryParams == null){
			queryParams = new QueryParams<>(domainClass);
		}
		queryParams.and(new Filter());
		this.reflexClazz(element, queryParams);
		return findAll(queryParams, pageable);
	}

	@Override
	public List<Map<String, Object>> getBeans(Select<T> qp, T t) {
		//新建统一的查询
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
		Root<T> root = criteriaQuery.from(domainClass);

		//拼接条件，即可以从qp中带过来，亦可以从对象中获取
		this.reflexClazz(t, qp);

		criteriaQuery.where(qp.toPredicate(root, criteriaQuery, criteriaBuilder));
		criteriaQuery.multiselect(qp.toSelection(root, criteriaBuilder));

		TypedQuery<Tuple> q = entityManager.createQuery(criteriaQuery);
		List<Tuple> result = q.getResultList();
		List<Map<String, Object>> resulList = new ArrayList<>(result.size());

		for(Tuple tuple : result){
			Map map = new HashMap(qp.selectCol.size());
			for(int i = 0; i < qp.selectCol.size(); i++){
				map.put(qp.selectCol.get(i).getAlias(),tuple.get(qp.selectCol.get(i).getAlias()));
			}
			resulList.add(map);
		}
		return resulList;
	}

	@Override
	public List<T> getBeansAutoExceptNull(T element) {
		QueryParams<T> queryParams = new QueryParams<>(domainClass);
		queryParams.and(new Filter());
		queryParams.setCriteriaNull(true);
		this.reflexClazzAutoExceptNull(element,  queryParams);
		return findAll(queryParams);
	}

	@Override
	public Page<T> getBeansAutoExceptNull(T element, int pageNumber, int pageSize) {
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = 10;
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		QueryParams<T> queryParams = new QueryParams<>(domainClass);
		queryParams.and(new Filter());
		queryParams.setCriteriaNull(true);
		this.reflexClazzAutoExceptNull(element, queryParams);
		return findAll(queryParams, pageable);
	}

	@Override
	public Page<T> getBeansAutoExceptNull(T element, List<Order> orderList, int pageNumber, int pageSize) {
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = 10;
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		QueryParams<T> queryParams = new QueryParams<>(domainClass);
		queryParams.and(new Filter());
		queryParams.setCriteriaNull(true);
		this.reflexClazzAutoExceptNull(element, queryParams);
		for(Order order : orderList) {
			if(Order.Direction.asc.equals(order.getDirection())){
				queryParams.orderASC(order.getProperty());
			} else if(Order.Direction.desc.equals(order.getDirection())){
				queryParams.orderDESC(order.getProperty());
			}
		}
		return findAll(queryParams, pageable);
	}

	@Override
	public List<T> getBeansAutoExceptNull(T element, QueryParams<T> queryParams) {
		if(queryParams == null){
			queryParams = new QueryParams<>(domainClass);
		}
		queryParams.and(new Filter());
		queryParams.setCriteriaNull(true);
		this.reflexClazzAutoExceptNull(element, queryParams);
		return findAll(queryParams);
	}

	@Override
	public Page<T> getBeansAutoExceptNull(T element, QueryParams<T> queryParams, int pageNumber, int pageSize) {
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = 10;
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		if(queryParams == null){
			queryParams = new QueryParams<>(domainClass);
		}
		queryParams.and(new Filter());
		queryParams.setCriteriaNull(true);
		this.reflexClazzAutoExceptNull(element, queryParams);
		return findAll(queryParams, pageable);
	}

	private void reflexClazz(T element, QueryParams<T> queryParams) {
		String column;
		PropertyDescriptor pd;
		Method getMethod;
		Field[] fields = domainClass.getDeclaredFields();
		for (Field field : fields) {
			column = field.getName();
			try {
				pd = new PropertyDescriptor(column, domainClass);
				getMethod = pd.getReadMethod();// 获得get方法
				Object o = getMethod.invoke(element);// 执行get方法返回一个Object
				if(o !=null) {//后续再这儿判断如果qp中已经存在的条件，则不再对象中获取
					if(!queryParams.checkFilterProperty(column))
						queryParams.and(Filter.eq(column, o));
				}
			} catch (Exception e) {
				if(logger.isErrorEnabled()) {
					logger.error("该字段" + column + "不是" + element + "类的字段", e);
				}
			}
		}
	}

	private void reflexClazzAutoExceptNull(T element, QueryParams<T> queryParams) {
		String column;
		PropertyDescriptor pd;
		Method getMethod;
		Field[] fields = domainClass.getDeclaredFields();
		for (Field field : fields) {
			column = field.getName();
			try {
				pd = new PropertyDescriptor(column, domainClass);
				getMethod = pd.getReadMethod();// 获得get方法
				Object o = getMethod.invoke(element);// 执行get方法返回一个Object
				if(o !=null && !"".equals(o)) {//后续再这儿判断如果qp中已经存在的条件，则不再对象中获取
					if(!queryParams.checkFilterProperty(column))
						queryParams.and(Filter.eq(column, o));
				}
			} catch (Exception e) {
				if(logger.isErrorEnabled()) {
					logger.error("该字段" + column + "不是" + element + "类的字段", e);
				}
			}
		}
		// 清空queryParams条件中值为空串 的条件，此条支持的是 and
		queryParams.deleteAndFiltersNull();
	}

	/**
     * 根据主键和对象更新beans
     * @param id 主键, entity 对象
     * @return T
     */
    @Override
    @Transactional
    public T updateBeans(ID id, T entity) {
		Optional<T> optional = this.findById(id);
		T managedEntity = optional.orElse(null);
		T mergedEntity;
		if (managedEntity == null) {
			entityManager.persist(entity);
			entityManager.flush();
			mergedEntity = entity;
		} else {
			BeanUtils.copyProperties(entity, managedEntity, getNullProperties(entity));
			entityManager.merge(managedEntity);
			entityManager.flush();
			mergedEntity = managedEntity;
		}
		return mergedEntity;
    }

	@Override
	@Transactional
	public T updateBeans(T entity) {
		Field[] fields = domainClass.getDeclaredFields();
		String column = "";
		PropertyDescriptor pd;
		Method getMethod;
		for (Field field : fields) {
			Id pkId = field.getAnnotation(Id.class);
			if(pkId !=null) {
				column = field.getName();
				break;
			}
		}
		try {
			pd = new PropertyDescriptor(column, domainClass);
			getMethod = pd.getReadMethod();// 获得get方法
			Object o = getMethod.invoke(entity);// 执行get方法返回一个Object
			Optional<T> optional = this.findById((ID) o);
			T managedEntity = optional.orElse(null);
			if (managedEntity == null) {
				entityManager.persist(entity);
				entityManager.flush();
				return entity;
			} else {
				BeanUtils.copyProperties(entity, managedEntity, getNullProperties(entity));
				entityManager.merge(managedEntity);
				entityManager.flush();
				return managedEntity;
			}
		} catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
			if(logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	@Override
	public void batchDelete(QueryParams<T> queryParams) {
		List entities = findAll(queryParams);
		deleteInBatch(entities);
	}

	@Override
	public void batchUpdate(List<T> entities) {
		for(T t : entities)
			updateBeans(t);
	}

	/**
     * 将为空的properties给找出来,然后返回出来
     * @param src
     * @return
     */
    private static String[] getNullProperties(Object src) {
        BeanWrapper srcBean=new BeanWrapperImpl(src);
        PropertyDescriptor[] pds=srcBean.getPropertyDescriptors();
        Set<String> emptyName=new HashSet<>();
        for(PropertyDescriptor p:pds){
            Object srcValue=srcBean.getPropertyValue(p.getName());
            if(srcValue==null) emptyName.add(p.getName());
        }
        String[] result=new String[emptyName.size()];
        return emptyName.toArray(result);
    }

}
