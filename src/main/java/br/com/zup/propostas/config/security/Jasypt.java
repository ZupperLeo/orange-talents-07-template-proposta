package br.com.zup.propostas.config.security;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.iv.RandomIvGenerator;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class Jasypt {

    private final String salt = pegarSalt();

    /**
     * metodo responsavel por gerar o hash
     *
     * @param documentoCriptografado recebe o documento limpo para realizar o hash antes de criptografar
     * @return hashDocumento retorna o documento "hasheado"
     */
    public String hash(String documentoCriptografado) {
        try {
            MessageDigest message = MessageDigest.getInstance("SHA-512");
            message.reset();

            String hashDocumento = String.format("%0128x", new BigInteger(
                    1, message.digest((documentoCriptografado + salt)
                    .getBytes(StandardCharsets.UTF_8))));
            return hashDocumento;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * metodo responsavel por criptografar o documento
     *
     * @param documento recebe o documento limpo para criptografar
     * @return documentoCriptografado retorna o documento criptografado
     */
    public String criptografar(String documento) {
        try {
            String documentoCriptografado = encryptor().encrypt(documento);

            return documentoCriptografado;
        } catch (EncryptionOperationNotPossibleException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * metodo resposnavel por descriptografar o documento
     *
     * @param hashDocumento recebe o documento criptografado
     * @return documentoDescriptografado retorna o documento descriptografado
     */
    public String descriptografar(String hashDocumento) {
        try {
            String documentoDescriptografado = encryptor().decrypt(hashDocumento);

            return documentoDescriptografado;
        } catch (EncryptionOperationNotPossibleException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * metodo responsavel pela configuracao da criptografia
     *
     * @return encryptor retorna um objeto da classe StandardPBEStringEncryptor contendo as configuracoes de criptografia
     */
    private StandardPBEStringEncryptor encryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(salt);
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        encryptor.setIvGenerator(new RandomIvGenerator());

        return encryptor;
    }


    private String pegarSalt() {
        Optional<String> buscarSalt = Optional.ofNullable(System.getenv().get("JASYPT_SECRET"));

        if (buscarSalt.isEmpty()) {
            return "rm'!@N=Ke!~p8VTA2ZRK~nMDQX5Uvm!m'D&]{@Vr?G;2?XhbC:Qa#9#eMLN}x3?JR3.2zr~v)gYF^8:8>:XfB:Ww75N/emt9Yj[bQMNCWwWJ?N,nvH.<2.r~w]*e~vgak)X\"v8H`MH/7\"2E`,^k@n<vE-wD3g9JWPy;CrY*.Kd2_D])=><D?YhBaSua5hW%{2]_FVXzb9`8FH^b[X3jzVER&:jw2<=c38=>L/zBq`}C6tT*cCSVC^c]-L}&/";
        }
        return buscarSalt.get();
    }
}
