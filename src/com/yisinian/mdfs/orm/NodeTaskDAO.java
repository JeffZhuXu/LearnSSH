package com.yisinian.mdfs.orm;

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * NodeTask entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.yisinian.mdfs.orm.NodeTask
 * @author MyEclipse Persistence Tools
 */

public class NodeTaskDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(NodeTaskDAO.class);
	// property constants
	public static final String TASK_ID = "taskId";
	public static final String TYPE = "type";
	public static final String FILE_ID = "fileId";
	public static final String BLOCK_ID = "blockId";
	public static final String NODE_ID = "nodeId";
	public static final String CONTENT = "content";
	public static final String FINISH_TIME = "finishTime";
	public static final String STATE = "state";
	public static final String BUILD_TIME = "buildTime";

	protected void initDao() {
		// do nothing
	}

	public void save(NodeTask transientInstance) {
		log.debug("saving NodeTask instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(NodeTask persistentInstance) {
		log.debug("deleting NodeTask instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public NodeTask findById(java.lang.Integer id) {
		log.debug("getting NodeTask instance with id: " + id);
		try {
			NodeTask instance = (NodeTask) getHibernateTemplate().get(
					"com.yisinian.mdfs.orm.NodeTask", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(NodeTask instance) {
		log.debug("finding NodeTask instance by example");
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
		log.debug("finding NodeTask instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from NodeTask as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTaskId(Object taskId) {
		return findByProperty(TASK_ID, taskId);
	}

	public List findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findByFileId(Object fileId) {
		return findByProperty(FILE_ID, fileId);
	}

	public List findByBlockId(Object blockId) {
		return findByProperty(BLOCK_ID, blockId);
	}

	public List findByNodeId(Object nodeId) {
		return findByProperty(NODE_ID, nodeId);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findByFinishTime(Object finishTime) {
		return findByProperty(FINISH_TIME, finishTime);
	}

	public List findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findByBuildTime(Object buildTime) {
		return findByProperty(BUILD_TIME, buildTime);
	}

	public List findAll() {
		log.debug("finding all NodeTask instances");
		try {
			String queryString = "from NodeTask";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public NodeTask merge(NodeTask detachedInstance) {
		log.debug("merging NodeTask instance");
		try {
			NodeTask result = (NodeTask) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(NodeTask instance) {
		log.debug("attaching dirty NodeTask instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(NodeTask instance) {
		log.debug("attaching clean NodeTask instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static NodeTaskDAO getFromApplicationContext(ApplicationContext ctx) {
		return (NodeTaskDAO) ctx.getBean("NodeTaskDAO");
	}
	//找到某个节点上未执行的任务
	public List findByNodeIdAndState(Object nodeId) {
		log.debug("finding NodeTask instance with property: " + NODE_ID
				+ ", value: " + nodeId);
		try {
			String queryString = "from NodeTask as model where model."
					+ NODE_ID + "= ?" +" and model.state='0'";
			return getHibernateTemplate().find(queryString, nodeId);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	//找到摸个任务完成的子任务个数
	public String findFinishedNodeTaskNum(Object taskId) {
		log.debug("finding NodeTask instance with property: " + NODE_ID
				+ ", value: " + taskId);
		try {
			String queryString = "select count(*) from NodeTask as model where model."
					+ TASK_ID + "= ?" +" and model.state='1'";
			return getHibernateTemplate().find(queryString, taskId).get(0)+"";
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}