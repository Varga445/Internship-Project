package eu.accesa.learningplatform.model.entity.ldap;

import javax.naming.Name;

import org.springframework.data.annotation.Transient;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

@Entry(objectClasses = { "person", "inetOrgPerson", "top", "organizationalPerson" })
public class User {

	@Id
	private Name id;

	private @Attribute(name = "uid") String username;
	private @Attribute(name = "userPassword") byte[] password;

	@DnAttribute(value = "ou", index = 0)
	@Transient // Indicates that this is not an attribute on the entry
	private String userType;

	public Name getId() {
		return id;
	}

	public void setId(Name id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}