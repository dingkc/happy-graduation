package com.bttc.HappyGraduation.utils.datebase;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    //根据对象bean进行查询,默认只支持and，以及 条件等于
    public List<T> getBeans(T element);

    //根据对象bean进行查询，并支持分页，识别条件为空
    public Page<T> getBeans(T element, int pageNumber, int pageSize);

    //根据对象bean进行查询，并支持排序和分页，识别条件为空
    public Page<T> getBeans(T element, List<Order> orderList, int pageNumber, int pageSize);

    //根据查询对象查询，并自动排除字段值为空的查询条件
    public Page<T> getBeans(QueryParams<T> queryParams, int pageNumber, int pageSize);

    //根据对象bean进行查询，支持条件扩展，识别条件为空
    public List<T> getBeans(T element, QueryParams<T> queryParams);

    //根据对象bean进行查询，支持条件扩展，并支持排序和分页，识别条件为空
    public Page<T> getBeans(T element, QueryParams<T> queryParams, int pageNumber, int pageSize);

    //根据对象bean以及查询列支持分组查询
    public List<Map<String, Object>> getBeans(Select<T> qp, T t);

    //根据对象bean进行查询,默认只支持and，以及 条件等于，自动忽略条件为空情况
    public List<T> getBeansAutoExceptNull(T element);

    //根据对象bean进行查询，并支持分页，自动忽略条件为空情况
    public Page<T> getBeansAutoExceptNull(T element, int pageNumber, int pageSize);

    //根据对象bean进行查询，并支持排序和分页，自动忽略条件为空情况
    public Page<T> getBeansAutoExceptNull(T element, List<Order> orderList, int pageNumber, int pageSize);

    //根据对象bean进行查询，支持条件扩展，自动忽略条件为空情况
    public List<T> getBeansAutoExceptNull(T element, QueryParams<T> queryParams);

    //根据对象bean进行查询，支持条件扩展，并支持排序和分页，自动忽略条件为空情况
    public Page<T> getBeansAutoExceptNull(T element, QueryParams<T> queryParams, int pageNumber, int pageSize);

    //根据主键和对象更新beans
    public T updateBeans(ID id, T entity);

    //更新实体，自动获取主键查询
    public T updateBeans(T entity);

    //根据条件批量删除
    public void batchDelete(QueryParams<T> queryParams);

    //根据条件批量删除
    public void batchUpdate(List<T> entities);
}
