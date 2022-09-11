package com.genesis.genesisapi.config;
/*package com.mtas.product.vsr.security;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;



public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Resource
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserMdoService userMdoService;
    
    private String clientId = System.getProperty(StringLiterals.OAUTH_CLIENTID);

    private String appId = System.getProperty(StringLiterals.OAUTH_AZUREAPPID);
    
	@Autowired
    @Qualifier("authProviderForDirectLogin")
    private MDOAuthProviderForDirectLogin mdoAuthProviderForDirectLogin;
    
    public CustomAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    	String email = "";
    	UserMdoModel model = new UserMdoModel();
    	if(StringUtils.isNotBlank(clientId) && StringUtils.isNotBlank(appId)) {
    		OAuth2AccessToken token = restTemplate.getAccessToken();
    		HttpClient client = new HttpClient();
    		GetMethod method = new GetMethod("https://graph.microsoft.com/v1.0/me");
    		method.setRequestHeader("Authorization", String.format("%s %s", token.getTokenType(), token.getValue()));
    		int statusCode = client.executeMethod(method);
    		
    		if (statusCode != HttpStatus.SC_OK) {
    			logger.error("Method failed: " + method.getStatusLine());
    		}
    		String respBody = method.getResponseBodyAsString();
    		JSONObject obj = new JSONObject();
    		try {
    			obj = (JSONObject) new JSONParser().parse(respBody);
			} catch (org.json.simple.parser.ParseException e) {
			}
    		if(obj.containsKey("userPrincipalName")) {
    			email = (String) obj.get("userPrincipalName");
    		}else if(obj.containsKey("mail")) {
    			email = (String) obj.get("mail");
    		}
    		model = userMdoService.findByPrimaryKey(email);
    		if(model == null) {
    			throw new AuthenticationException("User Id not found !!!") {};
    		}
        }else {
	    	final ResponseEntity<UserInfo> userInfoResponseEntity = restTemplate.getForEntity("https://www.googleapis.com/oauth2/v2/userinfo", UserInfo.class);
	    	UserInfo info = userInfoResponseEntity.getBody();
	        email = info.getEmail();
	        List<UserMdoModel> ls = userMdoService.findByEmailId(email);
	        if(ls.size() == 1){
				model = ls.get(0);
	        }else if(ls.size() > 1){
				throw new AuthenticationException("User email not unique !!!") {};
			}else{
				throw new AuthenticationException("User Id not found !!!") {};
			}
        }
		boolean enabled = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date startDate = sdf.parse(model.getStatus());
			Date curDate = new Date();
			if(curDate.compareTo(startDate) == 1 && "1".equals(model.getStage())) {
				enabled = true;
			}
		} catch (ParseException e1) {
			throw new AuthenticationException("User is disabled !!!") {};
		}
		if(!enabled){
			throw new AuthenticationException("User is disabled !!!") {};
		}
        Authentication auth = new UsernamePasswordAuthenticationToken(model.getUserId(), model.getPwd());
        Authentication result = mdoAuthProviderForDirectLogin.authenticate(auth);
        ((CustomSecurityHolder) result.getPrincipal()).setAuthType(AuthType.OAUTH);
        return result;
    }
}
*/