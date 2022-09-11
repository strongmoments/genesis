package com.genesis.genesisapi.controller;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.genesis.genesisapi.config.CustomPasswordEncoder;
import com.genesis.genesisapi.config.CustomSaltSource;
import com.genesis.genesisapi.config.CustomSha1Encoder;
import com.genesis.genesisapi.model.Account;
import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.model.ProfilePic;
import com.genesis.genesisapi.model.Role;
import com.genesis.genesisapi.model.UsersModel;
import com.genesis.genesisapi.repository.AccountRepo;
import com.genesis.genesisapi.repository.ProfilePicRepo;
import com.genesis.genesisapi.repository.UserRepository;
import com.genesis.genesisapi.service.UsersModelService;
import com.genesis.genesisapi.service.UsersModelServiceImpl;
import com.genesis.genesisapi.util.EncryptionHandler;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static String UPLOADED_FOLDER = "C://Users//Chandan//Desktop//Genesis//genesis//src//main//resources//static//images//";
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfilePicRepo profilePicRepo;
	
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	private CustomSaltSource customSaltSource;
	
	@Autowired
	private UsersModelServiceImpl usersModelServiceImpl;
	
	@GetMapping("/")
	public List<UsersModel> getAllUser(){
		List<UsersModel> userLists = userRepository.findAll();
		return userLists;
	}
	
	@GetMapping("/{userId}")
	public UsersModel getUserById(@PathVariable Long userId){
		UsersModel userLists = userRepository.findById(userId).get();
		return userLists;
	}
	
	@GetMapping("/getAdmin/{adminId}")
	public UsersModel getUserBygetAdmin(@PathVariable String adminId){
		
		UsersModel userLists = userRepository.findByuserId(adminId);
		return userLists;
	}
	
	@PostMapping("/")
	public ResponseEntity<UsersModel> saveUser(@RequestBody UsersModel usersModel) {
		System.out.println("Executing / Method");
		
		File file = new File("");
		ProfilePic profilePic = new ProfilePic();
				
		try {
			file = ResourceUtils.getFile("classpath:static\\img\\samples\\admin.jpg");
	    	if(usersModel.getGender().equals("female")){
	    		file = ResourceUtils.getFile("classpath:static\\img\\samples\\admin_female.jpg");
	    	}else{
	    		file = ResourceUtils.getFile("classpath:static\\img\\samples\\admin.jpg");
	    	}
			ByteArrayOutputStream byteOutStream = null;
        	BufferedImage bufferedImage=ImageIO.read(file);
            byteOutStream=new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", byteOutStream);
			byte[] bFile =  byteOutStream.toByteArray();
	        Blob blob = null;
			blob = new javax.sql.rowset.serial.SerialBlob(bFile);
			Path path = Paths.get(UPLOADED_FOLDER + file.getName());
			
	        profilePic.setProfilePicName(file.getName());
	        profilePic.setProfilePicType(getExtensionOfFile(file));
	        profilePic.setPicImage(blob);
	        profilePicRepo.save(profilePic);
	        byteOutStream.close();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} 
		
		usersModel.setPassword(EncryptionHandler.get_SHA_1_SecurePassword(usersModel.getPassword()));
		ProfilePic proPic = new ProfilePic();
		proPic.setProfilePicId(profilePic.getProfilePicId());
		usersModel.setProfilePic(proPic);
		UsersModel user = userRepository.save(usersModel);
		
		logger.info("Record successfully retrieved from users table.");
        return new ResponseEntity<>(user, HttpStatus.OK);
		//return user;
	}
	
	@PutMapping("/{sno}")
	public UsersModel updateUser(@PathVariable Long sno, @RequestBody UsersModel usersModel) {
		System.out.println("Executing Put Method");
		ProfilePic proPic = new ProfilePic();
		
		UsersModel user = userRepository.findById(sno).get();
		proPic.setProfilePicId(user.getProfilePic().getProfilePicId());
		
		user.setfName(usersModel.getfName());
		user.setlName(usersModel.getlName());
		user.setGender(usersModel.getGender());
		user.setName(usersModel.getfName()+" "+usersModel.getlName());
		user.setPassword(EncryptionHandler.get_SHA_1_SecurePassword(usersModel.getPassword()));
		user.setUserId(usersModel.getUserId());
		user.setMobileNo(usersModel.getMobileNo());
		user.setEmailId(usersModel.getEmailId());
		user.setProfilePic(proPic);
		UsersModel updatedUser = userRepository.save(user);
		return updatedUser;
	}
	
    @PostMapping("/saveUser/{sno}")
    public UsersModel saveClientInfo(@PathVariable Long sno, @RequestParam(value = "fName",required = false) String fName,
                                     @RequestParam(value = "lName",required = false) String lName,
                                     @RequestParam(value = "userId",required = false) String userId,
                                     @RequestParam(value = "password",required = false) String password,
                                     @RequestParam(value = "mobileNo",required = false) String mobileNo,
                                     @RequestParam(value = "emailId",required = false) String emailId,
                                     @RequestParam(value = "gender",required = false) String gender)throws ParseException{

    	System.out.println("fName  : "+fName);
    	
    	UsersModel user = new UsersModel();
    	user.setSno(sno);
    	user.setfName(fName);
    	user.setlName(lName);
    	user.setName(fName+" "+lName);
    	user.setMobileNo(mobileNo);
    	user.setEmailId(emailId);
        user.setGender(gender);
        user.setPassword(EncryptionHandler.get_SHA_1_SecurePassword(password));
        userRepository.save(user);
        return user;
    }
    
    
    @GetMapping(value="/getUserId")
    public JSONObject getLotInfoNo(@RequestParam(value = "sno",required = false) String sno
    		, @RequestParam(value = "userId",required = false) String userId){
        JSONObject  responsejson  = new JSONObject();
        JSONObject  obj  = new JSONObject();
        
        
        String userIdStatus = "";
        
        if(sno != "") {
        	Long sNo = Long.valueOf(sno);
        	String currentUserId = userRepository.findById(sNo).get().getUserId();
        	if(currentUserId.equals(userId)) {
        		userIdStatus = "userId is available";
        	}else {
        		UsersModel userData = userRepository.findByuserId(userId);
            	if(userData != null) {
            		userIdStatus = "userId is not available";
                }else {
                	userIdStatus = "userId is available";
                }
        	}
        	
        	
        }else {
        	UsersModel userData = userRepository.findByuserId(userId);
        	if(userData != null) {
        		userIdStatus = "userId is not available";
            }else {
            	userIdStatus = "userId is available";
            }
        }
        responsejson.put("data",userIdStatus);
        logger.info("Records successfully retreived from UsersModel table where userId = "+userId);
        return responsejson;
    }
    
    
    @PostMapping("/api/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("files") MultipartFile uploadfile, HttpSession httpSession) throws SerialException, SQLException {

        logger.debug("Single file upload!");

        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        Long userId = (Long) httpSession.getAttribute("sno");
        try {

            saveUploadedFiles(Arrays.asList(uploadfile),userId);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Successfully uploaded - " +
                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);

    }

    @GetMapping("/displayImage")
    public void showImage(HttpServletResponse response,HttpServletRequest request, HttpSession httpSession) 
            throws ServletException, IOException{
    	
    	Long sno = (Long)httpSession.getAttribute("sno");
    	
    	ProfilePic profilePic = profilePicRepo.findById(userRepository.findById(sno).get().getProfilePic().getProfilePicId()).get();
    	Blob blob = profilePic.getPicImage();
    	byte[] bytes = {100,101,50,17};
		try {
			bytes = blob.getBytes(1l, (int)blob.length());
		} catch (SQLException e) {
			System.out.println("Error in displaying image");
			e.printStackTrace();
		}
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(bytes);
        response.getOutputStream().close();
        logger.info("Image passed to home.jsp");
    }
    
    @GetMapping("/allUsers")
	public JSONObject getOnlyAllUser(){
		JSONObject  response  = new JSONObject();
		List<UsersModel> userLists = userRepository.findAll();
		JSONArray list = new JSONArray();
		for(UsersModel model : userLists){
            JSONObject  obj  = new JSONObject();
            obj.put("emailId", model.getEmailId());
            obj.put("name", model.getName());
            obj.put("gender", model.getGender());
            obj.put("mobileNo", model.getMobileNo());
            obj.put("userId", model.getUserId());
            obj.put("sno", String.valueOf(model.getSno()));
            list.add(obj);

        }
        logger.info("Records successfully retreived from tallysheet table.");
        response.put("data",list);
       
		return response;
	}
    
    @GetMapping("/allUsers/{sno}")
	public JSONObject getOnlyAllUserBySno(@PathVariable String sno){
		JSONObject  response  = new JSONObject();
        
		UsersModel model = userRepository.findById(Long.valueOf(sno)).get();
		
            JSONObject  obj  = new JSONObject();
            obj.put("emailId", model.getEmailId());
            obj.put("fName", model.getfName());
            obj.put("lName", model.getlName());
            obj.put("gender", model.getGender());
            obj.put("mobileNo", model.getMobileNo());
            obj.put("userId", model.getUserId());
            obj.put("password", model.getPassword());

       
        logger.info("Records successfully retreived from user table.");
        response.put("data",obj);
       
		return response;
	}
    public static String getExtensionOfFile(File file)
	{
		String fileExtension="";
		// Get file Name first
		String fileName=file.getName();
		
		// If fileName do not contain "." or starts with "." then it is not a valid file
		if(fileName.contains(".") && fileName.lastIndexOf(".")!= 0)
		{
			fileExtension=fileName.substring(fileName.lastIndexOf(".")+1);
		}
		
		return fileExtension;
	}
    
    private void saveUploadedFiles(List<MultipartFile> files,Long userId) throws IOException, SerialException, SQLException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }
            //ProfilePic profilePic = new ProfilePic();
            UsersModel userData = userRepository.findById(userId).get();
            ProfilePic profilePic =  profilePicRepo.findById(userData.getProfilePic().getProfilePicId()).get();
            byte[] bytes = file.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            profilePic.setProfilePicName(file.getOriginalFilename());
            profilePic.setProfilePicType(file.getContentType());
            profilePic.setPicImage(blob);
            profilePicRepo.save(profilePic);
            logger.info("Records successfully saved to Profile Pic table.");
            //Files.write(path, bytes);
            
        	/*if(userData != null) {
        		System.out.println("Not able to find record with this user id.");
            }else {
            	userData.
            }*/
             
            
        }

    }
}
