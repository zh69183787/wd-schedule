/**
 * 
 */
package com.wonders.task.contractReview.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * @ClassName: PAttach 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhoushun 
 * @date 2014年1月21日 下午3:05:47 
 *  
 */

@Entity
@Table(name = "T_ATTACH")
public class PAttach implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1313909930781427116L;

	private Long id; // id

	private String appname; // appname

	private String fileextname; // fileextname

	private String filename; // filename

	private Long filesize; // filesize

	private Long operateTime; // operateTime


	private String path; // path

	private Long removed; // removed

	private String savefilename; // savefilename

	private String status; // status

	private String uploaddate; // uploaddate

	private String uploader; // uploader

	private String uploaderLoginName; // uploaderLoginName

	private String version; // version

	public PAttach(){
		this.version="1";
		this.appname = "contractReview";
		this.status ="upload";
		this.status="eam";//BUg
		this.removed=0L;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	@Column(name = "APPNAME", nullable = true, length = 50)
	public String getAppname() {
		return appname;
	}

	public void setFileextname(String fileextname) {
		this.fileextname = fileextname;
	}

	@Column(name = "FILEEXTNAME", nullable = true, length = 10)
	public String getFileextname() {
		return fileextname;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "FILENAME", nullable = true, length = 200)
	public String getFilename() {
		return filename;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	@Column(name = "FILESIZE", nullable = true, length = 22)
	public Long getFilesize() {
		return filesize;
	}


	public void setOperateTime(Long operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "OPERATE_TIME", nullable = true, length = 22)
	public Long getOperateTime() {
		return operateTime;
	}


	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "PATH", nullable = true, length = 200)
	public String getPath() {
		return path;
	}

	public void setRemoved(Long removed) {
		this.removed = removed;
	}

	@Column(name = "REMOVED", nullable = true, length = 22)
	public Long getRemoved() {
		return removed;
	}

	public void setSavefilename(String savefilename) {
		this.savefilename = savefilename;
	}

	@Column(name = "SAVEFILENAME", nullable = true, length = 50)
	public String getSavefilename() {
		return savefilename;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "STATUS", nullable = true, length = 20)
	public String getStatus() {
		return status;
	}

	public void setUploaddate(String uploaddate) {
		this.uploaddate = uploaddate;
	}

	@Column(name = "UPLOADDATE", nullable = true, length = 19)
	public String getUploaddate() {
		return uploaddate;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	@Column(name = "UPLOADER", nullable = true, length = 30)
	public String getUploader() {
		return uploader;
	}

	public void setUploaderLoginName(String uploaderLoginName) {
		this.uploaderLoginName = uploaderLoginName;
	}

	@Column(name = "UPLOADER_LOGIN_NAME", nullable = true, length = 50)
	public String getUploaderLoginName() {
		return uploaderLoginName;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "VERSION", nullable = true, length = 10)
	public String getVersion() {
		return version;
	}
}
