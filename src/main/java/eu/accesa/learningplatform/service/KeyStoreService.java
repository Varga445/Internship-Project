package eu.accesa.learningplatform.service;

import java.security.*;

public interface KeyStoreService {

    public PublicKey getPublicKey();

    public PrivateKey getPrivateKey();
    
}
