package com.genesis.genesisapi.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;


public class JavaUtilities {

	private static final Logger LOGGER_APP = LoggerFactory.getLogger(JavaUtilities.class);

	private  JavaUtilities(){
	}
	
	public static String replacesqlspecialchar(String mysearchStrr){
		mysearchStrr = mysearchStrr.replaceAll(Pattern.quote("["), "\\\\[").replaceAll(Pattern.quote("]"), "\\\\]");
		mysearchStrr = mysearchStrr.replaceAll(Pattern.quote("%"), "[%]");
		mysearchStrr = mysearchStrr.replaceAll(Pattern.quote("*"), "%");
		mysearchStrr = mysearchStrr.replaceAll(Pattern.quote("'"), "['']");
		return mysearchStrr;
	} 
		public static String convert(String str)
	{
		StringBuilder ostr = new StringBuilder();
		if(str != null)
		for(int i=0; i<str.length(); i++) 
		{
			char ch = str.charAt(i);
 
			if ((ch >= 0x0020) && (ch <= 0x007e))	// Does the char need to be converted to unicode? 
			{
				ostr.append(ch);					// No.
			} else 									// Yes.
			{
	        	ostr.append("&#x") ;				// standard unicode format.
				String hex = Integer.toHexString(str.charAt(i) & 0xFFFF);	// Get hex value of the char. 
				for(int j=0; j<4-hex.length(); j++)	// Prepend zeros because unicode requires 4 digits
					ostr.append("0");
				ostr.append(hex.toLowerCase());		// standard unicode format.
				ostr.append(";");
				//ostr.append(hex.toLowerCase(Locale.ENGLISH));
			}
		}
		String returnValue = new String(ostr);
		returnValue = returnValue.replaceAll("'", "&lsquo;");
		return returnValue;		//Return the StringBuilder cast as a string.
	}
	
	/**
	 * If str is 1 as string then this return true, all other cases is false
	 * @param str
	 * @return
	 */
	public static Boolean getBooleanForDB(String str) {
        return "1".equalsIgnoreCase(str);
	}
	
	/**
	 * If str is 1 as string then this return true, all other cases is false
	 * @return
	 */
	public static Boolean getBooleanForDB(Integer i) {
        return 1 == i;
	}
	
	/**
	 * If bool is true as boolean then this return 1 as string, all other cases is 0
	 * @return
	 */
	public static String getStringFromBooleanForDB(Boolean bool) {
		if(bool != null && bool) {
			return "1";
		} else {
			return "0";
		}
	}
	
	public static byte[] objectToByteArray(Object obj){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(obj);
		  byte[] yourBytes = bos.toByteArray();
		  return yourBytes;
		} catch (IOException e) {
		} finally {
		  try {
		    if (out != null) {
		      out.close();
		    }
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		  try {
		    bos.close();
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		}
		return null;
	}

	public static Object byteArrayToObject(byte[] yourBytes){
		ByteArrayInputStream bis = new ByteArrayInputStream(yourBytes);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			Object o = in.readObject(); 
			return o;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER_APP.error("", e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			LOGGER_APP.error("", e);
		} finally {
			try {
				bis.close();
			} catch (IOException ex) {
				// ignore close exception
			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				// ignore close exception
			}
		}
		return null;
	}
	
	public static Document convertStringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try 
		{
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
			return doc;
		} catch (Exception e) {
			LOGGER_APP.error("", e);
		}
		return null;
	}

