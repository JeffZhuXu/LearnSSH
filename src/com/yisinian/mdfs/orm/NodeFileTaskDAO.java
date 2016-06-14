package com.yisinian.mdfs.orm;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * NodeFileTask entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.yisinian.mdfs.orm.NodeFileTask
 * @author MyEclipse Persistence Tools
 */

public class NodeFileTaskDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(NodeFileTaskDAO.class);
	// property constants
	public static final String NODE_ID = "nodeId";
	public static final String FILE_ID = "fileId";
	public static final String BLOCK_ID = "blockId";
	public static final String GET_STATE = "getState";
	public static final String GET_TIME = "getTime";

	protected void initDao() {
		// do nothing
	}

	public void save(NodeFileTask transientInstance) {
		log.debug("saving NodeFileTask instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(NodeFileTask persistentInstance) {
		log.debug("deleting NodeFileTask instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public NodeFileTask findById(java.lang.Integer id) {
		log.debug("getting NodeFileTask instance with id: " + id);
		try {
			NodeFileTask instance = (NodeFileTask) getHibernateTemplate().get(
					"com.yisinian.mdfs.orm.NodeFileTask", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(NodeFileTask instance) {
		log.debug("finding NodeFileTask instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding NodeFileTask instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from NodeFileTask as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByNodeId(Object nodeId) {
		return findByProperty(NODE_ID, nodeId);
	}

	public List findByFileId(Object fileId) {
		return findByProperty(FILE_ID, fileId);
	}

	public List findByBlockId(Object blockId) {
		return findByProperty(BLOCK_ID, blockId);
	}

	public List findByGetState(Object getState) {
		return findByProperty(GET_STATE, getState);
	}

	public List findByGetTime(Object getTime) {
		return findByProperty(GET_TIME, getTime);
	}

	public List findAll() {
		log.debug("finding all NodeFileTask instances");
		try {
			String queryString = "from NodeFileTask";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public NodeFileTask merge(NodeFileTask detachedInstance) {
		log.debug("merging NodeFileTask instance");
		try {
			NodeFileTask result = (NodeFileTask) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(NodeFileTask instance) {
		log.debug("attaching dirty NodeFileTask instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(NodeFileTask instance) {
		log.debug("attaching clean NodeFileTask instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static NodeFileTaskDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (NodeFileTaskDAO) ctx.getBean("NodeFileTaskDAO");
	}
	
	//自定义升级某个字段
	public void update(NodeFileTask transientInstance){
		log.debug("update Ip instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("update successful");
		} catch (RuntimeException e) {
			// TODO: handle exception
			log.error("update failed");
			throw e;
		}
	}
}