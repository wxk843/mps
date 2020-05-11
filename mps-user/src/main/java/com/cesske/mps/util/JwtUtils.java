package com.cesske.mps.util;

//import cn.dsmc.ecp.model.entity.sysauth.SysUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;

/**
 * 集成JWT实现的token生成和验证类
 */
@Slf4j
public class JwtUtils {

    /**
     * 5天
     */
    static final long EXPIRATIONTIME = 432_000_000;

    /**
     * JWT密码
     */
    static final String SECRET = "AaBbBB123123@dsmc";

    /**
     * Token前缀
     */
    static final String TOKEN_PREFIX = "AaBearer";

    /**
     * 存放Token的Header Key
     */
    static final String HEADER_STRING = "Authorization";

    private JwtUtils() {}

    /**
     * JWT生成方法
     * @param permissionUser
     */
//    public static String getToken(SysUser permissionUser) {
//        return Jwts.builder()
//                // 保存菜单权限（角色） 用户名写入标题 有效期设置签名设置
//                .claim("uid", permissionUser.getRid())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
//                .signWith(SignatureAlgorithm.HS512, SECRET)
//                .compact();
//    }

    /**
     * JWT验证方法
     *
     * @param token
     * @return
     */
    public static boolean checkToken(String token) {
        if (!Strings.isNullOrEmpty(token)) {
            try {
                Claims claims = Jwts.parser()
                        // 验签 去掉 Bearer
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();
                String user = claims.getOrDefault("uid", "").toString();
                // 得到用户的权限
                return !Strings.isNullOrEmpty(user);
            } catch (Exception e) {
                log.error("checkToken error", e);
                return false;
            }
        }
        return false;
    }

    public static String getId(String token) {
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = Jwts.parser()
                        // 验签 去掉 Bearer
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();
                String userId = claims.getOrDefault("uid", "0").toString();
                // 得到用户的 ID
                return userId;
            } catch (Exception e) {
                log.error("getId error", e);
                return null;
            }
        }
        return null;
    }

    /**
     * @param token
     * @return
     */
    public static Claims parseJWT(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("parseJWT is error", e);
            return null;
        }
    }

    public static Long getUser(HttpServletRequest request){
        // 获取Authorization字段
        String token = request.getHeader(HEADER_STRING);
        String uid = "0";
        if(!Strings.isNullOrEmpty(token)){
            Claims claims = parseJWT(token);
            uid = claims.getOrDefault("uid", "0").toString();
        }
        return Long.valueOf(uid);
    }

    /**
     * 解析 token payload 中的 superId
     * 忽视过期时间
     * @param token
     * @return
     */
    public static String parseTokenSuperId(String token) {
        if(StringUtils.isBlank(token)) {
            return "UNKNOWN";
        }
        String[] tokenSplit = token.split("\\.");
        if(tokenSplit.length != 3) {
            return "UNKNOWN";
        }
        String jwtTokenPayload = tokenSplit[1];
        String payloadString = new String(Base64.getUrlDecoder().decode(jwtTokenPayload));
        if(StringUtils.isBlank(payloadString)) {
            return "UNKNOWN";
        }
        JSONObject payload = JSON.parseObject(payloadString);
        if(payload == null) {
            return "UNKNOWN";
        }
        String superId = payload.getString("superId");
        return StringUtils.isBlank(superId) ? "UNKNOWN" : superId;
    }
}