	public static String convertDocumentToString(Document doc) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			// below code to remove XML declaration
			 transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String output = writer.getBuffer().toString();
			return output;
		} catch (TransformerException e) {
			LOGGER_APP.error("", e);
		}

		return null;
	}
	
	public static void cleanFolder(File folderToClean) {
		try {
			if(folderToClean.exists()) {
				if(folderToClean.isDirectory()) {
					FileUtils.deleteDirectory(folderToClean);
				} else {
					folderToClean.delete();
				}
			}
		} catch (IOException e) {
		}
	}
	
	/**
	 * Return string value or empty string if null
	 * @param value
	 * @return
	 */
	public static String getValue(Object value) {
		if(value==null || value == null) {
			return "";
		}
		return String.valueOf(value).trim();
			
	}


	// convert the dd.MM.yyyy format into milliseconds  
	public static String getTimeInMilliSecondsNew(String dateconvertto)
	{
		long duedateMS = 0;	
		try
		{
			SimpleDateFormat dateFrmt = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
			if(dateconvertto !=null){
				dateconvertto = dateconvertto + " 00:00:00";
				Date dateStart1 = dateFrmt.parse(dateconvertto);
				duedateMS = dateStart1.getTime();
			}
			
		}
		catch(Exception e)
		{
			LOGGER_APP.error("", e);
		}
	
		return String.valueOf(duedateMS);
	}


	// convert the dd-MM-yyyy hh:mm am/pm format into milliseconds
	public static String getTimeInMilliSeconds(String dateFormat)
	{
		Calendar dtCal = Calendar.getInstance();
		String[] sDate,sTime,hourMin;
		String sD,sM,sY="00",sAP="AM",sHH="00",sMM="00";
		String sDateFormat = dateFormat;
		try
		{
			sDate = sDateFormat.split("-");
			sD = sDate[0];
			sM = sDate[1];
			sTime = sDate[2].split(" ");
			if(sTime.length>0)
				sY = sTime[0];
			if(sTime.length>1)
				sAP = sTime[2];
					
			dtCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sD));
			dtCal.set(Calendar.MONTH, Integer.parseInt(sM)-1);
			dtCal.set(Calendar.YEAR, Integer.parseInt(sY));
			dtCal.set(Calendar.HOUR, Integer.parseInt(sHH));
			dtCal.set(Calendar.MINUTE, Integer.parseInt(sMM));
		}
		catch(Exception e)
		{
			LOGGER_APP.error("", e);
		}
				
		return String.valueOf(dtCal.getTimeInMillis());
	}

	public static String  lineBylineList(String reqStr,int brakeNo)
	{String str1,str2,str3,exitflag,str4 = "";
	 int rlenth,p1,pCnt;
		if(!reqStr.equals(""))
		{
			if (reqStr.length() <= brakeNo)
				   return reqStr;
			else
			{str1 = reqStr;
			 str3 = str1;
				str1 = str3.substring(0,brakeNo);
				 
				 /*str1 = reqStr;
				 str3 = str1;
				 exitflag	=	"N";
				  
				  	pCnt=reqStr.length();
			
				   while ((pCnt) > 0)
				   {
						
					    str1 = str3.substring(0,brakeNo);
						pCnt	=	pCnt - (str1.length());
			          
						
						if((str3.length()) > brakeNo)
						{	
							
							str2 = str3.substring((str3.length())-brakeNo);
							str3 = str2;
						}	
						else if (str3.length() < brakeNo && !str3.equals(StringLiterals.EMPTY_NOSPACE) )
						 {
							   // str3 = str3;
							    exitflag	= "Y";
						}
						else	
						{
							exitflag= "Y";
						}
				   }	
	
						if (str4 .equals(StringLiterals.EMPTY_NOSPACE) )
						{
							str4 = str1;
						}
							else
							str4 = str4 + "<br>" + str1;
				return str4;   */
				return str1;
			}
			
		}
		else
		{
			return 	(reqStr);
		}	
	}

	public static String removeLeadingZeros(String OBJID) {
		int beginIndex=0;
		for(int i=0;i<OBJID.length();i++) {
			String newStr=new String(OBJID.charAt(i)+"");
			if(!newStr.equals("0")){
				beginIndex=i;
				break;
			}
		}
		OBJID = OBJID.substring(beginIndex, OBJID.length());			
		return OBJID;
	}
	
	public static String converObjToString(Object objVal){
		String returnVal = null;
		if(objVal == null){
			return null;
		}else if(objVal instanceof Clob){
			Clob clb = (Clob)objVal;
			try {
				returnVal= clb.getSubString(1, (int) clb.length());
			} catch (SQLException e) {
				LOGGER_APP.error("", e);
			}
		}else if(objVal instanceof Blob){
			Blob blobval = (Blob) objVal;
			try {
				returnVal = IOUtils.toString(blobval.getBinaryStream(),"UTF-8" );
			} catch (IOException e) {
				LOGGER_APP.error("", e);
			} catch (SQLException e) {
				LOGGER_APP.error("", e);
			}
		}else{
			returnVal = String.valueOf(objVal);
		}
		return returnVal;
		
	}

    public static void zipFiles(List<File> files, File output){

        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        try {
            fos = new FileOutputStream(output);
            zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
            zipOut.setMethod(ZipOutputStream.DEFLATED);
            zipOut.setLevel(Deflater.BEST_COMPRESSION);
            for(File f : files){
                FileInputStream fis = new FileInputStream(f);
                ZipEntry ze = new ZipEntry(f.getName());
                zipOut.putNextEntry(ze);
                byte[] tmp = new byte[4*1024];
                int size = 0;
                while((size = fis.read(tmp)) != -1){
                    zipOut.write(tmp, 0, size);
                }
                zipOut.flush();
                fis.close();
            }
            zipOut.close();
            LOGGER_APP.info("Done... Zipped the files...");
        } catch (FileNotFoundException e) {
            LOGGER_APP.error("Error zipping", e);
        } catch (IOException e) {
            LOGGER_APP.error("Error writing zip", e);
        } finally{
            try{
                if(fos != null) fos.close();
            } catch(Exception ex){
                LOGGER_APP.error("Close error", ex);
            }
        }
    }
    
    public static String getDateTimeFormat(String userDateFormat){
    	String datFormat ="";
		String[] date_format ={"mm.dd.yy","dd.mm.yy","dd M, yy","MM d, yy"};
			for(int d=0;d<date_format.length;d++)
			{
				if(userDateFormat.equalsIgnoreCase(date_format[d]))
				{
					if(d==0){
						datFormat ="MM.dd.yyyy HH:mm:ss";
					}else if(d==1){
						datFormat ="dd.MM.yyyy HH:mm:ss";
					}else if(d==2){
						datFormat ="dd MMM, yyyy HH:mm:ss";
					}else{
						datFormat ="MMMM d, yyyy HH:mm:ss";
					}
				}
			}
			if(StringUtils.isBlank(datFormat)){
				datFormat ="dd.MM.yyyy HH:mm:ss";
			}
		return datFormat;	
		
    }
    
    public static void writeFileToResponse(File fileToWrite, String contentType, String filename, HttpServletResponse response) {
        byte[] buffer = new byte[1024];
        try {
            FileInputStream inputStream = new FileInputStream(fileToWrite);

            // set content attributes for the response
            response.setContentType(contentType);
            response.setContentLength((int) fileToWrite.length());

            // set headers for the response
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=\""+filename+"\"";
            response.setHeader(headerKey, headerValue);

            // get output stream of the response
            OutputStream outStream = response.getOutputStream();

            int bytesRead = -1;

            // write bytes read from the input stream into the output stream
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outStream.close();
        } catch (IOException e) {
            LOGGER_APP.error("Download client cip failed", e);
        }
    }

