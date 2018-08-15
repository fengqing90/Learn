// import java.io.Serializable;
// import java.util.Collections;
// import java.util.Iterator;
// import java.util.List;
// import java.util.Map;
//
// import org.hibernate.Criteria;
// import org.hibernate.Query;
// import org.hibernate.SQLQuery;
// import org.hibernate.Session;
// import org.hibernate.criterion.Criterion;
// import org.hibernate.criterion.Order;
// import org.hibernate.criterion.Projections;
// import org.hibernate.engine.spi.SessionFactoryImplementor;
// import org.hibernate.hql.internal.ast.QueryTranslatorImpl;
// import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
// import org.springframework.stereotype.Repository;
//
// @Repository
// public class CommonDao<X> extends HibernateDaoSupport {
// /**
// * 根据id获得对象
// * @param <X>
// * @param clazz
// * @param id
// * @return
// */
// public <X> X get(final Class<X> clazz, final Serializable id) {
// return (X) this.session().get(clazz, id);
// }
//
// /** * 删除对象 * @param entity */
// public void delete(final Object entity) {
// this.session().delete(entity);
// }
//
// /** * 根据ID删除对象,load提高性能 * @param <X> * @param clazz * @param id */
// public <X> void delete(final Class<X> clazz, final Serializable id) {
// delete(this.session().load(clazz, id));
// }
//
// /**
// * hql更新 * @param hql * @return
// */
// public int executeUpdate(final String hql) {
// return this.createQuery(hql).executeUpdate();
// }
//
// /**
// * * 有条件参数的hql更新 * @param hql * @param params * @return
// */
// public int executeUpdate(final String hql, List<? extends Object> params) {
// return this.createQuery(hql, params).executeUpdate();
// }
//
// /**
// * * 有条件参数的 hql更新 * @param hql * @param params * @param
// * splitParams参数是否拆分，传入null 或ture表示拆分参数，false参数不拆分，适合只有一个参数的情况 * @return
// */
// public int executeUpdate(final String hql, List<? extends Object> params,
// Boolean splitParams) {
// return this.createQuery(hql, params, splitParams).executeUpdate();
// }
//
// /**
// * hql批量删除实体 * @param hql * @param ids * @return
// */
// public int deletes(final String hql, List<Object> ids) {
// return this.executeUpdate(hql, ids, false);
// }
//
// /** * 批量删除实体，不推荐 * @param <X> * @param entities * @param cacheSize */
// public <X> void deletes(final List<X> entities, final Integer cacheSize) {
// Session session = this.session();
// for (int i = 0; i < entities.size(); i++) {
// X entity = entities.get(i);
// session.delete(entity);
// int defaultCacheSize = 0;
// if (cacheSize == null || cacheSize == 0) {
// defaultCacheSize = 20;
// } else {
// defaultCacheSize = cacheSize;
// }
// if (i % defaultCacheSize == 0) {
// session.flush();
// session.clear();
// }
// }
// }
//
// /** * 保存的实体对象 * @param entity */
// public void merge(final Object entity) {
// getSession().merge(entity);
// }
//
// /** * 批量保存实体,缓存大小默认是20 * @param <X> * @param entities */
// public <X> void merges(final List<X> entities) {
// }
//
// /**
// * * 批量保存实体 * @param <X> * @param entities * @param
// * cacheSize缓存大小，如果传入null或小于0，默认是20
// */
// public <X> void merges(final List<X> entities, final Integer cacheSize) {
// Session session = this.session();
// for (int i = 0; i < entities.size(); i++) {
// X entity = entities.get(i);
// session.merge(entity);
// int defaultCacheSize = 0;
// if (cacheSize == null || cacheSize == 0) {
// defaultCacheSize = 20;
// } else {
// defaultCacheSize = cacheSize;
// }
// if (i % defaultCacheSize == 0) {
// session.flush();
// session.clear();
// }
// }
// }
//
// /** * 没有条件参数的HQL查询单个对象 * @param hql * @return */
// public Object get(final String hql) {
// return this.createQuery(hql).uniqueResult();
// }
//
// /** * 单一条件参数的HQL查询单个对象 * @param hql * @param param * @return */
// public Object get(final String hql, final Object param) {
// return this.createQuery(hql).setParameter(0, param).uniqueResult();
// }
//
// /** * 支持条件参数的HQL查询单个对象 * @param hql * @param params * @return */
// public Object get(final String hql, final List<? extends Object> params) {
// return this.createQuery(hql, params).uniqueResult();
// }
//
// /** * 没有条件参数的HQL查询 * @param <X> * @param hql * @return */
// public <X> List<X> query(final String hql) {
// return this.createQuery(hql).list();
// }
//
// /** * 单一条件参数的HQL查询 * @param <X> * @param hql * @param param * @return */
// public <X> List<X> query(final String hql, final Object param) {
// return this.createQuery(hql).setParameter(0, param).list();
// }
//
// /**
// * 支持条件参数的HQL查询 * @param <X> * @param listHql * @return
// */
// public <X> List<X> query(final String hql, final List<? extends Object>
// params) {
// return this.createQuery(hql, params).list();
// }
//
// /** * 没有条件和排序参数的对象化查询 * @param <X> * @param clazz * @return */
// public <X> List<X> query(final Class<X> clazz) {
// return this.createCriteria(clazz).list();
// }
//
// /** * 单一条件的对象化查询 * @param <X> * @param clazz * @param criterion * @return */
// public <X> List<X> query(final Class<X> clazz, final Criterion criterion) {
// return this.createCriteria(clazz).add(criterion).list();
// }
//
// /** * 支持条件没有排序的对象化查询 * @param <X> * @param clazz * @param criterions *
// @return */
// public <X> List<X> query(final Class<X> clazz, final List<Criterion>
// criterions) {
// return this.query(clazz, criterions, null);
// }
//
// /** * 支持条件和排序参数的对象化查询 * @param <X> * @param clazz * @return */
// public <X> List<X> query(final Class<X> clazz, final List<Criterion>
// criterions, final List<Order> orders) {
// return this.createCriteria(clazz, criterions, orders).list();
// }
//
// /**
// * Hibernate HQL查询分页,参数按索引号传递 * @param <X> * @param listHql * @param params
// * * @param firstResult * @param maxResults * @param itemsTotal * @return
// */
// public Page<X> queryByPage(final String listHql, final List<? extends Object>
// params, final Integer firstResult,
// final Integer maxResults, final Long totalCount) {
// if (listHql == null || listHql.trim().length() == 0) {
// return null;
// }
// Page<X> page = new Page<X>();
// Query listQuery = this.createQuery(listHql, params); // 设置开始检索的起始记录
// listQuery.setFirstResult(firstResult);
//
// // 设置每次检索返回的最大对象数
// listQuery.setMaxResults(maxResults);
// List<X> list = listQuery.list();
// page.setResults(list); // 如果总数已经存在，则不查询
// if (totalCount > 0) {
// page.setTotalCount(totalCount);
// } else {
// String countHql = listHql;
// int order_index = countHql.toUpperCase().lastIndexOf("ORDER");
// if (order_index != -1) {
// countHql = countHql.substring(0, order_index);
// } // 通过转成SQL来进行查询，解决
// // hql不能在distinct，group by结果集上使用count的问题
// QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(null, countHql,
// Collections.EMPTY_MAP,
// (SessionFactoryImplementor) getSession().getSessionFactory());
// queryTranslator.compile(Collections.EMPTY_MAP, false);
// countHql = new
// StringBuffer("select count(*) from (").append(queryTranslator.getSQLString())
// .append(") tmp_count_table").toString();
// SQLQuery countQuery = this.createSQLQuery(countHql, params);
// page.setTotalCount(Long.valueOf(countQuery.uniqueResult().toString()));
// return page;
// }
// }
//
// /**
// * Hibernate HQL查询分页,参数按索key传递
// *
// * @param <X>
// * @param listHql
// * @param params
// * @param firstResult
// * @param maxResults
// * @param itemsTotal
// * @return
// */
// public Page<X> queryByPage(final String listHql, final Map<String, ? extends
// Object> namedParames,
// final Integer firstResult, final Integer maxResults, final Long totalCount) {
// if (listHql == null || listHql.trim().length() == 0) {
// return null;
// }
// Page<X> page = new Page<X>();
// Query listQuery = this.createQuery(listHql, namedParames); // 设置开始检索的起始记录
// listQuery.setFirstResult(firstResult);
//
// // 设置每次检索返回的最大对象数
// listQuery.setMaxResults(maxResults);
// List<X> list = listQuery.list();
// page.setResults(list);
// // 如果总数已经存在，则不查询
// if (totalCount > 0) {
// page.setTotalCount(totalCount);
// } else {
// String countHql = listHql;
// int order_index = countHql.toUpperCase().lastIndexOf("ORDER");
// if (order_index != -1) {
// countHql = countHql.substring(0, order_index);
// }
// }
// // 通过转成SQL来进行查询，解决 hql不能在distinct，group by结果集上使用count的问题
// QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(null, countHql,
// Collections.EMPTY_MAP,
// (SessionFactoryImplementor) getSession().getSessionFactory());
// queryTranslator.compile(Collections.EMPTY_MAP, false);
// countHql = new
// StringBuffer("select count(*) from (").append(queryTranslator.getSQLString())
// .append(") tmp_count_table").toString();
// SQLQuery countQuery = this.createSQLQuery(countHql, namedParames);
// page.setTotalCount(Long.valueOf(countQuery.uniqueResult().toString()));
// return page;
// }
//
// /**
// * Hibernate面向对象的方法查询分页 * * @param <X> * @param clazz * @param criterions *
// @param
// * orders * @param firstResult * @param maxResults * @param itemsTotal *
// @return
// */
// public Page<X> queryByPage(final Class<X> clazz, final List<Criterion>
// criterions, final List<Order> orders,
// final Integer firstResult, final Integer maxResults, final Long totalCount) {
// Criteria criteria = this.createCriteria(clazz, criterions, orders);
// return this.queryByPage(criteria, firstResult, maxResults, totalCount);
// }
//
// /**
// * Hibernate面向对象的方法查询分页 * @param <X> * @param criteria * @param firstResult
// * * @param maxResults * @param totalCount * @return
// */
// public Page<X> queryByPage(final Criteria criteria, final Integer
// firstResult, final Integer maxResults,
// final Long totalCount) {
// Page<X> page = new Page<X>(); // 查询记录总数
// if (totalCount > 0) {
// page.setTotalCount(totalCount);
// } else {
// // 缓存总数(点击页码的时候没必要计算总数，只有查询提交的时候才重新查询数据库)
// page.setTotalCount(Long.valueOf((criteria.setProjection(Projections.rowCount()).uniqueResult()).toString()));
// // 记得要把它设置为null,否则会报错
// criteria.setProjection(null);
// }
// // 设置分页 // 分页从那条记录开始
// criteria.setFirstResult(firstResult);
// // 每页多少条记录
// criteria.setMaxResults(maxResults);
// page.setResults(criteria.list());
// return page;
// }
//
// private Session session() {
// return getSession();
// }
//
// private Query createQuery(final String hql) {
// return this.session().createQuery(hql);
// }
//
// private Query createQuery(final String hql, final List<? extends Object>
// params) {
// return this.createQuery(hql, params, null);
// }
//
// private Query createQuery(final String hql, final List<? extends Object>
// params, Boolean splitParams) {
// Query query = this.createQuery(hql);
// if (splitParams == null || splitParams == true) {
// if (params != null && !params.isEmpty()) {
// int size = params.size();
// for (int i = 0; i < size; i++) {
// query.setParameter(i, params.get(i));
// }
// }
// } else {
// if (params != null && !params.isEmpty()) {
// query.setParameter(0, params);
// }
// }
// return query;
// }
//
// private Query createQuery(final String hql, final Map<String, ? extends
// Object> namedParames) {
// Query query = this.createQuery(hql);
// if (namedParames != null && !namedParames.isEmpty()) {
// Iterator iter = namedParames.entrySet().iterator();
// while (iter.hasNext()) {
// Map.Entry entry = (Map.Entry) iter.next();
// query.setParameter((String) entry.getKey(), entry.getValue());
// }
// }
// return query;
// }
//
// private SQLQuery createSQLQuery(final String sql) {
// return this.session().createSQLQuery(sql);
// }
//
// private SQLQuery createSQLQuery(final String sql, final List<? extends
// Object> params) {
// return this.createSQLQuery(sql, params, null);
// }
//
// private SQLQuery createSQLQuery(final String sql, final List<? extends
// Object> params, Boolean splitParams) {
// SQLQuery sqlQuery = this.createSQLQuery(sql);
// if (splitParams == null || splitParams == false) {
// if (params != null && !params.isEmpty()) {
// int size = params.size();
// for (int i = 0; i < size; i++) {
// sqlQuery.setParameter(i, params.get(i));
// }
// }
// } else {
// if (params != null && !params.isEmpty()) {
// sqlQuery.setParameter(0, params);
// }
// }
// return sqlQuery;
// }
//
// private SQLQuery createSQLQuery(final String sql, final Map<String, ? extends
// Object> namedParames) {
// SQLQuery sqlQuery = this.createSQLQuery(sql);
// if (namedParames != null && !namedParames.isEmpty()) {
// Iterator iter = namedParames.entrySet().iterator();
// while (iter.hasNext()) {
// Map.Entry entry = (Map.Entry) iter.next();
// sqlQuery.setParameter((String) entry.getKey(), entry.getValue());
// }
// }
// return sqlQuery;
// }
//
// /** * 获取Criteria * @param <X> * @param clazz * @return */
// public <X> Criteria createCriteria(final Class<X> clazz) {
// return this.session().createCriteria(clazz);
// }
//
// /**
// * * 获取Criteria * @param <X> * @param clazz * @param criterions * @param
// * orders * @return
// */
// public <X> Criteria createCriteria(final Class<X> clazz, final
// List<Criterion> criterions, final List<Order> orders) {
// Criteria criteria = this.createCriteria(clazz); // 添加查询条件
// if (criterions != null && !criterions.isEmpty()) {
// for (Criterion c : criterions) {
// criteria.add(c);
// }
// // 添加排序条件
// if (orders != null && !orders.isEmpty()) {
// for (Order order : orders) {
// criteria.addOrder(order);
// }
// }
// }
// return criteria;
// }
//}
