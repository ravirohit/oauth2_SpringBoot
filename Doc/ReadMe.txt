Ref link: 
https://howtodoinjava.com/spring-boot2/oauth2-auth-server/
https://stackoverflow.com/questions/46862840/unable-to-use-resourceserverconfigureradapter-and-websecurityconfigureradapter-i
customUserDetailsService impl ref link:
http://progressivecoder.com/implementing-spring-boot-security-using-userdetailsservice/
----------------------
-> project name: spring-security-demo
-> Run as java application from file containing main class: SpringSecurityDemoApplication.java
-> Steps to get auth code:
   -> open chrome browser and enter below url:
      http://localhost:8080/oauth/authorize?client_id=clientapp&response_type=code&scope=read_profile_info
   -> it will redirect to login page, enter below credential which is defined in WebSecurityConfig.java
      username: humptydumpty
      password: 123456
   -> it will redirect to a page http://localhost:8081/login passing the auth code as query param to the same redirect url.
      it is defined in OAuth2AuthorizationServer.java file.

-> use Auth code to get access token from authorization server. I am using Postman for this purpose:
   -> Lunch postman 
   -> select POST Http method type.
   -> enter url: http://localhost:8080/oauth/token
   -> add header: 
      Authorization: Basic ${cliendId:secret}           // defined in OAuth2AuthorizationServer.java
      Content-Type: application/x-www-form-urlencoded
   -> under body, select x-www-form-urlencoded radio button and provide below key value pair information:
      key: grant_type     value: authorization_code
      key: code           value: y94hTO
      key: redirect_uri   value: http://localhost:8081/login
   -> once you will hit the api, response will be:
       {
		    "access_token": "8f4e1f73-fd59-40a5-a214-407f7ecce5e7",
		    "token_type": "bearer",
		    "refresh_token": "5f6d3996-4cf3-4d01-9646-1424a0ebb04a",
		    "expires_in": 1199,
		    "scope": "read_profile_info"
		}
 -> Use access_token to invoke resource api:
    -> select method type: GET
    -> enter url: http://localhost:8080/api/users/me
    -> add Header:
       Authorization: Bearer ${access_token_value}
    -> hit the Url, response will be:
        {
		    "name": "humptydumpty",
		    "email": "humptydumpty@howtodoinjava.com"
		}
   
=== ERROR ============
ERROR:
  o.s.s.c.bcrypt.BCryptPasswordEncoder     : Encoded password does not look like BCrypt
Solution:
  There is two places where user credential is used.
  1. when we are retrieving the user data from db and storing it in CustomUser class object. 
     this operation is happening in CustomUserDetailsService.java class.
  2. There is another place where we provide CustomUserDetailsService class object and PasswordEncoder object where 
     Spring boot would be reading user info from CustomUser object.
     this object is provided to spring boot in WebSecurityConfig.java file.
     NOTE: When Spring boot read password from CustomUser object. which has been initialized. it assume we have encoded the
     password first then initialize it. and when spring read the password, first it decode it before using.
  As i was not encoding the password before storing it in CustomUser object. and Spring was using the passowrd after 
  decoding, it was causing "Bad Credential" error in UI side and in backend error is "Encoded password does not look like BCrypt"
  Solution:
    I have added below code to encode the password before initialize it in CustomUserDetailsService.java:
    
    customUser.setPassword(passwordEncoder().encode(username));  and  // username is password string
    
    public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }

   
   
   
   
   
   
   
   
   
   