public static LinkedHashMap sortColumn( HashMap jsonObject,String column){
    	  LinkedHashMap sortedMap = new LinkedHashMap();
    	  String[] arrayColumn=column.split(",");
    	  for (String key : arrayColumn) {
    		  if(jsonObject.get(key)!=null){
    			  sortedMap.put(key, jsonObject.get(key));
    		  }
    	  }
    	  return sortedMap;
  	}
    /*convert character to unicode */
    public static String convertIntoUnicode (String src) {
    	//src = "la transaction a �t� cr��e ou modifi�e";
    	final CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();
    	final StringBuilder result = new StringBuilder();
    	for (final Character  character : src.toCharArray()) {
    		if (asciiEncoder.canEncode(character)) {
    			result.append(character);
    		} else {
    			result.append("\\u");
    			result.append(Integer.toHexString(0x10000 | character).substring(1).toUpperCase());
    		}
    	}
    	return result.toString();
    }
    
    public static StringBuilder zeroAppender(long numberOfZeros) {
		StringBuilder zeros = new StringBuilder();
		for (int i = 0; i < numberOfZeros; i++) {
			zeros.append("0");
		}
		return zeros;
	}

	public  static JSONObject parseJsonStringToJson(String jsonString){
		final JSONParser jsonParser = new JSONParser();
		if(StringUtils.isNotBlank(jsonString)){
			try {
				return (JSONObject) jsonParser.parse(jsonString);
			} catch (ParseException e) {
				LOGGER_APP.error("Unable To parse Json String.",e);
				return null;
			}
		}else{
			return null;
		}

	}
	
	public static String getJavaExecutablePath() {
		return System.getProperty("java.home") + File.separator + "bin" + File.separator + (SystemUtils.IS_OS_WINDOWS ? "java.exe" : "java");
	}
}
