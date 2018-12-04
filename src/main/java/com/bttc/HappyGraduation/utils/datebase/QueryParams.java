package com.bttc.HappyGraduation.utils.datebase;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * 
 * @author 章涛
 *
 *  封装查询条件的实体
 */
public class QueryParams<T> implements Specification<T> {

	private Class  clasz;
	
	public QueryParams(Class c) {
		super();
		clasz=c;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 属性分隔符 */
	private static final String PROPERTY_SEPARATOR = ".";
	/**
	 * and条件
	 */
	private List<Filter> andFilters = new ArrayList<>();
	/**
	 * or条件
	 */
	private List<Filter> orFilters = new ArrayList<>();
	/**
	 * 排序属性
	 */
	private List<Order> orders = new ArrayList<>();

	/**
	 * 分组属性
	 */
	private List<String> groupBys = new ArrayList<>();

	/**
	 * 分组过滤器and属性
	 */
	private List<Having> andHavings = new ArrayList<>();

	/**
	 * 分组过滤器or属性
	 */
	private List<Having> orHavings = new ArrayList<>();

	/**
	 * criteria null,该值为真时，自动去除值为空的查询条件
	 */
	private boolean criteriaNull = false;

	/**
	 * 获取Path
	 *
	 * @param path
	 *            Path
	 * @param propertyPath
	 *            属性路径
	 * @return Path
	 */
	@SuppressWarnings("unchecked")
	private <X> Path<X> getPath(Path<?> path, String propertyPath) {
		if (path == null || StringUtils.isEmpty(propertyPath)) {
			return (Path<X>) path;
		}
		String property = StringUtils.substringBefore(propertyPath, PROPERTY_SEPARATOR);
		return getPath(path.get(property), StringUtils.substringAfter(propertyPath, PROPERTY_SEPARATOR));
	}

	/**
	 * 转换为Predicate
	 *
	 * @param root
	 *            Root
	 * @return Predicate
	 */
	@SuppressWarnings("unchecked")
	public Predicate toAndPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, List<Filter> filterList) {
		Predicate restrictions = criteriaBuilder.conjunction();
		if (root == null || CollectionUtils.isEmpty(filterList)) {
			return restrictions;
		}
		for (Filter filter : filterList) {
			if (filter == null) {
				continue;
			}
			String property = filter.getProperty();
			Filter.Operator operator = filter.getOperator();
			if(property ==null || ("").equals(property) || operator == null){
				continue;
			}
			Object value = filter.getValue();
			//如果查询条件为空自动去除，则判断value
			if(criteriaNull && value == null) {
				continue;
			}
			Boolean ignoreCase = filter.getIgnoreCase();
			Path<?> path = getPath(root, property);
			if (path == null) {
				continue;
			}
			// 根据运算符生成相应条件
			switch (operator) {
			case eq:
				if (value != null) {
					if (BooleanUtils.isTrue(ignoreCase) && String.class.isAssignableFrom(path.getJavaType())
							&& value instanceof String) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
								.equal(criteriaBuilder.lower((Path<String>) path), ((String) value).toLowerCase()));
					} else {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(path, value));
					}
				} else {
					restrictions = criteriaBuilder.and(restrictions, path.isNull());
				}
				break;
			case ne:
				if (value != null) {
					if (BooleanUtils.isTrue(ignoreCase) && String.class.isAssignableFrom(path.getJavaType())
							&& value instanceof String) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
								.notEqual(criteriaBuilder.lower((Path<String>) path), ((String) value).toLowerCase()));
					} else {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(path, value));
					}
				} else {
					restrictions = criteriaBuilder.and(restrictions, path.isNotNull());
				}
				break;
			case gt:
				if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
					restrictions = criteriaBuilder.and(restrictions,
							criteriaBuilder.gt((Path<Number>) path, (Number) value));
				}
				break;
			case lt:
				if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
					restrictions = criteriaBuilder.and(restrictions,
							criteriaBuilder.lt((Path<Number>) path, (Number) value));
				}
				break;
			case ge:
				if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
					restrictions = criteriaBuilder.and(restrictions,
							criteriaBuilder.ge((Path<Number>) path, (Number) value));
				}
				break;
			case le:
				if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
					restrictions = criteriaBuilder.and(restrictions,
							criteriaBuilder.le((Path<Number>) path, (Number) value));
				}
				break;
			case like:
				if (String.class.isAssignableFrom(path.getJavaType()) && value instanceof String) {
					if (BooleanUtils.isTrue(ignoreCase)) {
						restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
								.like(criteriaBuilder.lower((Path<String>) path), ("%"+value+"%").toLowerCase()));
					} else {
						restrictions = criteriaBuilder.and(restrictions,
								criteriaBuilder.like((Path<String>) path, "%"+value+"%"));
					}
				}
				break;
			case in:
				restrictions = criteriaBuilder.and(restrictions, path.in(value));
				break;
			case isNull:
				restrictions = criteriaBuilder.and(restrictions, path.isNull());
				break;
			case isNotNull:
				restrictions = criteriaBuilder.and(restrictions, path.isNotNull());
				break;
			}
		}
		return restrictions;
	}

	/**
	 * 转换为Predicate
	 *
	 * @param root
	 *            Root
	 * @return Predicate
	 */
	@SuppressWarnings("unchecked")
	private Predicate toOrPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, List<Filter> filterList) {
		Predicate restrictions = criteriaBuilder.disjunction();
		if (root == null || CollectionUtils.isEmpty(filterList)) {
			return restrictions;
		}
		for (Filter filter : filterList) {
			if (filter == null) {
				continue;
			}
			String property = filter.getProperty();
			Filter.Operator operator = filter.getOperator();
			if(property ==null || ("").equals(property) || operator == null){
				continue;
			}
			Object value = filter.getValue();
			//如果查询条件为空自动去除，则判断value
			if(criteriaNull && value == null) {
				continue;
			}
			Boolean ignoreCase = filter.getIgnoreCase();
			Path<?> path = getPath(root, property);
			if (path == null) {
				continue;
			}
			switch (operator) {
			case eq:
				if (value != null) {
					if (BooleanUtils.isTrue(ignoreCase) && String.class.isAssignableFrom(path.getJavaType())
							&& value instanceof String) {
						restrictions = criteriaBuilder.or(restrictions, criteriaBuilder
								.equal(criteriaBuilder.lower((Path<String>) path), ((String) value).toLowerCase()));
					} else {
						restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.equal(path, value));
					}
				} else {
					restrictions = criteriaBuilder.or(restrictions, path.isNull());
				}
				break;
			case ne:
				if (value != null) {
					if (BooleanUtils.isTrue(ignoreCase) && String.class.isAssignableFrom(path.getJavaType())
							&& value instanceof String) {
						restrictions = criteriaBuilder.or(restrictions, criteriaBuilder
								.notEqual(criteriaBuilder.lower((Path<String>) path), ((String) value).toLowerCase()));
					} else {
						restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.notEqual(path, value));
					}
				} else {
					restrictions = criteriaBuilder.or(restrictions, path.isNotNull());
				}
				break;
			case gt:
				if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
					restrictions = criteriaBuilder.or(restrictions,
							criteriaBuilder.gt((Path<Number>) path, (Number) value));
				}
				break;
			case lt:
				if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
					restrictions = criteriaBuilder.or(restrictions,
							criteriaBuilder.lt((Path<Number>) path, (Number) value));
				}
				break;
			case ge:
				if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
					restrictions = criteriaBuilder.or(restrictions,
							criteriaBuilder.ge((Path<Number>) path, (Number) value));
				}
				break;
			case le:
				if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
					restrictions = criteriaBuilder.or(restrictions,
							criteriaBuilder.le((Path<Number>) path, (Number) value));
				}
				break;
			case like:
				if (String.class.isAssignableFrom(path.getJavaType()) && value instanceof String) {
					if (BooleanUtils.isTrue(ignoreCase)) {
						restrictions = criteriaBuilder.or(restrictions, criteriaBuilder
								.like(criteriaBuilder.lower((Path<String>) path), ((String) value).toLowerCase()));
					} else {
						restrictions = criteriaBuilder.or(restrictions,
								criteriaBuilder.like((Path<String>) path, (String) value));
					}
				}
				break;
			case in:
				restrictions = criteriaBuilder.or(restrictions, path.in(value));
				break;
			case isNull:
				restrictions = criteriaBuilder.or(restrictions, path.isNull());
				break;
			case isNotNull:
				restrictions = criteriaBuilder.or(restrictions, path.isNotNull());
				break;
			}
		}
		return restrictions;
	}

	/**
	 * 转换为Order
	 *
	 * @param root
	 *            Root
	 * @return Order
	 */
	private List<javax.persistence.criteria.Order> toOrders(Root<T> root, CriteriaBuilder criteriaBuilder) {
		List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();
		if (root == null || CollectionUtils.isEmpty(orders)) {
			return orderList;
		}
		for (Order order : orders) {
			if (order == null) {
				continue;
			}
			String property = order.getProperty();
			Order.Direction direction = order.getDirection();
			Path<?> path = getPath(root, property);
			if (path == null || direction == null) {
				continue;
			}
			switch (direction) {
			case asc:
				orderList.add(criteriaBuilder.asc(path));
				break;
			case desc:
				orderList.add(criteriaBuilder.desc(path));
				break;
			}
		}
		return orderList;
	}

	/**
	 * 生成条件的
	 * 
	 * @param root
	 *            该对象的封装
	 * @param query
	 *            查询构建器
	 * @param cb
	 *            构建器
	 * @return 条件集合
	 */
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		
		Predicate restrictions = null;
		if (andFilters.size() != 0)
			restrictions = cb.and(toAndPredicate(root, cb, andFilters));
		if (orFilters.size() != 0)
			restrictions = cb.and(restrictions, toOrPredicate(root, cb, orFilters));
		query.where(restrictions);
		if (orders.size() != 0)
			query.orderBy(toOrders(root, cb));
		if(groupBys.size()!=0)
			query.groupBy(toGroupBy(root, cb));
		if(andHavings.size()!=0){
			Predicate restHaving = cb.and(toHavingAndPredicate(root, cb, andHavings));
			query.having(restHaving);
		}
		if(orHavings.size()!=0){
			Predicate restHaving = cb.and(toHavingAndPredicate(root, cb, orHavings));///////////////////////////////错误
			query.having(restHaving);
		}
		return query.getRestriction();
	}
	
	public QueryParams groupBy(String... s) {
		this.groupBys.addAll(Arrays.asList(s));
		return this;
	}
	
	private List<Expression<?>> toGroupBy(Root<T> root, CriteriaBuilder criteriaBuilder) {
		List<Expression<?>> groupByList = new ArrayList<Expression<?>>();
		if (root == null || CollectionUtils.isEmpty(groupBys)) {
			return groupByList;
		}
		for (String groupBy : groupBys) {
			groupByList.add(root.get(groupBy));
		}
		return groupByList;
	}

	/**
	 * 添加一个and条件
	 * 
	 * @param filter
	 *            该条件
	 * @return 链式调用
	 */
	public QueryParams and(Filter filter) {
		this.andFilters.add(filter);
		return this;
	}

	/**
	 * 添加多个and条件
	 * 
	 * @param filter
	 *            该条件
	 * @return 链式调用
	 */
	public QueryParams and(Filter... filter) {
		this.andFilters.addAll(Arrays.asList(filter));
		return this;
	}

	/**
	 * 添加一个or条件
	 * 
	 * @param filter
	 *            该条件
	 * @return 链式调用
	 */
	public QueryParams or(Filter filter) {
		this.orFilters.add(filter);
		return this;
	}

	/**
	 * 添加多个or条件
	 * 
	 * @param filter
	 *            该条件
	 * @return 链式调用
	 */
	public QueryParams or(Filter... filter) {
		this.orFilters.addAll(Arrays.asList(filter));
		return this;
	}

	/**
	 * 升序字段
	 * 
	 * @param property
	 *            该字段对应变量名
	 * @return 链式调用
	 */
	public QueryParams orderASC(String property) {
		this.orders.add(Order.asc(property));
		return this;
	}

	/**
	 * 降序字段
	 * 
	 * @param property
	 *            该字段对应变量名
	 * @return 链式调用
	 */
	public QueryParams orderDESC(String property) {
		this.orders.add(Order.desc(property));
		return this;
	}

	/**
	 * 删除and条件中值是空串的
	 *
	 * @return 该实例
	 */
	public QueryParams deleteAndFiltersNull() {
		Iterator<Filter> it = this.andFilters.iterator();
		while(it.hasNext()){
			Filter filter = it.next();
			if(filter.getValue() == null || ("").equals(filter.getValue())){
				it.remove();
			}
		}
		return this;
	}

	/**
	 * 删除or条件中值是空串的
	 *
	 * @return 该实例
	 */
	public QueryParams deleteOrFiltersNull() {
		Iterator<Filter> it = this.orFilters.iterator();
		while(it.hasNext()){
			Filter filter = it.next();
			if(filter.getValue() == null || ("").equals(filter.getValue())){
				it.remove();
			}
		}
		return this;
	}

	/**
	 * 清除所有条件
	 * 
	 * @return 该实例
	 */
	public QueryParams clearAll() {
		if (!this.andFilters.isEmpty())
			this.andFilters.clear();
		if (!this.orFilters.isEmpty())
			this.orFilters.clear();
		if (!this.orders.isEmpty())
			this.orders.clear();
		return this;
	}

	/**
	 * 清除and条件
	 * 
	 * @return 该实例
	 */
	public QueryParams clearAnd() {
		if (!this.andFilters.isEmpty())
			this.andFilters.clear();
		return this;
	}

	/**
	 * 清除or条件
	 * 
	 * @return 该实例
	 */
	public QueryParams clearOr() {
		if (!this.orFilters.isEmpty())
			this.andFilters.clear();
		return this;
	}

	/**
	 * 清除order条件
	 * 
	 * @return 该实例
	 */
	public QueryParams clearOrder() {
		if (!this.orders.isEmpty())
			this.orders.clear();
		return this;
	}

	/**
	 * 检查filter中是否已经存在某字段判断条件
	 *
	 * @param property
	 *         条件字段
	 * @return 有为真，无为假
	 */
	public boolean checkFilterProperty(String property) {
		boolean check = false;
		if(property == null || property.equals("")){
			return check;
		}
		for(Filter filter : andFilters){
			if(null != filter.getProperty() && filter.getProperty().equals(property)) {
				return true;
			}
		}
		for(Filter filter : orFilters){
			if(null != filter.getProperty() && filter.getProperty().equals(property)) {
				return true;
			}
		}
		return check;
	}

	/**
	 * 添加一个having条件
	 *
	 *
     * @param age
     * @param filter
     *            该条件
     * @return 链式调用
	 */
	public QueryParams havingAnd(Filter age, Filter filter) {
		this.andHavings.add((Having) filter);
		return this;
	}

	/**
	 * 添加多个having条件
	 *
	 * @param filter
	 *            该条件
	 * @return 链式调用
	 */
	public QueryParams havingOr(Filter... filter) {
	    List a = Arrays.asList(filter);
	    for(int i=0; i <a.size(); i++){
            this.orHavings.add((Having) a.get(i));
        }
		return this;
	}

    /**
     * 转换为Predicate
     *
     * @param root
     *            Root
     * @return Predicate
     */
    @SuppressWarnings("unchecked")
    public Predicate toHavingAndPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, List<Having> havingList) {
        Predicate restrictions = criteriaBuilder.conjunction();
        if (root == null || CollectionUtils.isEmpty(havingList)) {
            return restrictions;
        }
        for (Having having : havingList) {
            if (having == null) {
                continue;
            }
            String property = having.getFilter().getProperty();
            Filter.Operator operator = having.getFilter().getOperator();
			if(property ==null || ("").equals(property) || operator == null){
				continue;
			}
            Object value = having.getFilter().getValue();
			//如果查询条件为空自动去除，则判断value
			if(criteriaNull) {
				continue;
			}
            Boolean ignoreCase = having.getFilter().getIgnoreCase();
            String methidFunction = having.getMethidFunction();
            Path<?> path = getPath(root, property);
            if (path == null) {
                continue;
            }
            // 根据运算符生成相应条件
            switch (operator) {
                case eq:
                    if (value != null) {
                        if (BooleanUtils.isTrue(ignoreCase) && String.class.isAssignableFrom(path.getJavaType())
                                && value instanceof String) {
                            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
                                    .equal(criteriaBuilder.lower((Path<String>) path), ((String) value).toLowerCase()));
                        } else {
                            if("count".equals(methidFunction))
                                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(criteriaBuilder.count(path), value));
                            else if("avg".equals(methidFunction)){
//								Expression<Number> param = criteriaBuilder.parameter(Number.class);
//								Predicate condition = criteriaBuilder.gt(testEmp.get(Employee_.age), param);
//								param.
							}
                            else
                                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(path, value));
                        }
                    } else {
                        restrictions = criteriaBuilder.and(restrictions, path.isNull());
                    }
                    break;
                case ne:
                    if (value != null) {
                        if (BooleanUtils.isTrue(ignoreCase) && String.class.isAssignableFrom(path.getJavaType())
                                && value instanceof String) {
                            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
                                    .notEqual(criteriaBuilder.lower((Path<String>) path), ((String) value).toLowerCase()));
                        } else {
                            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(path, value));
                        }
                    } else {
                        restrictions = criteriaBuilder.and(restrictions, path.isNotNull());
                    }
                    break;
                case gt:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
                        restrictions = criteriaBuilder.and(restrictions,
                                criteriaBuilder.gt((Path<Number>) path, (Number) value));
                    }
                    break;
                case lt:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
                        restrictions = criteriaBuilder.and(restrictions,
                                criteriaBuilder.lt((Path<Number>) path, (Number) value));
                    }
                    break;
                case ge:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
                        restrictions = criteriaBuilder.and(restrictions,
                                criteriaBuilder.ge((Path<Number>) path, (Number) value));
                    }
                    break;
                case le:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
                        restrictions = criteriaBuilder.and(restrictions,
                                criteriaBuilder.le((Path<Number>) path, (Number) value));
                    }
                    break;
                case like:
                    if (String.class.isAssignableFrom(path.getJavaType()) && value instanceof String) {
                        if (BooleanUtils.isTrue(ignoreCase)) {
                            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
                                    .like(criteriaBuilder.lower((Path<String>) path), ("%"+value+"%").toLowerCase()));
                        } else {
                            restrictions = criteriaBuilder.and(restrictions,
                                    criteriaBuilder.like((Path<String>) path, "%"+value+"%"));
                        }
                    }
                    break;
                case in:
                    restrictions = criteriaBuilder.and(restrictions, path.in(value));
                    break;
                case isNull:
                    restrictions = criteriaBuilder.and(restrictions, path.isNull());
                    break;
                case isNotNull:
                    restrictions = criteriaBuilder.and(restrictions, path.isNotNull());
                    break;
            }
        }
        return restrictions;
    }

	public boolean isCriteriaNull() {
		return criteriaNull;
	}

	public void setCriteriaNull(boolean criteriaNull) {
		this.criteriaNull = criteriaNull;
	}
}