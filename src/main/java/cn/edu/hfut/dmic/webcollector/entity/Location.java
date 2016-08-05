package cn.edu.hfut.dmic.webcollector.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 位置bean
 * 
 * 位置实体类
 * 
 * @author 钟翔
 *
 */
@Entity
@Table(name="location")
public class Location
{
	private long id;	
	private String address;	
	private double lat;
	private double lng;
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

	@Column(name="address")
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getAddress()
	{
		return this.address;
	}
	
	@Column(name="lat")
	public void setLat(double lat)
	{
		this.lat = lat;
	}
	public double getLat()
	{
		return this.lat;
	}
	
	@Column(name="lng")
	public void setLng(double lng)
	{
		this.lng = lng;
	}
	public double getLng()
	{
		return this.lng;
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
