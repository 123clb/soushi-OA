package com.kaoshidian.oa.permission;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kaoshidian.oa.api.SerureUtil;
import com.kaoshidian.oa.base.KsdRowProcessor;
import com.kaoshidian.oa.log.mng.LogMgr;
import com.kaoshidian.oa.permission.entity.User;
import com.kaoshidian.oa.permission.entity.UserStateEnum;
import com.kaoshidian.oa.permission.entity.UserTypeEnum;
import com.kaoshidian.oa.util.LogEntityEnum;
import com.kaoshidian.oa.util.LogOperationEnum;
import com.kaoshidian.oa.util.LogStatusEnum;


public class ShiroDbRealm extends JdbcRealm{
	private static final Logger log = LoggerFactory.getLogger(ShiroDbRealm.class);
		
	protected String authenticationQuery = "SELECT userId, loginName,password,realName,state, rtxno, createDate,description, salt FROM oa_perm_user WHERE loginName = ? AND userType='" + UserTypeEnum.WORKER.name() + "'";

    protected String userRolesQuery = "SELECT r.roleName FROM oa_perm_user u,oa_perm_userrole ur,oa_perm_role r WHERE u.userId=ur.userId AND ur.roleId=r.roleId AND u.loginName=?";

    protected String permissionsQuery = "SELECT * FROM oa_perm_menu m,oa_perm_rolemenu rm,oa_perm_role r WHERE m.menuId=rm.menuId AND rm.roleId=r.roleId AND r.roleName=?";
	
    @Autowired
    private LogMgr logMgr;
    
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        // Null username is invalid
        if (username == null) {
        	logMgr.save(null, null, upToken.getHost(), null, LogEntityEnum.USER, LogOperationEnum.LOGIN, LogStatusEnum.FAILURE, "登录失败：未提交用户名" );
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        
        AuthenticationInfo info = null;
        Connection conn = null;
        try {

            User user = findUserByLoginName(username);
            
            if(user.getState() != UserStateEnum.ACTIVITY) {
            	logMgr.save(null, username, upToken.getHost(), null, LogEntityEnum.USER, LogOperationEnum.LOGIN, LogStatusEnum.FAILURE, "登录失败：用户被锁定" );
                throw new UnknownAccountException("This account[" + username + "] has bean locked");
            }

            if (user.getPassword() == null) {
            	logMgr.save(null, username, upToken.getHost(), null, LogEntityEnum.USER, LogOperationEnum.LOGIN, LogStatusEnum.FAILURE, "登录失败：密码为空" );
                throw new UnknownAccountException("No account found for user [" + username + "]");
            }
            String password = user.getPassword();
            String salt = user.getSalt();
            user.setPassword(null);
            user.setSalt(null);

            upToken.setPassword(SerureUtil.md5ValueForHex(new String(upToken.getPassword()) + salt).toCharArray());
            
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT clazz, entityId FROM oa_perm_dataperm WHERE userId=?");
	        ps.setInt(1, user.getUserId());
	        ResultSet  rs = ps.executeQuery();
	        while(rs.next()) {
	        	Integer entityId = rs.getInt(2);
	        	String clazz = rs.getString(1);
	        	user.getPermissions().put(clazz, entityId);
	        }
            
            info = new SimpleAuthenticationInfo(user, password.toCharArray(), user.getRealName());

        } catch (SQLException e) {
            final String message = "There was a SQL error while authenticating user [" + username + "]";
            if (log.isErrorEnabled()) {
                log.error(message, e);
            }

            // Rethrow any SQL errors as an authentication exception
            throw new AuthenticationException(message, e);
        } catch (NoSuchAlgorithmException e) {
	        throw new AuthenticationException(e.getMessage(), e);
        } finally {
        	JdbcUtils.closeConnection(conn);
        }

        return info;
	}

    private User findUserByLoginName(String loginName) throws SQLException {
    	QueryRunner run = new QueryRunner(dataSource);
    	User user = run.query(authenticationQuery, new BeanHandler<User>(User.class,new KsdRowProcessor()),loginName);
    	if(user==null){
    		logMgr.save(null, loginName, null, null, LogEntityEnum.USER, LogOperationEnum.LOGIN, LogStatusEnum.FAILURE, loginName + "登录系统失败： 用户不存在");
    		throw new UnknownAccountException("No account found for user [" + loginName + "]");
    	}
    	return user;
    }
    protected Set<String> getRoleNamesForUser(Connection conn, String username) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Set<String> roleNames = new LinkedHashSet<String>();
        try {
            ps = conn.prepareStatement(userRolesQuery);
            ps.setString(1, username);

            // Execute query
            rs = ps.executeQuery();

            // Loop over results and add each returned role to a set
            while (rs.next()) {

                String roleName = rs.getString(1);

                // Add the role to the list of names if it isn't null
                if (roleName != null) {
                    roleNames.add(roleName);
                } else {
                    if (log.isWarnEnabled()) {
                        log.warn("Null role name found while retrieving role names for user [" + username + "]");
                    }
                }
            }
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
        }
        return roleNames;
    }

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        
        User user = (User) getAvailablePrincipal(principals);

        Connection conn = null;
        Set<String> roleNames = null;
        Set<String> permissions = new HashSet<String>();
        try {
            conn = dataSource.getConnection();

            // Retrieve roles and permissions from database
            roleNames = getRoleNamesForUser(conn, user.getLoginName());
            if (permissionsLookupEnabled) {
                permissions = getPermissions(conn, user.getLoginName(), roleNames);
            }
            
            PreparedStatement ps = conn.prepareStatement("SELECT clazz, entityId FROM oa_perm_dataperm WHERE userId=?");
	        ps.setInt(1, user.getUserId());
	        ResultSet  rs = ps.executeQuery();
	        while(rs.next()) {
	        	Integer entityId = rs.getInt(2);
	        	String clazz = rs.getString(1);
	        	permissions.add(clazz + ":" + entityId);
	        }
	        

        } catch (SQLException e) {
            final String message = "There was a SQL error while authorizing user [" + user.getLoginName() + "]";
            if (log.isErrorEnabled()) {
                log.error(message, e);
            }

            // Rethrow any SQL errors as an authorization exception
            throw new AuthorizationException(message, e);
        } finally {
            JdbcUtils.closeConnection(conn);
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
	}
    



}
