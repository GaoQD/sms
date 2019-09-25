package com.sms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sms.model.ClazzInfo;
import com.sms.model.Paging;
import com.sms.util.StringUtil;

public class ClazzDao extends BaseDao{
	
	/**
	 * 获取班级列表
	 *
	 * @date 2019年9月25日  
	 * @方法名: getClazzList
	 * @param clazz
	 * @param paging
	 * @return
	 */
	public List<ClazzInfo> getClazzList(ClazzInfo clazz,Paging paging) {
		List<ClazzInfo> list = new ArrayList<ClazzInfo>();
		
		String sql = "select id,name,introduce from classInfo";
		
		if (!StringUtil.isEmpty(clazz.getName())) {
			sql += "where name like '%" + clazz.getName() + "%' ";
		}
		
		sql += " limit " + paging.getPageStart() + "," + paging.getPageSize();
		
		try (ResultSet rs = query(sql)){
			while (rs.next()) {
				ClazzInfo clazzInfo = new ClazzInfo();
				clazzInfo.setId(rs.getInt("id"));
				clazzInfo.setName(rs.getString("name"));
				clazzInfo.setIntroduce(rs.getString("introduce"));
				list.add(clazzInfo);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取班级列表的条数
	 *
	 * @date 2019年9月25日  
	 * @方法名: getClazzListNum
	 * @param clazz
	 * @return
	 */
	public int getClazzListNum(ClazzInfo clazz) {
		int num = 0;
		String sql = "select count(*) as num from classInfo";
		
		if (!StringUtil.isEmpty(clazz.getName())) {
			sql += "where name like '%" + clazz.getName() + "%' ";
		}
		
		try (ResultSet rs = query(sql)){
			while(rs.next()) {
				num = rs.getInt("num");
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return num;
	}
	
	/**
	 * 添加班级
	 *
	 * @date 2019年9月25日  
	 * @方法名: addClass
	 * @param clazzInfo
	 * @return
	 */
	public boolean addClass(ClazzInfo clazzInfo) {
		String sql = null;
		if (!StringUtil.isEmpty(clazzInfo.toString())) {
			sql = "insert into classInfo(name,introduce) values('" + clazzInfo.getName() + "','" + clazzInfo.getIntroduce() + "')";
		}
		
		return update(sql);
	}
	
	/**
	 * 删除班级
	 *
	 * @date 2019年9月25日  
	 * @方法名: deleteClass
	 * @param classId
	 * @return
	 */
	public boolean deleteClass(int classId) {
		String sql = "delete from classInfo where id = " + classId;
		return update(sql);
	}
	
	/**
	 * 更新班级信息
	 *
	 * @date 2019年9月25日  
	 * @方法名: editClassInfo
	 * @param clazzInfo
	 * @return
	 */
	public boolean editClassInfo(ClazzInfo clazzInfo) {
		String sql = "update classInfo set name='" + clazzInfo.getName() + "' ,  introduce='" + clazzInfo.getIntroduce() + "' where id= " + clazzInfo.getId();
		
		return update(sql);
	}
}
 