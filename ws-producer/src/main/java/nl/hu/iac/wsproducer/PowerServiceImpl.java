package nl.hu.iac.wsproducer;

import java.math.BigInteger;

import javax.jws.WebService;

import com.sun.xml.ws.developer.SchemaValidation;

import wsinterface.*;


@WebService( endpointInterface= "wsinterface.WSInterface")
@SchemaValidation(handler = SchemaValidationErrorHandler.class)

public class PowerServiceImpl implements wsinterface.WSInterface  {

	@Override
	public Response calculatePower(Request request) throws Fault{
		System.out.println("Request object "+request.getX()+ " " +request.getPower());
		ObjectFactory factory = new ObjectFactory();
		Response response = factory.createResponse();
		try {
			// het omzetten van watt naar kw/h
			BigInteger result = request.getPower();
			response.setResult(result);
		} catch (Exception e) {//RuntimeException |
			PowerFault x = factory.createPowerFault();
			x.setErrorCode((short) 1);
			x.setMessage("error");
			Fault fault = new Fault(
					"Er ging iets mis met het omzetten van watt naar kw/h", x);

			throw fault;
		}
		return response;
	}


}
