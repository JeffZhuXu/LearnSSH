package com.yisinian.mdfs.orm;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Node
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.yisinian.mdfs.orm.Node
 * @author MyEclipse Persistence Tools
 */

public class NodeDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(NodeDAO.class);
	// property constants
	public static final String NODE_ID = "nodeId";
	public static final String SYSTEM_ID = "systemId";
	public static final String NODE_NAME = "nodeName";
	public static final String NODE_PASSWORD = "nodePassword";
	public static final String COMPANY = "company";
	public static final String PHONE_TYPE = "phoneType";
	public static final String OS = "os";
	public static final String OS_VERSION = "osVersion";
	public static final String LOCAL_STORAGE = "localStorage";
	public static final String REST_LOCAL_STORAGE = "restLocalStorage";
	public static final String STORAGE = "storage";
	public static final String REST_STORAGE = "restStorage";
	public static final String RAM = "ram";
	public static final String CPU_FREQUENCY = "cpuFrequency";
	public static final String CORE_NUMBER = "coreNumber";
	public static final String NET_TYPE = "netType";
	public static final String NET_SPEED = "netSpeed";
	public static final String PHONE_MODEL = "phoneModel";
	public static final String IMEL = "imel";
	public static final String SERIAL_NUMBER = "serialNumber";
	public static final String JPUSH_ID = "jpushId";
	public static final String STATE = "state";
	public static final String START_TIME = "startTime";
	public static final String END_TIME = "endTime";

	protected void initDao() {
		// do nothing
	}

	public void save(Node transientInstance) {
		log.debug("saving Node instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Node persistentInstance) {
		log.debug("deleting Node instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Node findById(String nodeId) {
		log.debug("getting Node instance with id: " + nodeId);
		try {
			Node instance = (Node) getHibernateTemplate().get(
					"com.yisinian.mdfs.orm.Node", nodeId);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Node instance) {
		log.debug("finding Node instance by example");
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
		log.debug("finding Node instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Node as model where model."
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

	public List findBySystemId(Object systemId) {
		return findByProperty(SYSTEM_ID, systemId);
	}

	public List findByNodeName(Object nodeName) {
		return findByProperty(NODE_NAME, nodeName);
	}

	public List findByNodePassword(Object nodePassword) {
		return findByProperty(NODE_PASSWORD, nodePassword);
	}

	public List findByCompany(Object company) {
		return findByProperty(COMPANY, company);
	}

	public List findByPhoneType(Object phoneType) {
		return findByProperty(PHONE_TYPE, phoneType);
	}

	public List findByOs(Object os) {
		return findByProperty(OS, os);
	}

	public List findByOsVersion(Object osVersion) {
		return findByProperty(OS_VERSION, osVersion);
	}

	public List findByLocalStorage(Object localStorage) {
		return findByProperty(LOCAL_STORAGE, localStorage);
	}

	public List findByRestLocalStorage(Object restLocalStorage) {
		return findByProperty(REST_LOCAL_STORAGE, restLocalStorage);
	}

	public List findByStorage(Object storage) {
		return findByProperty(STORAGE, storage);
	}

	public List findByRestStorage(Object restStorage) {
		return findByProperty(REST_STORAGE, restStorage);
	}

	public List findByRam(Object ram) {
		return findByProperty(RAM, ram);
	}

	public List findByCpuFrequency(Object cpuFrequency) {
		return findByProperty(CPU_FREQUENCY, cpuFrequency);
	}

	public List findByCoreNumber(Object coreNumber) {
		return findByProperty(CORE_NUMBER, coreNumber);
	}

	public List findByNetType(Object netType) {
		return findByProperty(NET_TYPE, netType);
	}

	public List findByNetSpeed(Object netSpeed) {
		return findByProperty(NET_SPEED, netSpeed);
	}

	public List findByPhoneModel(Object phoneModel) {
		return findByProperty(PHONE_MODEL, phoneModel);
	}

	public List findByImel(Object imel) {
		return findByProperty(IMEL, imel);
	}

	public List findBySerialNumber(Object serialNumber) {
		return findByProperty(SERIAL_NUMBER, serialNumber);
	}

	public List findByJpushId(Object jpushId) {
		return findByProperty(JPUSH_ID, jpushId);
	}

	public List findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List findByStartTime(Object startTime) {
		return findByProperty(START_TIME, startTime);
	}

	public List findByEndTime(Object endTime) {
		return findByProperty(END_TIME, endTime);
	}

	public List findAll() {
		log.debug("finding all Node instances");
		try {
			String queryString = "from Node";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Node merge(Node detachedInstance) {
		log.debug("merging Node instance");
		try {
			Node result = (Node) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Node instance) {
		log.debug("attaching dirty Node instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Node instance) {
		log.debug("attaching clean Node instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static NodeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (NodeDAO) ctx.getBean("NodeDAO");
	}
	//查找所有节点的个数
	public String countAllNumber() {
		log.debug("select count(*) from Node");
		try {
			String queryString = "select count(*) from Node";
			return getHibernateTemplate().find(queryString).get(0).toString();
		} catch (RuntimeException re) {
			log.error("find node countAllNumber failed", re);
			throw re;
		}
	}
	
	//查找在线节点的个数
	public String countLiveNode() {
		log.debug("select count(*) from Node");
		try {
			String queryString = "select count(*) from Node where state='1'";
			return getHibernateTemplate().find(queryString).get(0).toString();
		} catch (RuntimeException re) {
			log.error("find node countAllNumber failed", re);
			throw re;
		}
	}
	//查找在线节点识别码列表
	public List<String> getLiveNodeNumber() {
		List<String> nodeNumberList = new ArrayList<String>();
		log.debug("select node_id from Node where state='1'");
		try {
			String queryString = "select nodeId from Node where state='1'";
			
			List<String> resList=getHibernateTemplate().find(queryString);
			int size = resList.size();
			for (int i = 0; i < size; i++) {
				nodeNumberList.add(resList.get(i).toString());
			}
			return nodeNumberList;
		} catch (RuntimeException re) {
			log.error("find node countAllNumber failed", re);
			throw re;
		}
	}
}