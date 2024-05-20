package th.co.truecorp.crmdev.asset;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.fasterxml.jackson.databind.JsonNode;

import th.co.truecorp.crmdev.util.common.JsonManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CRMIAssetTestCase {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private Environment environment;
    
    private Document testDataDOM;
    private JsonManager jsonManager;

    @Before
    public void initial() throws Exception {
    	FileInputStream fileInputStream = null;
    	
    	try {
    		String[] activeProfiles = environment.getActiveProfiles();
    		String activeProfile = activeProfiles[0];
    		
    		File xmlFile = new File(getClass().getResource("/TestData-" + activeProfile + ".xml").getFile());
            fileInputStream = new FileInputStream(xmlFile);
            
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            this.testDataDOM = docBuilder.parse(fileInputStream);
            
            this.jsonManager = new JsonManager();
    	}
    	finally {
    		if (fileInputStream != null) {
    			fileInputStream.close();
    			fileInputStream = null;
    		}
    	}
    }
    
    /**
     * 
     * @param expression such as "/test_data/test_case_name/request/text()"
     * @return data
     * @throws XPathException 
     * @throws TransformerException 
     * 
     */
    public String getTestData(String expression) throws XPathException, TransformerException {
    	XPath xPath = XPathFactory.newInstance().newXPath();
    	Node node = (Node) xPath.compile(expression).evaluate(this.testDataDOM, XPathConstants.NODE);
    	
    	StringWriter writer = new StringWriter();
    	Transformer transformer = TransformerFactory.newInstance().newTransformer();
    	transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    	transformer.transform(new DOMSource(node), new StreamResult(writer));
    	return writer.toString();
    }
    
    /**
     * 
     * @param requestPath such as /getAssetRootList
     * @param testCaseName
     * @return ResultActions
     * @throws Exception
     */
    public ResultActions invokeAPI(String requestPath, String testCaseName) throws Exception {
    	
    	String jsonRequest = this.getTestData("/test_data/" + testCaseName + "/request/text()");
    	
    	MockHttpServletRequestBuilder httpRequestBuilder = MockMvcRequestBuilders.post("/CRMIAsset" + requestPath);
    	httpRequestBuilder.contentType("application/json");
    	httpRequestBuilder.content(jsonRequest);
    	
    	return this.mockMvc.perform(httpRequestBuilder);
    }
    
    public JsonNode getExpectedValue(String testCaseName) throws Exception {
    	String jsonResponse = this.getTestData("/test_data/" + testCaseName + "/response/text()");
    	return this.jsonManager.readTree(jsonResponse);
    }
    
    @Test
    public void getAssetRootListSuccessTMV() throws Exception {
    	JsonNode expectedValue = this.getExpectedValue("SuccessTMV");
    	
        ResultActions resultActions = this.invokeAPI("/getAssetRootList", "SuccessTMV");
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.code").value(expectedValue.get("code").textValue()));
        resultActions.andExpect(jsonPath("$.assetBeanList").exists());
    }
    
    @Test
    public void getAssetRootListSuccessTOL() throws Exception {
    	JsonNode expectedValue = this.getExpectedValue("SuccessTOL");
    	
        ResultActions resultActions = this.invokeAPI("/getAssetRootList", "SuccessTOL");
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.code").value(expectedValue.get("code").textValue()));
        resultActions.andExpect(jsonPath("$.assetBeanList").exists());
    }
    
    @Test
    public void getAssetRootListFailNotFound() throws Exception {
    	// Case : Not Found
    	JsonNode expectedValue = this.getExpectedValue("FailNotFound");
    	
        ResultActions resultActions = this.invokeAPI("/getAssetRootList", "FailNotFound");
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.code").value(expectedValue.get("code").textValue()));
        resultActions.andExpect(jsonPath("$.assetBeanList").doesNotExist());
    }
    
    @Test
    public void getAssetRootListFailRequiredField() throws Exception {
    	// Case : Require Field
    	JsonNode expectedValue = this.getExpectedValue("FailRequiredField");
    	
        ResultActions resultActions = this.invokeAPI("/getAssetRootList", "FailRequiredField");
        resultActions.andDo(print());
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.code").value(expectedValue.get("code").textValue()));
        resultActions.andExpect(jsonPath("$.assetBeanList").doesNotExist());
    }
}