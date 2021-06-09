package com.nab.icom.user.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "USER_CREDENTIAL")
public class OnlineUser {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

    @Column(name="USER_ID",unique=true,nullable=false)
    private String screenName;

    @Column(name="PASSWORD",nullable=false)
    private String password;

	@Column(name="ACTIVE" ,nullable=false, columnDefinition = "TINYINT(1)")
    private boolean active;

	@OneToMany(cascade = CascadeType.ALL,mappedBy="onlineUser", orphanRemoval = true)
    private Set<UserRole> userRoles;


	public void addRole(UserRole userRole){
		if(userRoles==null){
			userRoles=new HashSet<UserRole>(5);
		}
		userRole.setOnlineUser(this);
		userRoles.add(userRole);
	}

    public OnlineUser() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}




}
