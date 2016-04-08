package com.yisinian.mdfs.orm;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * System entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.yisinian.mdfs.orm.System
 * @author MyEclipse Persistence Tools
 */

public class SystemDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(SystemDAO.class);
	// property constants
	public static final String SYSTEM_NAME = "systemName";
	public static final String SYSTEM_PASSWORD = "systemPassword";
	public static final String ESTABLISH_TIME = "establishTime";
	public static final String INTRODUCTION = "introduction";
	public static final String NODE_NUM = "nodeNum";
	public static final String LIVE_NODE_NUM = "liveNodeNum";
	public static final String SLEEP_NODE_NUM = "sleepNodeNum";
	public static final String DEAD_NODE_NUM = "deadNodeNum";
	public static final String TOTAL_STORAGE = "totalStorage";
	public static final String REST_STORAGE = "restStorage";
	public static final String LIVE_STORAGE = "liveStorage";
	public static final String BLOCK_SIZE = "blockSize";
	public static final String BACKUP_TIME = "backupTime";
	public static final String NET_SPEED = "netSpeed";
	public static final String FILE_NUM = "fileNum";
	public static final String BLOCK_NUM = "blockNum";
	public static final String LIVE_BLOCK_NUM = "liveBlockNum";
	public static final String EXTEND_STORAGE = "extendStorage";
	public static final String LIVE_EXTEND_STORAGE = "liveExtendStorage";
	public static final String CPU_NUM = "cpuNum";
	public static final String LIVE_CPU_NUM = "liveCpuNum";
	public static final String CPU_FREQUENCY = "cpuFrequency";
	public static final String LIVE_CPU_FREQUENCY = "liveCpuFrequency";
	public static final String RAM = "ram";
	public static final String LIVE_RAM = "liveRam";
	public static final String COMPRESS = "compress";
	public static final String ADAPT_TRANSMISSION = "adaptTransmission";

	protected void initDao() {
		// do nothing
	}

	public void save(System transientInstance) {
		log.debug("saving System instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(System persistentInstance) {
		log.debug("deleting System instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public System findById(java.lang.Integer id) {
		log.debug("getting System instance with id: " + id);
		try {
			System instance = (System) getHibernateTemplate().get(
					"com.yisinian.mdfs.orm.System", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(System instance) {
		log.debug("finding System instance by example");
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
		log.debug("finding System instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from System as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySystemName(Object systemName) {
		return findByProperty(SYSTEM_NAME, systemName);
	}

	public List findBySystemPassword(Object systemPassword) {
		return findByProperty(SYSTEM_PASSWORD, systemPassword);
	}

	public List findByEstablishTime(Object establishTime) {
		return findByProperty(ESTABLISH_TIME, establishTime);
	}

	public List findByIntroduction(Object introduction) {
		return findByProperty(INTRODUCTION, introduction);
	}

	public List findByNodeNum(Object nodeNum) {
		return findByProperty(NODE_NUM, nodeNum);
	}

	public List findByLiveNodeNum(Object liveNodeNum) {
		return findByProperty(LIVE_NODE_NUM, liveNodeNum);
	}

	public List findBySleepNodeNum(Object sleepNodeNum) {
		return findByProperty(SLEEP_NODE_NUM, sleepNodeNum);
	}

	public List findByDeadNodeNum(Object deadNodeNum) {
		return findByProperty(DEAD_NODE_NUM, deadNodeNum);
	}

	public List findByTotalStorage(Object totalStorage) {
		return findByProperty(TOTAL_STORAGE, totalStorage);
	}

	public List findByRestStorage(Object restStorage) {
		return findByProperty(REST_STORAGE, restStorage);
	}

	public List findByLiveStorage(Object liveStorage) {
		return findByProperty(LIVE_STORAGE, liveStorage);
	}

	public List findByBlockSize(Object blockSize) {
		return findByProperty(BLOCK_SIZE, blockSize);
	}

	public List findByBackupTime(Object backupTime) {
		return findByProperty(BACKUP_TIME, backupTime);
	}

	public List findByNetSpeed(Object netSpeed) {
		return findByProperty(NET_SPEED, netSpeed);
	}

	public List findByFileNum(Object fileNum) {
		return findByProperty(FILE_NUM, fileNum);
	}

	public List findByBlockNum(Object blockNum) {
		return findByProperty(BLOCK_NUM, blockNum);
	}

	public List findByLiveBlockNum(Object liveBlockNum) {
		return findByProperty(LIVE_BLOCK_NUM, liveBlockNum);
	}

	public List findByExtendStorage(Object extendStorage) {
		return findByProperty(EXTEND_STORAGE, extendStorage);
	}

	public List findByLiveExtendStorage(Object liveExtendStorage) {
		return findByProperty(LIVE_EXTEND_STORAGE, liveExtendStorage);
	}

	public List findByCpuNum(Object cpuNum) {
		return findByProperty(CPU_NUM, cpuNum);
	}

	public List findByLiveCpuNum(Object liveCpuNum) {
		return findByProperty(LIVE_CPU_NUM, liveCpuNum);
	}

	public List findByCpuFrequency(Object cpuFrequency) {
		return findByProperty(CPU_FREQUENCY, cpuFrequency);
	}

	public List findByLiveCpuFrequency(Object liveCpuFrequency) {
		return findByProperty(LIVE_CPU_FREQUENCY, liveCpuFrequency);
	}

	public List findByRam(Object ram) {
		return findByProperty(RAM, ram);
	}

	public List findByLiveRam(Object liveRam) {
		return findByProperty(LIVE_RAM, liveRam);
	}

	public List findByCompress(Object compress) {
		return findByProperty(COMPRESS, compress);
	}

	public List findByAdaptTransmission(Object adaptTransmission) {
		return findByProperty(ADAPT_TRANSMISSION, adaptTransmission);
	}

	public List findAll() {
		log.debug("finding all System instances");
		try {
			String queryString = "from System";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public System merge(System detachedInstance) {
		log.debug("merging System instance");
		try {
			System result = (System) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(System instance) {
		log.debug("attaching dirty System instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(System instance) {
		log.debug("attaching clean System instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static SystemDAO getFromApplicationContext(ApplicationContext ctx) {
		return (SystemDAO) ctx.getBean("SystemDAO");
	}
	
	//自定义升级某个字段
	public void update(System transientInstance){
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