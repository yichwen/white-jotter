package org.ursprung.wj.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import org.ursprung.wj.entity.User;
import org.ursprung.wj.result.Result;
import org.ursprung.wj.result.ResultFactory;
import org.ursprung.wj.service.UserService;
import org.ursprung.wj.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

//    @Autowired
//    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

//    @PostMapping("api/login")
//    public Result login(@RequestBody User requestUser) {
//        // 对 html 标签进行转义，防止 XSS 攻击
//        String username = requestUser.getUsername();
//        username = HtmlUtils.htmlEscape(username);
//
//        User user = userService.get(username, requestUser.getPassword());
//        if (null == user) {
//            return ResultFactory.buildFailResult(null);
//        } else {
//            return ResultFactory.buildSuccessResult(null);
//        }
//    }

    @PostMapping("/api/login")
    public Result login(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, requestUser.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        Map<String, Object> claims = new HashMap<>();
        claims.put("user", authentication.getPrincipal());
        String jwtToken = jwtUtil.generateJWT(claims);
        TokenDto token = new TokenDto();
        token.setToken(jwtToken);
        if (null == jwtToken) {
            return ResultFactory.buildFailResult(null);
        } else {
            return ResultFactory.buildSuccessResult(jwtToken);
        }
    }

    @Data
    public static class Claims {
        private Object user;
    }

    @Data
    public static class TokenDto {
        private String token;
    }

}
