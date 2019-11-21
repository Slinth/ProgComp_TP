package tp.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service("conversionAwsService")
public class ConversionAwsService {
    private Regions region;
    private String functionName;

    @Autowired
    public ConversionAwsService() {
        this.region = Regions.fromName(Regions.US_EAST_1.getName());
        this.functionName = "ConvertPriceToUsd";
    }

    public double convert(double basePrice) {
        AWSLambdaClientBuilder builder = AWSLambdaClientBuilder.standard().withRegion(this.region);

        AWSLambda client = builder.build();

        InvokeRequest req = new InvokeRequest().withFunctionName(this.functionName).withPayload(String.valueOf(basePrice));

        InvokeResult result = client.invoke(req);

        Double ret = -1d;
        try {
            ret = Double.parseDouble(new String(result.getPayload().array(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            System.out.println("[AWS Lambda] Converted price : " + ret);
        }
        return ret;
    }

}
