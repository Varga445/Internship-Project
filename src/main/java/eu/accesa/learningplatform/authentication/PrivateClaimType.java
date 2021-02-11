package eu.accesa.learningplatform.authentication;

/**
 * Supported private claim types for building the Token and the ID Token
 */
public enum PrivateClaimType {
	/**
	 * Email address of the customer
	 */
	MAIL("email"),

	/**
	 * alias of the customer
	 */
	NAME("name"),

	ACTIVE("active"),

	ROLES("roles");


	private final String loginType;

	PrivateClaimType(final String loginType) {
		this.loginType = loginType;
	}

	public String getType() {
		return this.loginType;
	}
}
