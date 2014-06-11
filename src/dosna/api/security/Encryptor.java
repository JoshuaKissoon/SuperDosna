package dosna.api.security;

/**
 * An interface representing some sort of worker class used to encrypt data
 * - This class can be used to add padding, etc to data
 *
 * @author Joshua Kissoon
 *
 * @since 20140611
 *
 * @param <Algorithm> The decryption algorithm used by this Encryptor class
 * @param <Input>     The type of the input it takes to encrypt
 * @param <Output>    The type of the output given after encryption
 */
public interface Encryptor<Algorithm extends EncryptionAlgorithm, Input, Output>
{

    /**
     * Method used to decrypt data
     *
     * @param data   The data to be decrypted
     * @param params Any extra parameters needed for decryption
     *
     * @return The encrypted output
     */
    public Output encrypt(Input data, SecurityParameter params);

}
