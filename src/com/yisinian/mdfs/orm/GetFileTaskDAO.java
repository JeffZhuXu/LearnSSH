package com.yisinian.mdfs.orm;

import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * GetFileTask entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.yisinian.mdfs.orm.GetFileTask
 * @author MyEclipse Persistence Tools
 */

public class GetFileTaskDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(GetFileTaskDAO.class);
	// property constants
	public static final String FILE_ID = "fileId";
	public static final String GET_FILE_NUM = "getFileNum";
	public static final String NEED_FILE_NUM = "needFileNum";
	public static final String ALL_FILE_NUM = "allFileNum";
	public static final String FILE_TYPE = "fileType";
	public static final String SET_TIME = "setTime";
	public static final String SUCESS = "sucess";

	protected void initDao() {
		// do nothing
	}

	public void save(GetFileTask transientInstance) {
		log.debug("saving GetFileTask instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(GetFileTask persistentInstance) {
		log.debug("deleting GetFileTask instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public GetFileTask findById(java.lang.Integer id) {
		log.debug("getting GetFileTask instance with id: " + id);
		try {
			GetFileTask instance = (GetFileTask) getHibernateTemplate().get(
					"com.yisinian.mdfs.orm.GetFileTask", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(GetFileTask instance) {
		log.debug("finding GetFileTask instance by example");
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
		log.debug("finding GetFileTask instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from GetFileTask as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFileId(Object fileId) {
		return findByProperty(FILE_ID, fileId);
	}

	public List findByGetFileNum(Object getFileNum) {
		return findByProperty(GET_FILE_NUM, getFileNum);
	}

	public List findByNeedFileNum(Object needFileNum) {
		return findByProperty(NEED_FILE_NUM, needFileNum);
	}

	public List findByAllFileNum(Object allFileNum) {
		return findByProperty(ALL_FILE_NUM, allFileNum);
	}

	public List findByFileType(Object fileType) {
		return findByProperty(FILE_TYPE, fileType);
	}

	public List findBySetTime(Object setTime) {
		return findByProperty(SET_TIME, setTime);
	}

	public List findBySucess(Object sucess) {
		return findByProperty(SUCESS, sucess);
	}

	public List findAll() {
		log.debug("finding all GetFileTask instances");
		try {
			String queryString = "from GetFileTask";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public GetFileTask merge(GetFileTask detachedInstance) {
		log.debug("merging GetFileTask instance");
		try {
			GetFileTask result = (GetFileTask) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(GetFileTask instance) {
		log.debug("attaching dirty GetFileTask instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(GetFileTask instance) {
		log.debug("attaching clean GetFileTask instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static GetFileTaskDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (GetFileTaskDAO) ctx.getBean("GetFileTaskDAO");
	}
	//自定义升级某个字段
	public void update(GetFileTask transientInstance){
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