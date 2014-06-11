package dosna.api.security;

/**
 * Class used to provide an encryption algorithm to encrypt data.
 *
 * @author Joshua Kissoon
 *
 * @since 20140611
 *
 * @param <Input>  The type of the input it takes to encrypt
 * @param <Output> The type of the output given after encryption
 */
public interface EncryptionAlgorithm<Input, Output>
{

    /**
     * Method used to encrypt some data
     *
     * @param data   The data to be encrypted
     * @param params Any extra parameters needed for encryption
     *
     * @return The encrypted output
     */
    public Output encrypt(Input data, SecurityParameter params);

}
