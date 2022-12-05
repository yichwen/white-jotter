#### 客户端存储方案 （cookie、localStorage、sessionStorage）

- 接下来说一下认证信息在 **客户端** 存储的方式。首先明确，无论是明文用户名密码，还是 sessionId 和 token，都可以用三种方式存储，即 cookie、localStorage 和 sessionStorage。
- 但 cookie 和 local/session storage 分工又有所不同，cookie 可以作为传递的参数，并可通过后端进行控制，local/session storage 则主要用于在客户端中保存数据，其传输需要借助 cookie 或其它方式完成。
- 下面是三种方式的对比

| 特性     | Cookie                                                       | localStorage                                       | sessionStorage                               |
| -------- | ------------------------------------------------------------ | -------------------------------------------------- | -------------------------------------------- |
| 生命周期 | 一般由服务器生成，可设置失效时间。如果在浏览器端生成cookie，默认是关闭浏览器后失效 | 除非被清除，否则永久保存                           | 仅在当前会话下有效，关闭页面或浏览器后被清除 |
| 数据大小 | 4K左右                                                       | 一般为5MB                                          | 一般为5MB                                    |
| 通信方式 | 每次都会携带在HTTP头中，如果使用cookie保存过多数据会带来性能问题 | 仅在客户端（即浏览器）中保存，不参与和服务器的通信 | 同 localStorage                              |

- 通常来说，在可以使用 cookie 的场景下，作为验证用途进行传输的用户名密码、sessionId、token 直接放在 cookie 里即可。而后端传来的其它信息则可以根据需要放在 local/session storage 中，作为全局变量之类进行处理。



**实现 JWT Authentication**

- 在没有使用 Spring Security OAuth 或者 Spring Security 5 的 OAuth2 实现 JWT Authentication

- 一般有两个步骤：

  - 用户验证成功后创建令牌并返回
  - 使用令牌使用 API，验证令牌

- 在用户验证的实现中，研究之后目前有两种实现方式

  - 使用 Filter 进行验证，以下过滤器的代码是继承 UsernamePasswordAuthenticationFilter 进行验证，但是这个方法有一个缺点：默认的 UsernamePasswordAuthenticationFilter 是使用 form-data 接收用户登录的信息，如果要使用请求体为 JSON发送用户登录数据，则无需继承 UsernamePasswordAuthenticationFilter，但可以继承其父类 AbstractAuthenticationProcessingFilter 并参考 UsernamePasswordAuthenticationFilter 实现。

    ```java
    /**
     *  default is POST /login
     */
    public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
        private JwtUtil jwtUtil;
    
        public JwtAuthenticationFilter(JwtUtil jwtUtil) {
            this.jwtUtil = jwtUtil;
        }
    
        @Override
        protected void successfulAuthentication(HttpServletRequest request, 
                                                HttpServletResponse response, 
                                                FilterChain chain, 
                                                Authentication authResult) 
            									throws IOException, ServletException {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authResult;
            Object principal = token.getPrincipal();
            Map<String, Object> claims = new HashMap<>();
            claims.put("user", principal);
            String jwtToken = jwtUtil.generateJWT(claims);
            Map<String, Object> data = new HashMap<>();
            data.put("token", jwtToken);
            String responseBody = new ObjectMapper().writeValueAsString(data);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(responseBody);
            out.flush();
        }
    
    }
    ```

  - 使用 Controller 进行验证

    - 在使用请求体为 JSON接收用户登录数据时，容易实现

    ```java
    @PostMapping("api/login")
    public Result login(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
    
        UsernamePasswordAuthenticationToken authenticationToken = 
            new UsernamePasswordAuthenticationToken(username, requestUser.getPassword());
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
    ```

    

- 使用令牌使用 API，验证令牌

  - 此过滤器必须在 WebSecurityConfigurerAdapter 中配置

  ```java
  public class JwtTokenFilter extends OncePerRequestFilter {
  
      private static final String BEARER = "Bearer ";
  
      private JwtUtil jwtUtil;
  
      public JwtTokenFilter(JwtUtil jwtUtil) {
          this.jwtUtil = jwtUtil;
      }
  
      @Override
      protected void doFilterInternal(HttpServletRequest request, 
                                      HttpServletResponse response, 
                                      FilterChain filterChain) throws ServletException, IOException {
          String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
          if (StringUtils.hasText(authorization) && authorization.startsWith(BEARER)) {
              String token = authorization.substring(7);
              if (!jwtUtil.isTokenExpired(token)) {
                  Claims claimsFromToken = jwtUtil.getClaimsFromToken(token);
                  Map<String, Object> userInfo = (Map<String, Object>) claimsFromToken.get("user");
                  List<String> authorities = (List<String>) userInfo.get("authorities");
                  String username = (String) userInfo.get("username");
                  List<GrantedAuthority> grantedAuthorities = authorities.stream()
                          .map(auth -> new SimpleGrantedAuthority(auth))
                          .collect(Collectors.toList());
                  UsernamePasswordAuthenticationToken authentication = 
                      new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
                  authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                  SecurityContextHolder.getContext().setAuthentication(authentication);
              }
          }
          filterChain.doFilter(request, response);
      }
  }
  ```

  

**Menus 目录**

```yaml
首页:
	- 运行情况 /admin/dashboard
用户管理:
	- 用户信息 /admin/user/profile
	- 角色配置 /admin/user/role
内容管理:
	- 图书管理 /admin/content/book
	- 广告管理 /admin/content/banner
	- 文章管理 /admin/content/article
系统配置:
```

















