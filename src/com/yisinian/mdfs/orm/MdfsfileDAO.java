package com.yisinian.mdfs.orm;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Mdfsfile entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.yisinian.mdfs.orm.Mdfsfile
 * @author MyEclipse Persistence Tools
 */

public class MdfsfileDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(MdfsfileDAO.class);
	// property constants
	public static final String FILE_NAME = "fileName";
	public static final String FILE_STORAGE_NAME = "fileStorageName";
	public static final String FILE_SIZE = "fileSize";
	public static final String UPLOAD_TIME = "uploadTime";
	public static final String BLOCK_NUMBER = "blockNumber";
	public static final String DELETE_STATUS = "deleteStatus";
	public static final String DELETE_TIME = "deleteTime";
	public static final String FILE_PATH = "filePath";
	public static final String FILE_DOWNLOAD_TIME = "fileDownloadTime";
	public static final String PROCESS_TIME = "processTime";

	protected void initDao() {
		// do nothing
	}

	public void save(Mdfsfile transientInstance) {
		log.debug("saving Mdfsfile instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Mdfsfile persistentInstance) {
		log.debug("deleting Mdfsfile instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Mdfsfile findById(java.lang.Integer id) {
		log.debug("getting Mdfsfile instance with id: " + id);
		try {
			Mdfsfile instance = (Mdfsfile) getHibernateTemplate().get(
					"com.yisinian.mdfs.orm.Mdfsfile", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Mdfsfile instance) {
		log.debug("finding Mdfsfile instance by example");
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
		log.debug("finding Mdfsfile instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Mdfsfile as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFileName(Object fileName) {
		return findByProperty(FILE_NAME, fileName);
	}

	public List findByFileStorageName(Object fileStorageName) {
		return findByProperty(FILE_STORAGE_NAME, fileStorageName);
	}

	public List findByFileSize(Object fileSize) {
		return findByProperty(FILE_SIZE, fileSize);
	}

	public List findByUploadTime(Object uploadTime) {
		return findByProperty(UPLOAD_TIME, uploadTime);
	}

	public List findByBlockNumber(Object blockNumber) {
		return findByProperty(BLOCK_NUMBER, blockNumber);
	}

	public List findByDeleteStatus(Object deleteStatus) {
		return findByProperty(DELETE_STATUS, deleteStatus);
	}

	public List findByDeleteTime(Object deleteTime) {
		return findByProperty(DELETE_TIME, deleteTime);
	}

	public List findByFilePath(Object filePath) {
		return findByProperty(FILE_PATH, filePath);
	}

	public List findByFileDownloadTime(Object fileDownloadTime) {
		return findByProperty(FILE_DOWNLOAD_TIME, fileDownloadTime);
	}

	public List findByProcessTime(Object processTime) {
		return findByProperty(PROCESS_TIME, processTime);
	}

	public List findAll() {
		log.debug("finding all Mdfsfile instances");
		try {
			String queryString = "from Mdfsfile";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Mdfsfile merge(Mdfsfile detachedInstance) {
		log.debug("merging Mdfsfile instance");
		try {
			Mdfsfile result = (Mdfsfile) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Mdfsfile instance) {
		log.debug("attaching dirty Mdfsfile instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Mdfsfile instance) {
		log.debug("attaching clean Mdfsfile instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MdfsfileDAO getFromApplicationContext(ApplicationContext ctx) {
		return (MdfsfileDAO) ctx.getBean("MdfsfileDAO");
	}
	
	//查找所有文件的个数
	public String countAllNumber() {
		log.debug("select count(*) from Mdfsfile");
		try {
			String queryString = "select count(*) from Mdfsfile";
			return getHibernateTemplate().find(queryString).get(0).toString();
		} catch (RuntimeException re) {
			log.error("find countAllNumber failed", re);
			throw re;
		}
	}

	//自定义升级某个字段
	public void update(Mdfsfile transientInstance){
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