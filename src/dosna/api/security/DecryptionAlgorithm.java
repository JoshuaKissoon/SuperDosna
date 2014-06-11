package dosna.api.security;

/**
 * Class used to provide an decryption algorithm to encrypt data.
 *
 * @author Joshua Kissoon
 *
 * @since 20140611
 *
 * @param <Input>  The type of the input it takes to decrypt
 * @param <Output> The type of the output given after decryption
 */
public interface DecryptionAlgorithm<Input, Output>
{

    /**
     * Method used to decrypt some data
     *
     * @param data   The data to be decrypted
     * @param params Any extra parameters needed for decryption
     *
     * @return The encrypted output
     */
    public Output decrypt(Input data, SecurityParameter params);

}
