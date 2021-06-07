package nl.hu.bep.shopping.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import nl.hu.bep.shopping.model.MyUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.security.Key;
import java.util.AbstractMap;
import java.util.Calendar;


@Path("login")
public class AuthenticationResource {
    public static final Key key = MacProvider.generateKey();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticatieUser(@FormParam("username") String username, @FormParam("password") String password){
        String role = MyUser.validateLogin(username, password);

        if(role == null)throw new IllegalArgumentException("No user found");

        String token = createToken(username, role);
        return Response.ok(new AbstractMap.SimpleEntry<>("JWT", token)).build();
    }

    private String createToken(String username, String role) throws JwtException{
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 30);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration.getTime())
                .claim(username, role)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

}
