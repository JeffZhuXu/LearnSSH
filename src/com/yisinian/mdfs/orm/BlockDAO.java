package com.yisinian.mdfs.orm;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Block
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.yisinian.mdfs.orm.Block
 * @author MyEclipse Persistence Tools
 */

public class BlockDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(BlockDAO.class);
	// property constants
	public static final String FILE_ID = "fileId";
	public static final String NODE_ID = "nodeId";
	public static final String BLOCK_NAME = "blockName";
	public static final String FILE_SERIAL_NUMBER = "fileSerialNumber";
	public static final String BLOCK_PATH = "blockPath";
	public static final String BLOCK_SIZE = "blockSize";
	public static final String BLOCK_SET_TIME = "blockSetTime";
	public static final String STATE = "state";
	public static final String STATE_TIME = "stateTime";
	public static final String DOWNLOAD_TIME = "downloadTime";

	protected void initDao() {
		// do nothing
	}

	public void save(Block transientInstance) {
		log.debug("saving Block instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Block persistentInstance) {
		log.debug("deleting Block instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Block findById(java.lang.Integer id) {
		log.debug("getting Block instance with id: " + id);
		try {
			Block instance = (Block) getHibernateTemplate().get(
					"com.yisinian.mdfs.orm.Block", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Block instance) {
		log.debug("finding Block instance by example");
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
		log.debug("finding Block instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Block as model where model."
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

	public List findByNodeId(Object nodeId) {
		return findByProperty(NODE_ID, nodeId);
	}

	public List findByBlockName(Object blockName) {
		return findByProperty(BLOCK_NAME, blockName);
	}

	public List findByFileSerialNumber(Object fileSerialNumber) {
		return findByProperty(FILE_SERIAL_NUMBER, fileSerialNumber);
	}

	public List findByBlockPath(Object blockPath) {
		return findByProperty(BLOCK_PATH, blockPath);
	}

	public List findByBlockSize(Object blockSize) {
		return findByProperty(BLOCK_SIZE, blockSize);
	}

	public List findByBlockSetTime(Object blockSetTime) {
		return findByProperty(BLOCK_SET_TIME, blockSetTime);
	}

	public List findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findByStateTime(Object stateTime) {
		return findByProperty(STATE_TIME, stateTime);
	}

	public List findByDownloadTime(Object downloadTime) {
		return findByProperty(DOWNLOAD_TIME, downloadTime);
	}

	public List findAll() {
		log.debug("finding all Block instances");
		try {
			String queryString = "from Block";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Block merge(Block detachedInstance) {
		log.debug("merging Block instance");
		try {
			Block result = (Block) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Block instance) {
		log.debug("attaching dirty Block instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Block instance) {
		log.debug("attaching clean Block instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BlockDAO getFromApplicationContext(ApplicationContext ctx) {
		return (BlockDAO) ctx.getBean("BlockDAO");
	}
	
	//查找所有块的个数的个数
	public String countAllNumber() {
		log.debug("select count(*) from Block");
		try {
			String queryString = "select count(*) from Block";
			return getHibernateTemplate().find(queryString).get(0).toString();
		} catch (RuntimeException re) {
			log.error("find Block countAllNumber failed", re);
			throw re;
		}
	}
	
	
	//查找指定文件的所有块的个数
	public String countAllNumberForOneFile(String fileId) {
		log.debug("select count(*) from Block");
		try {
			String queryString = "select count(*) from Block where "+FILE_ID+"="+fileId;
			return getHibernateTemplate().find(queryString).get(0).toString();
		} catch (RuntimeException re) {
			log.error("find Block countAllNumber failed", re);
			throw re;
		}
	}
	//查找指定文件的所有块的总的下载时间
	public String countTotalDownloadTimeForOneFileByFileId(String fileId) {
		log.debug("select sum(downloadTime) from Block where fileId = ..");
		try {
			String queryString = "SELECT sum(downloadTime) from Block where "+FILE_ID+"="+fileId;
			return getHibernateTemplate().find(queryString).get(0).toString();
		} catch (RuntimeException re) {
			log.error("find Block countAllNumber failed", re);
			throw re;
		}
	}
	//自定义升级某个字段
	public void update(Block transientInstance){
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