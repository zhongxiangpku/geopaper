package cn.edu.hfut.dmic.webcollector.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 评论bean
 * 
 * 评论实体类
 * 
 * @author 钟翔
 *
 */
@Entity
@Table(name="comments")
public class Comment
{
	private long id;	
	private long cid;	
	private long sid;	
	private long lid;	
	private String score;
	private Date comment_time;
	private String content;				
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
	
	@Column(name="score")
	public void setScore(String score) {
		this.score = score;
	}
	public String getScore() {
		return score;
	}
	
	@Column(name="comment_time")
	public void setComment_time(Date comment_time) {
		this.comment_time = comment_time;
	}
	public Date getComment_time() {
		return comment_time;
	}
	
	@Column(name="content")
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
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
