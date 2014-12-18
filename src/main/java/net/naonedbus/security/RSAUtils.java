package net.naonedbus.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import net.naonedbus.utils.Base64;
import net.naonedbus.utils.Base64DecoderException;

/**
 * Classe fournissant des services de chiffrement et déchiffrement.
 * @author Romain
 * @version $Revision$ $Date$
 */
public class RSAUtils
{
    /**
     * Algorithme utilisé pour le chiffrement/déchiffrement.
     */
    private static String ALGORITHM = "RSA/None/PKCS1Padding";

    /**
     * Algorithme utilisé pour la création des clés
     */
    private static String ALGORITHM_KEY_GEN = "RSA";

    /**
     * Nom du provider utilisé pour le chiffrement/déchiffrement.
     */
    private static String PROVIDER = "BC";

    /**
     * Méthode retournant le hashcode de la concaténation des arguments.
     * @param args Liste des arguments à traiter.
     * @return Hashcode de la concaténation de {@code args}.
     */
    public static String getConcatHashCode(final String... args)
    {
        // Concaténation de tous les arguments
        final StringBuilder sb = new StringBuilder();
        for (final String arg : args)
        {
            sb.append(arg == null
                                 ? ""
                                 : arg);
        }
        final String concat = sb.toString();
        final Integer hashCode = concat.hashCode();

        return hashCode.toString();
    }

    /**
     * Méthode en charge de générer une clé publique ou privée de chiffrement/déchiffrement.
     * @param keyType Type de la clé souhaitée.
     * @param mod Modulo.
     * @param exp Exposant
     * @return Clé générée.
     * @throws Erreur à l'initialisation de la clé.
     */
    public static Key genNaonedbusKey(final KeyType keyType,
                                      final BigInteger mod,
                                      final BigInteger exp)
        throws GeneralSecurityException
    {
        final KeyFactory fact = KeyFactory.getInstance(RSAUtils.ALGORITHM_KEY_GEN,
                                                       RSAUtils.PROVIDER);

        Key key;
        if (KeyType.PUBLIC == keyType)
        {
            final RSAPublicKeySpec keySpec = new RSAPublicKeySpec(mod,
                                                                  exp);
            key = fact.generatePublic(keySpec);
        }
        else
        {
            final RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(mod,
                                                                    exp);
            key = fact.generatePrivate(keySpec);
        }

        return key;
    }

    /**
     * Méthode en charge de générer une clé publique ou privée de chiffrement/déchiffrement.
     * @param keyType Type de la clé souhaitée.
     * @param mod Modulo.
     * @param exp Exposant
     * @return Clé générée.
     * @throws Base64DecoderException
     * @throws Erreur à l'initialisation de la clé.
     */
    public static PublicKey genNaonedbusKey(final String signaturePEM)
        throws GeneralSecurityException,
            Base64DecoderException
    {
        final byte[] decodedKey = Base64.decode(signaturePEM);
        final KeyFactory fact = KeyFactory.getInstance(ALGORITHM_KEY_GEN,
                                                       PROVIDER);
        return fact.generatePublic(new X509EncodedKeySpec(decodedKey));
    }

    /**
     * Chiffrer un tableau de byte avec une clé
     * @param data
     * @param pubKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     */
    public static String encrypt(final String data,
                                 final Key key)
        throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            UnsupportedEncodingException,
            NoSuchProviderException
    {

        final Cipher cipher = Cipher.getInstance(RSAUtils.ALGORITHM,
                                                 RSAUtils.PROVIDER);
        cipher.init(Cipher.ENCRYPT_MODE,
                    key);

        return new String(cipher.doFinal(data.getBytes()),
                          "UTF-8");
    }

    /**
     * Chiffrer un tableau de byte avec une clé, puis encoder le résultat en Base64
     * @param data
     * @param pubKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     */
    public static String encryptBase64(final String data,
                                       final Key key)
        throws GeneralSecurityException,
            UnsupportedEncodingException
    {

        final Cipher cipher = Cipher.getInstance(RSAUtils.ALGORITHM,
                                                 RSAUtils.PROVIDER);
        cipher.init(Cipher.ENCRYPT_MODE,
                    key);

        return Base64.encode(cipher.doFinal(data.getBytes()));
    }

    /**
     * Déchiffrer un tableau de byte avec une clé
     * @param data
     * @param privateKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     */
    public static String decrypt(final byte[] data,
                                 final Key key)
        throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            UnsupportedEncodingException,
            NoSuchProviderException
    {

        final Cipher cipher = Cipher.getInstance(RSAUtils.ALGORITHM,
                                                 RSAUtils.PROVIDER);
        cipher.init(Cipher.DECRYPT_MODE,
                    key);

        return new String(cipher.doFinal(data),
                          "UTF-8");
    }

    /**
     * Décoder un tableau de byte au format Base64, puis le déchiffrer avec une clé
     * @param data
     * @param privateKey
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     * @throws Base64DecoderException
     */
    public static String decryptBase64(final String data,
                                       final Key key)
        throws InvalidKeyException,
            NoSuchAlgorithmException,
            NoSuchPaddingException,
            IllegalBlockSizeException,
            BadPaddingException,
            UnsupportedEncodingException,
            NoSuchProviderException,
            Base64DecoderException
    {
        final byte[] src = org.apache.commons.codec.binary.Base64.decodeBase64(data.getBytes());
        return RSAUtils.decrypt(src,
                                key);
    }
}
