package cn.edu.hfut.dmic.webcollector.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 商家bean
 * 
 * 商家实体类
 * 
 * @author 钟翔
 *
 */
@Entity
@Table(name="shop")
public class Shop
{
	private long id;	
	private long sid;	
	private long lid;	
	private String name;
	private String phone;
	private String sys_score;	
	private String type;	
	private long consumers;
	private long comments;
	private int status;
	private Date createtime;
	private Date updatetime;
	
	//===========setXXX()与getXXX()方法========
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId(){
		return this.id;
	}
	public void setId(long id){
		this.id = id;
	}

	@Column(name="sid")
	public void setSid(long sid) {
		this.sid = sid;
	}
	public long getSid() {
		return sid;
	}
	
	@Column(name="lid")
	public void setLid(long lid) {
		this.lid = lid;
	}
	public long getLid() {
		return lid;
	}
	
	@Column(name="name")
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	@Column(name="phone")
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhone() {
		return phone;
	}
	
	@Column(name="sys_score")
	public void setSys_score(String sys_score) {
		this.sys_score = sys_score;
	}
	public String getSys_score() {
		return sys_score;
	}
	
	@Column(name="consumers")
	public void setConsumers(long consumers) {
		this.consumers = consumers;
	}
	public long getConsumers() {
		return consumers;
	}
	
	@Column(name="comments")
	public void setComments(long comments) {
		this.comments = comments;
	}
	public long getComments() {
		return comments;
	}
	
	@Column(name="type")
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	
	@Column(name="status")
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}
	
	@Column(name="createtime")
	public void setCreatetime(Date createtime)
	{
		this.createtime = createtime;
	}
	public Date getCreatetime()
	{
		return this.createtime;
	}
	
	@Column(name="updatetime")
	public void setUpdatetime(Date updatetime)
	{
		this.updatetime = updatetime;
	}
	public Date getUpdatetime()
	{
		return this.updatetime;
	}
}
