

public class HttpResponse {

	//
	public String createResponse(String responseData, String contentType, int httpCode, String httpMsg){
		
        StringBuilder response = new StringBuilder();

        //Add header
        response.append("HTTP/1.0 " +httpCode+ " "+httpMsg + "\r\n");
        
        //add the content type dynamically
        if(contentType!=null){
        response.append("Content-Type: " + contentType+" ; text/html ; charset=UTF-8" + "\r\n");
        }else
        	response.append("Content-Type: " +"text/html" + "\r\n");
        	
        response.append("Content-Length: " + responseData.length() + "\r\n");
        response.append("\r\n");
        
        //Add response
        response.append(responseData);
	   
		return response.toString();
	}


}
