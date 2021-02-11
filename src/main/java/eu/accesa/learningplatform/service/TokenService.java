package eu.accesa.learningplatform.service;

import java.util.Map;

public interface TokenService {

	public String generateJwtToken(final String userId, final Map<String, String> privateClaimTypeAndString,
			final Map<String, String[]> privateClaimTypeAndStringArray);

	public String getRefreshToken(String email);
	
	public boolean verifyToken(String encryptedJwe);
	
}
