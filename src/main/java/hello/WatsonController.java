package hello;

import static org.mockito.Mockito.ignoreStubs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonFormat.Features;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.KeywordsOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.KeywordsResult;



	
@Controller
public class WatsonController {
	
	private static String content = "this is #the thing* and this is trash# and this is the thing* and this is trash#thing!*";
	
   // @RequestMapping("/analyze")
    public void analyze(@RequestParam(value="name", required=false, defaultValue="") String name, Model model) throws Exception {
    		ArrayList<String> sentences = getSentences(content);
    		System.out.println(sentences);
    	
    			
    			//model.addAttribute("name", response);

    }
    
    private ArrayList<String> getSentences(String content){

    		Scanner s = new Scanner(content).useDelimiter("#|\\*");
    		ArrayList<String> sentences = new ArrayList<String>();
    		String next;
    		String trash;
    		while(s.hasNext()) {
    			trash = s.next();
    			if(s.hasNext()) {
    				next = s.next();
    				sentences.add(next);
    			}
    			
    		}
    	
    		
    		
    	return sentences;
    }
    
    private String getTopKeyword(String text) {
    	
    		NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
  			  NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
  			  "1f714fc9-b781-4128-bb02-63c843fb098f",
  			  "xEIGvUHgirY4"
  			);


  			KeywordsOptions keywordsOptions = new KeywordsOptions.Builder()
  			  .limit(2)
  			  .build();

  			com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features features = new com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features.Builder()
  			.keywords(keywordsOptions)
  			.build();

  			AnalyzeOptions parameters = new AnalyzeOptions.Builder()
  			  .text(text)
  			  .features(features)
  			  .build();

  			AnalysisResults response = service
  			  .analyze(parameters)
  			  .execute();
  			System.out.println(response);
  			
  			Iterator<KeywordsResult> responseIterator = response.getKeywords().iterator();
  			
  			String topkeyword = responseIterator.next().toString();
  			
  			return topkeyword;
    	
    }
}

