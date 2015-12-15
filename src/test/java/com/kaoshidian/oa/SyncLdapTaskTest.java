/**
 * 
 */
package com.kaoshidian.oa;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.kaoshidian.oa.permission.SyncFromLdapTask;
import com.kaoshidian.tool.ldap.LdapOrg;

/**
 * @author <p>Turbidsoul Chen 于 2013年7月1日 上午10:59:38</p>
 *
 */
public class SyncLdapTaskTest extends JUnitBase {

	@Test
	public void testSyncOrg() throws Exception {
		SyncFromLdapTask task = context.getBean(SyncFromLdapTask.class);
		assertEquals(task.getLdap().getHost(), "113.200.76.34");
		assertEquals(task.getLdap().getPort(), 389);
		assertEquals(task.getLdap().getBaseDn(), "OU=学府考研,DC=isoushi,DC=cn");
		assertEquals(task.getLdap().getUserName(), "mrms");
		assertEquals(task.getLdap().getPassword(), "soushi_ksd");
		
		
		//assertTrue(task.getLdap().auth("css", "123456"));
		
		task.syncUser();
		task.syncOrg();
	}

}
