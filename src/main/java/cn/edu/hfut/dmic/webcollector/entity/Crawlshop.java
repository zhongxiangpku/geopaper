package cn.edu.hfut.dmic.webcollector.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 商家IDbean
 * 
 * 商家ID类
 * 
 * @author 钟翔
 *
 */
@Entity
@Table(name="crawlshop")
public class Crawlshop
{
	private long id;	
	private long shopid;	
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

	@Column(name="shopid")
	public void setShopid(long shopid)
	{
		this.shopid = shopid;
	}
	public long getShopid()
	{
		return this.shopid;
	}
	
	@Column(name="status")
	public void setStatus(int status)
	{
		this.status = status;
	}
	public int getStatus()
	{
		return this.status;
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
