package cn.edu.hfut.dmic.webcollector.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 客户bean
 * 
 * 客户实体类
 * 
 * @author 钟翔
 *
 */
@Entity
@Table(name="customer")
public class Customer
{
	private long id;	
	private long cid;	
	private String name;	
	private String level;		
	private String portrait;		
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

	@Column(name="cid")
	public long getCid(){
		return this.cid;
	}
	public void setCid(long cid){
		this.cid = cid;
	}
	
	@Column(name="name")
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	@Column(name="level")
	public String getLevel(){
		return this.level;
	}
	public void setLevel(String level){
		this.level = level;
	}
	
	@Column(name="portrait")
	public String getPortrait(){
		return this.portrait;
	}
	public void setPortrait(String portrait){
		this.portrait = portrait;
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
