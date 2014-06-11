package dosna.api.security;

/**
 * An interface representing some sort of worker class used to decrypt data
 * - This class can be used to remove padding, etc from data
 *
 * @author Joshua Kissoon
 *
 * @since 20140611
 *
 * @param <Algorithm> The decryption algorithm used by this Decryptor class
 * @param <Input>     The type of the input it takes to decrypt
 * @param <Output>    The type of the output given after decryption
 */
public interface Decryptor<Algorithm extends DecryptionAlgorithm, Input, Output>
{

    /**
     * Method used to decrypt data
     *
     * @param data   The data to be decrypted
     * @param params Any extra parameters needed for decryption
     *
     * @return The encrypted output
     */
    public Output decrypt(Input data, SecurityParameter params);

}
