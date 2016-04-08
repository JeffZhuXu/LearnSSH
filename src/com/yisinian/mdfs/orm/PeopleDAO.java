package com.yisinian.mdfs.orm;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * People entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.yisinian.mdfs.orm.People
 * @author MyEclipse Persistence Tools
 */

public class PeopleDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(PeopleDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String AGE = "age";
	public static final String SALARY = "salary";

	protected void initDao() {
		// do nothing
	}

	public void save(People transientInstance) {
		log.debug("saving People instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(People persistentInstance) {
		log.debug("deleting People instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public People findById(java.lang.Integer id) {
		log.debug("getting People instance with id: " + id);
		try {
			People instance = (People) getHibernateTemplate().get(
					"com.yisinian.mdfs.orm.People", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(People instance) {
		log.debug("finding People instance by example");
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
		log.debug("finding People instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from People as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByAge(Object age) {
		return findByProperty(AGE, age);
	}

	public List findBySalary(Object salary) {
		return findByProperty(SALARY, salary);
	}

	public List findAll() {
		log.debug("finding all People instances");
		try {
			String queryString = "from People";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public People merge(People detachedInstance) {
		log.debug("merging People instance");
		try {
			People result = (People) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(People instance) {
		log.debug("attaching dirty People instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(People instance) {
		log.debug("attaching clean People instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PeopleDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PeopleDAO) ctx.getBean("PeopleDAO");
	}
	//自定义升级某个字段
	public void update(People transientInstance){
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