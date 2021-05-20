import java.security.Key;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * TODO
 *
 * @author fengqing
 * @date 2021/5/11 11:24
 */
public class JTWTest {
    public static void main(String[] args) {
        // We need a signing key, so we'll create one just for this example. Usually
        // the key would be read from your application configuration instead.
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();
        System.out.println(jws);

        System.out.println(Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(jws).getBody().getSubject().equals("Joe"));

    }
}
