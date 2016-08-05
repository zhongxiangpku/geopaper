package cn.edu.hfut.dmic.webcollector.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 商家标签bean
 * 
 * 商家标签实体类
 * 
 * @author 钟翔
 *
 */
@Entity
@Table(name="shop_tag")
public class ShopTag
{
	private long id;	
	private long shopid;	
	private long tagid;		
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
	public long getShopid(){
		return this.shopid;
	}
	public void setShopid(long shopid){
		this.shopid = shopid;
	}
	
	@Column(name="tagid")
	public long getTagid(){
		return this.tagid;
	}
	public void setTagid(long tagid){
		this.tagid = tagid;
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
